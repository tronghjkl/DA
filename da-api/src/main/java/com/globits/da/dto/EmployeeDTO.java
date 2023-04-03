package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Employee;

import javax.validation.constraints.*;
import java.util.UUID;

public class EmployeeDTO extends BaseObjectDto {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "duplicate code")
    @Size(min = 6, max = 10, message = "code length from 6 to 10 characters")
    private String code;
    @NotBlank(message = "email is required")
    @Email(message = "email is required")
    private String email;
    @NotBlank(message = "phone is required")
    @Pattern(regexp = "\\d{10,11}", message = "phone contains max 11 numbers and min 10 numbers")
    private String phone;
    @Min(value = 0, message = "age cannot be negative")
    private Integer age;

    private UUID provinceId;
    private UUID districtId;
    private UUID wardId;

    public UUID getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(UUID provinceId) {
        this.provinceId = provinceId;
    }

    public UUID getDistrictId() {
        return districtId;
    }

    public void setDistrictId(UUID districtId) {
        this.districtId = districtId;
    }

    public UUID getWardId() {
        return wardId;
    }

    public void setWardId(UUID wardId) {
        this.wardId = wardId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public EmployeeDTO() {
        super();
    }

    // check employee is exist
    public EmployeeDTO(Employee employee) {
        if (employee != null) {
            this.setId(employee.getId());
            this.code = employee.getCode();
            this.name = employee.getName();
            this.email = employee.getEmail();
            this.phone = employee.getPhone();
            this.age = employee.getAge();
            if (employee.getProvince() != null && employee.getDistrict() != null && employee.getWard() != null) {
                this.provinceId = employee.getProvince().getId();
                this.districtId = employee.getDistrict().getId();
                this.wardId = employee.getWard().getId();
            }
        }
    }

}
