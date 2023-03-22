package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.domain.Ward;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.repository.DistrictReponsitory;
import com.globits.da.repository.ProvinceReponsitory;
import com.globits.da.service.ProvinceService;
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
import java.util.UUID;

@Service
public class ProvinceServiceImpl extends GenericServiceImpl<Province, UUID> implements ProvinceService {
    @Autowired
    ProvinceReponsitory reponsitory;
    @Autowired
    DistrictReponsitory districtReponsitory;

    @Override
    public Page<ProvinceDto> getPage(int pageSize, int pageIndex) {
        return null;
    }

    @Override
    public ProvinceDto saveOrUpdate(UUID id, ProvinceDto dto) {
        if (dto != null) {
            Province entity = null;
            // update
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = reponsitory.getOne(id);
            }
            if (entity == null) {
                entity = new Province();
//                entity.setDistricts(new ArrayList<>());
            }

            entity.setCode(dto.getCode());
            entity.setName(dto.getName());
            entity.setArea(dto.getArea());
            entity.setPopulation(dto.getPopulation());
            entity.setGDP(dto.getGdp());

//            List<District> districts = dto.getDistricts();
//            for (District district : districts) {
//                district = districtReponsitory.save(district);
//                district.setProvince(entity);
//                entity.getDistricts().add(district);
//            }

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
            }

            entity.setDistricts(districts);

            entity = reponsitory.save(entity);
            if (entity != null) {
                return new ProvinceDto(entity);
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
    public ProvinceDto getCertificate(UUID id) {
        return null;
    }

    @Override
    public Page<ProvinceDto> searchByPage(ProvinceSearchDto dto) {
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
        Page<ProvinceDto> result = new PageImpl<ProvinceDto>(entities, pageable, count);
        return result;
    }

    @Override
    public Boolean checkCode(UUID id, String code) {
        return null;
    }

    @Override
    public List<ProvinceDto> getAllProvince() {
        List<ProvinceDto> provinceDtoList = reponsitory.getAllProvince();
        return provinceDtoList;
    }

    @Override
    public Boolean deleteCheckById(UUID id) {
        return null;
    }

    @Override
    public ProvinceDto update(UUID id, ProvinceDto dto) {
        if (dto != null) {
            Province entity = null;
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = reponsitory.findById(id).get();
            }
            if (entity == null) {
                entity = new Province();
                entity.setDistricts(new ArrayList<>());
            }

            entity.setCode(dto.getCode());
            entity.setName(dto.getName());
            entity.setArea(dto.getArea());
            entity.setPopulation(dto.getPopulation());
            entity.setGDP(dto.getGdp());

//            List<DistrictDto> districtDtoList = districtReponsitory.getDistrictsByProvinceId(id);
            List<DistrictDto> districtDtoList = dto.getDistricts();
            List<District> districts = new ArrayList<>();

            for (DistrictDto districtDto : districtDtoList) {
                District district = districtReponsitory.findById(districtDto.getId()).get();
                district.setCode(districtDto.getCode());
                district.setName(districtDto.getName());
                district.setPopulation(districtDto.getPopulation());
                district.setArea(districtDto.getArea());
                district.setGDP(districtDto.getGDP());

                districts.add(district);

            }

            entity.setDistricts(districts);
            reponsitory.save(entity);

            if (entity != null) {
                return new ProvinceDto(entity);
            }
        }

        return null;
    }

    @Override
    public ProvinceDto addProvince(UUID id, ProvinceDto dto) {

        if (dto != null) {
            Province entity = null;
            // update
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = reponsitory.getOne(id);
            }
            if (entity == null) {
                entity = new Province();
//                entity.setDistricts(new ArrayList<>());
            }

            entity.setCode(dto.getCode());
            entity.setName(dto.getName());
            entity.setArea(dto.getArea());
            entity.setPopulation(dto.getPopulation());
            entity.setGDP(dto.getGdp());


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
//                district.setWards(new ArrayList<>());

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
            entity.setDistricts(districts);
            entity = reponsitory.save(entity);
            if (entity != null) {
                return new ProvinceDto(entity);
            }
        }
        return null;
    }


//    @Transactional
//    public void updateProvinceAndDistricts(ProvinceDto provinceDto, List<DistrictDto> districtDtos) {
//
//        Province province = reponsitory.findById(provinceDto.getId())
//                .orElseThrow(() -> new EntityNotFoundException("Cannot find province with ID: " + provinceDto.getId()));
//
//        // Update province information
//        province.setName(provinceDto.getName());
//        province.setCode(provinceDto.getCode());
//        province.setArea(provinceDto.getArea());
//        province.setPopulation(provinceDto.getPopulation());
//        province.setGDP(provinceDto.getGdp());
//
//        // Update districts information
//        for (DistrictDto districtDto : districtDtos) {
//            District district = districtReponsitory.findById(districtDto.getId())
//                    .orElseThrow(() -> new EntityNotFoundException("Cannot find district with ID: " + districtDto.getId()));
//            district.setName(districtDto.getName());
//            district.setCode(districtDto.getCode());
//            district.setArea(districtDto.getArea());
//            district.setPopulation(districtDto.getPopulation());
//            district.setGDP(districtDto.getGDP());
//            districtReponsitory.save(district);
//        }
//        reponsitory.save(province);
//    }

}
