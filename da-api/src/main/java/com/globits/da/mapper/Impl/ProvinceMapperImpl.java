package com.globits.da.mapper.Impl;

import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.mapper.ProvinceMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProvinceMapperImpl implements ProvinceMapper {
    @Override
    public void map(ProvinceDto source, Province destination) {
        if (source.getCode() != null) {
            destination.setCode(source.getCode());
        }
        if (source.getName() != null) {
            destination.setName(source.getName());
        }
        if (source.getArea() != null) {
            destination.setArea(source.getArea());
        }
        if (source.getPopulation() != null) {
            destination.setPopulation(source.getPopulation());
        }
        if (source.getGdp() != null) {
            destination.setGDP(source.getGdp());
        }
        if (source.getDistricts() != null) {
            // handle districts
            List<DistrictDto> districtDtoList = source.getDistricts();
            List<District> districts = new ArrayList<>();

            for (DistrictDto districtDto : districtDtoList) {
                District district = new District();
                district.setId(districtDto.getId());
                district.setCode(districtDto.getCode());
                district.setName(districtDto.getName());
                district.setPopulation(districtDto.getPopulation());
                district.setArea(districtDto.getArea());
                district.setGDP(districtDto.getGDP());
                districts.add(district);
            }
            destination.setDistricts(districts);
        }
    }
}
