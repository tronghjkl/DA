package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDto extends BaseObjectDto {
    private String name;

    private String code;

    private BigInteger population;

    private Double area;

    private Double gdp;

    private List<DistrictDto> districts;

    private List<EmployeeDTO> employeeDTOS;

    public List<EmployeeDTO> getEmployeeDTOS() {
        return employeeDTOS;
    }

    public void setEmployeeDTOS(List<EmployeeDTO> employeeDTOS) {
        this.employeeDTOS = employeeDTOS;
    }

    public List<DistrictDto> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictDto> districts) {
        this.districts = districts;
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

    public Double getGdp() {
        return gdp;
    }

    public void setGdp(Double gdp) {
        this.gdp = gdp;
    }

    public ProvinceDto() {
        super();
    }

    public ProvinceDto(Province province) {
        if (province != null) {
            this.setId(province.getId());
            this.name = province.getName();
            this.code = province.getCode();
            this.area = province.getArea();
            this.population = province.getPopulation();
            this.gdp = province.getGDP();
            // district
            this.districts = new ArrayList<>();
            if (province.getDistricts() != null) {
                for (District district : province.getDistricts()) {
                    DistrictDto districtDto = new DistrictDto(district);
                    this.districts.add(districtDto);
                }
            }
        }
    }
}
