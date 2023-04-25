package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.domain.Ward;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.repository.DistrictReponsitory;
import com.globits.da.repository.ProvinceReponsitory;
import com.globits.da.service.ProvinceService;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
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
public class ProvinceServiceImpl extends GenericServiceImpl<Province, UUID> implements ProvinceService {
    @Autowired
    ProvinceReponsitory reponsitory;
    @Autowired
    DistrictReponsitory districtReponsitory;

    @Override
    public Page<ProvinceDto> getPage(int pageSize, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return reponsitory.getListPage(pageable);
    }

    @Override
    public ResponObject<Page<ProvinceDto>> searchByPage(ProvinceSearchDto dto) {
        if (dto == null) {
            return new ResponObject<>("Province search is blank", "BAD REQUEST", 400);
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

        String sqlCount = "select count(entity.id) from  Province as entity where (1=1)";
        String sql = "select new com.globits.da.dto.ProvinceDto(entity) from  Province as entity where (1=1)  ";

        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text OR entity.population LIKE :text OR entity.area LIKE :text OR entity.gdp LIKE :text )";
        }


        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, ProvinceDto.class);
        Query qCount = manager.createQuery(sqlCount);


        if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
            q.setParameter("text", '%' + dto.getKeyword() + '%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }
        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<ProvinceDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<ProvinceDto> result = new PageImpl<>(entities, pageable, count);
        return new ResponObject<>("Successfully", "OK", 200, result);
    }

    @Override
    public ResponObject<ProvinceDto> update(UUID id, ProvinceDto dto) {
        if (dto == null) {
            return new ResponObject<>("ProvinceDto is blank", "Bad Request", 400);
        }
        Province entity = reponsitory.findById(id).orElse(null);
        if (entity == null) {
            return new ResponObject<>("ProvinceId is blank", "Bad Request", 400);
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(dto, entity);

        if (!dto.getDistricts().isEmpty()) {
            List<DistrictDto> districtDtoList = dto.getDistricts();
            List<District> districts = new ArrayList<>();

            for (DistrictDto districtDto : districtDtoList) {
                District district = districtReponsitory.findById(districtDto.getId()).orElse(null);
                modelMapper.map(districtDto, district);
                districts.add(district);
            }
            entity.setDistricts(districts);
        }
        reponsitory.save(entity);
        return new ResponObject<>("Update Successfuly", "OK", 200, new ProvinceDto(entity));
    }

    @Override
    public ResponObject<ProvinceDto> add(ProvinceDto dto) {

        if (dto == null) {
            return new ResponObject<>("ProvinceDto is blank", "Bad Request", 400);
        }
        Province entity = new Province();

        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setArea(dto.getArea());
        entity.setPopulation(dto.getPopulation());
        entity.setGDP(dto.getGdp());

        if (CollectionUtils.isNotEmpty(dto.getDistricts())) {

            List<District> districts = new ArrayList<>();

            for (DistrictDto districtDto : dto.getDistricts()) {
                District district = new District();
                district.setName(districtDto.getName());
                district.setCode(districtDto.getCode());
                district.setArea(districtDto.getArea());
                district.setPopulation(districtDto.getPopulation());
                district.setGDP(districtDto.getGDP());
                district.setProvince(entity);
                districts.add(district);

                if (CollectionUtils.isNotEmpty(districtDto.getWards())) {
                    List<Ward> wards = new ArrayList<>();
                    for (WardDto wardDto : districtDto.getWards()) {
                        Ward ward = new Ward();
                        ward.setName(wardDto.getName());
                        ward.setCode(wardDto.getCode());
                        ward.setArea(wardDto.getArea());
                        ward.setPopulation(wardDto.getPopulation());
                        ward.setDistrict(district);
                        wards.add(ward);
                    }
                    district.setWards(wards);
                }
            }
            entity.setDistricts(districts);
        }
        if (entity != null) {
            entity = reponsitory.save(entity);
        }
        return new ResponObject<>("Add Successfuly", "OK", 200, new ProvinceDto(entity));
    }

    @Override
    public ResponObject<Boolean> deleteKho(UUID id) {
        if (id == null) {
            return new ResponObject<>("Input Id", "BAD REQUEST", 400, false);
        }
        Optional<Province> province = reponsitory.findById(id);
        if (province.isPresent()) {
            reponsitory.deleteById(id);
            return new ResponObject<>("Delete Succesful", "OK", 200, true);
        } else
            return new ResponObject<>("Not Found Province Need Delete", "BAD REQUEST", 400);

    }

    @Override
    public ResponObject<List<ProvinceDto>> getAll() {
        List<ProvinceDto> provinceDtoList = reponsitory.getAllProvince();
        if (CollectionUtils.isEmpty(provinceDtoList)) {
            return new ResponObject<>("Not Find Province", "BAD REQUEST", 400);
        }
        return new ResponObject<>("Get All Successful", "OK", 200, provinceDtoList);
    }

}
