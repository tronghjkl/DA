package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.domain.Ward;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.DistrictSearchDto;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.repository.DistrictReponsitory;
import com.globits.da.repository.ProvinceReponsitory;
import com.globits.da.repository.WardReponsitory;
import com.globits.da.service.DistrictService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DistrictServiceImpl extends GenericServiceImpl<District, UUID> implements DistrictService {
    @Autowired
    DistrictReponsitory reponsitory;
    @Autowired
    ProvinceReponsitory provinceReponsitory;

    @Autowired
    WardReponsitory wardReponsitory;


    @Override
    public Page<DistrictDto> getPage(int pageSize, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return reponsitory.getListPage(pageable);
    }

    @Override
    public ResponObject<DistrictDto> add(DistrictDto dto) {
        if (dto == null) {
            return new ResponObject<>("Not Found", "BAD REQUEST", 400);
        }
        District entity = new District();

        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setArea(dto.getArea());
        entity.setPopulation(dto.getPopulation());

        if (dto.getWards() != null) {
            List<Ward> wards = new ArrayList<>();
            for (WardDto wardDto : dto.getWards()) {
                Ward ward = new Ward();
                ward.setName(wardDto.getName());
                ward.setCode(wardDto.getCode());
                ward.setArea(wardDto.getArea());
                ward.setPopulation(wardDto.getPopulation());

                ward.setDistrict(entity);
                wards.add(ward);
            }

            entity.setWards(wards);
        }
        if (entity != null) {
            reponsitory.save(entity);
        }
        return new ResponObject<>("Add District Ok", "OK", 200, dto);
    }

    @Override
    public ResponObject<DistrictDto> update(UUID id, DistrictDto dto) {
        if (dto != null) {
            District entity = null;
            entity = reponsitory.findById(id).get();

            if (entity == null) {
                return new ResponObject<>("District is not exist", "BAD REQUEST", 400);
            }

            entity.setCode(dto.getCode());
            entity.setName(dto.getName());
            entity.setArea(dto.getArea());
            entity.setPopulation(dto.getPopulation());

            List<WardDto> wardDtoList = dto.getWards();
            List<Ward> wards = new ArrayList<>();

            for (WardDto wardDto : wardDtoList) {
                Ward ward = wardReponsitory.findById(wardDto.getId()).get();
                ward.setCode(wardDto.getCode());
                ward.setName(wardDto.getName());
                ward.setPopulation(wardDto.getPopulation());
                ward.setArea(wardDto.getArea());

                wards.add(ward);
            }
            entity.setWards(wards);

            reponsitory.save(entity);
        }

        return new ResponObject<>("Update Successful", "OK", 200, dto);
    }

    @Override
    public ResponObject<Boolean> deleteKho(UUID id) {
        if (id != null) {
            Optional<District> district = reponsitory.findById(id);
            if (district.isPresent()) {
                reponsitory.deleteById(id);
                return new ResponObject<>("Delete Successful", "OK", 200, true);
            } else {
                return new ResponObject<>("Not Found District Need Delete", "BAD REQUEST", 400, false);
            }
        }

        return new ResponObject<>("Delete Failed", "BAD REQUEST", 400, false);
    }

    @Override
    public Page<DistrictDto> searchByPage(DistrictSearchDto dto) {
        if (dto == null) {
            return null;
        }

        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();

        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }

        String whereClause = "";


        String orderBy = " ORDER BY entity.createDate DESC";

        String sqlCount = "select count(entity.id) from  District as entity where (1=1)";
        String sql = "select new com.globits.da.dto.DistrictDto(entity) from  District as entity where (1=1)  ";

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text OR entity.population LIKE :text OR entity.area LIKE :text OR entity.GDP LIKE :text OR entity.province LIKE :text )";
        }

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, DistrictDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }
        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<DistrictDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<DistrictDto> result = new PageImpl<DistrictDto>(entities, pageable, count);
        return result;
    }

    @Override
    public ResponObject<List<DistrictDto>> getAll() {
        List<DistrictDto> districtDtoList = reponsitory.getAllDistrict();
        if (CollectionUtils.isEmpty(districtDtoList)) {
            return new ResponObject<>("Not Find Province", "BAD REQUEST", 400);
        }
        return new ResponObject<>("Get All Successful", "OK", 200, districtDtoList);
    }

    @Override
    public ResponObject<DistrictDto> saveWithProvinceId(DistrictDto dto) {
        if (dto != null) {
            return new ResponObject<>("District is blank", "Bad Request", 400, dto);
        }

        District entity = new District();

        Optional<Province> province = provinceReponsitory.findById(dto.getProvinceId());

        if (province.isPresent()) {
            entity.setCode(dto.getCode());
            entity.setName(dto.getName());
            entity.setPopulation(dto.getPopulation());
            entity.setArea(dto.getArea());
            entity.setGDP(dto.getGDP());
            entity.setProvince(province.get());

            if (entity == null) {
                return new ResponObject<>("ProvinceId is blank", "Bad Request", 400, dto);
            }
            reponsitory.save(entity);
        }
        return new ResponObject<>("Add District Successfuly", "OK", 200, dto);
    }

    @Override
    public ResponObject<List<DistrictDto>> getDistrictByProvinceId(UUID id) {
        List<DistrictDto> districtDtoList = reponsitory.getDistrictsByProvinceId(id);
        if (CollectionUtils.isNotEmpty(districtDtoList)) {
            return new ResponObject<>("Get List Successfuly", "OK", 200, districtDtoList);
        }
        return new ResponObject<>("Get List Failed", "BAD REQUEST", 400);
    }
}
