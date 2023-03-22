package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.District;
import com.globits.da.domain.Ward;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DistrictDto extends BaseObjectDto {
    private String name;

    private String code;

    private BigInteger population;

    private Double area;

    private Double GDP;

    private UUID provinceId;
    private List<WardDto> wards;
    private List<EmployeeDTO> employeeDTOS;

    public List<EmployeeDTO> getEmployeeDTOS() {
        return employeeDTOS;
    }

    public void setEmployeeDTOS(List<EmployeeDTO> employeeDTOS) {
        this.employeeDTOS = employeeDTOS;
    }

    public List<WardDto> getWards() {
        return wards;
    }

    public void setWards(List<WardDto> wards) {
        this.wards = wards;
    }

    public UUID getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(UUID provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigInteger getPopulation() {
        return population;
    }

    public void setPopulation(BigInteger population) {
        this.population = population;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getGDP() {
        return GDP;
    }

    public void setGDP(Double GDP) {
        this.GDP = GDP;
    }

//    public DistrictDto(District district) {
//        if (district != null) {
//            this.setId(district.getId());
//            this.name = district.getName();
//            this.code = district.getCode();
//            this.population = district.getPopulation();
//            this.area = district.getArea();
//            this.GDP = district.getGDP();
//            this.provinceId = district.getProvince().getId();
//
//        }
//    }

    public DistrictDto(District district) {
        if (district != null) {
            this.setId(district.getId());
            this.name = district.getName();
            this.code = district.getCode();
            this.population = district.getPopulation();
            this.area = district.getArea();
            this.GDP = district.getGDP();
//            this.provinceId = district.getProvince().getId();
            // ward
            this.wards = new ArrayList<>();
            if (district.getWards() != null) {
                for (Ward ward : district.getWards()) {
                    WardDto wardDto = new WardDto(ward);
                    this.wards.add(wardDto);
                }
            }
        }
    }

    public DistrictDto() {
        super();
    }
}
