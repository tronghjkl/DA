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
    public DistrictDto saveOrUpdate(UUID id, DistrictDto dto) {
        if (dto != null) {
            District entity = null;
            // update
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = reponsitory.getOne(id);
            }
            if (entity == null) {
                entity = new District();
                entity.setWards(new ArrayList<>());
            }

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

            entity = reponsitory.save(entity);
            if (entity != null) {
                return new DistrictDto(entity);
            }
        }
        return null;
    }

    @Override
    public Boolean deleteKho(UUID id) {
        if (id != null) {
            reponsitory.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public DistrictDto getCertificate(UUID id) {
        return null;
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
    public Boolean checkCode(UUID id, String code) {
        return null;
    }

    @Override
    public List<DistrictDto> getAllDistrict() {
        List<DistrictDto> listDistrict = reponsitory.getAllDistrict();
        return listDistrict;
    }

    @Override
    public Boolean deleteCheckById(UUID id) {
        return null;
    }

    @Override
    public ResponObject<DistrictDto> saveOrUpdate2(UUID id, DistrictDto dto) {
        if (dto != null) {
            District entity = null;
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = reponsitory.getOne(dto.getId());
            }
            if (entity == null) {
                entity = new District();
            }

            Optional<Province> province = provinceReponsitory.findById(dto.getProvinceId());

            if (province.isPresent()) {
                entity.setCode(dto.getCode());
                entity.setName(dto.getName());
                entity.setPopulation(dto.getPopulation());
                entity.setArea(dto.getArea());
                entity.setGDP(dto.getGDP());
                entity.setProvince(province.get());

                entity = reponsitory.save(entity);
                if (entity != null) {
                    return new ResponObject<>("Add District Successfuly", "OK", 200, dto);
                }
            } else {
                return new ResponObject<>("ProvinceId is blank", "Bad Request", 400, dto);
            }

        }
        return new ResponObject<>("District is blank", "Bad Request", 400, dto);

    }

    @Override
    public DistrictDto update(UUID id, DistrictDto dto) {
        if (dto != null) {
            District entity = null;
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = reponsitory.findById(id).get();
            }
            if (entity == null) {
                entity = new District();
                entity.setWards(new ArrayList<>());
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

            if (entity != null) {
                return new DistrictDto(entity);
            }
        }

        return null;
    }

    @Override
    public ResponObject<List<DistrictDto>> getDistrictByProvinceId(UUID id) {
        List<DistrictDto> districtDtoList = reponsitory.getDistrictsByProvinceId(id);
        return new ResponObject<List<DistrictDto>>("Get List Successfuly", "OK", 200, districtDtoList);
    }
}
