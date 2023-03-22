package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Ward;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public class WardDto extends BaseObjectDto {
    private String name;

    private String code;

    private BigInteger population;

    private Double area;

    private UUID districtId;

    private List<EmployeeDTO> employeeDTOS;

    public List<EmployeeDTO> getEmployeeDTOS() {
        return employeeDTOS;
    }

    public void setEmployeeDTOS(List<EmployeeDTO> employeeDTOS) {
        this.employeeDTOS = employeeDTOS;
    }

    public UUID getDistrictId() {
        return districtId;
    }

    public void setDistrictId(UUID districtId) {
        this.districtId = districtId;
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

    public WardDto() {
        super();
    }

    public WardDto(Ward ward) {
        if (ward != null) {
            this.setId(ward.getId());
            this.name = ward.getName();
            this.code = ward.getCode();
            this.population = ward.getPopulation();
            this.area = ward.getArea();
            // ward
            this.districtId = ward.getDistrict().getId();
        }
    }
}
