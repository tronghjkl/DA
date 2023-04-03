package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class EmployeeExcelDto extends BaseObjectDto {
    private String name;
    private String code;
    private String email;
    private String phone;
    private int age;
    private UUID provinceId;
    private UUID districtId;
    private UUID wardId;

    public EmployeeExcelDto(Employee entity) {
        this.name = entity.getName();
        this.code = entity.getCode();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.age = entity.getAge();
        this.provinceId = entity.getProvince().getId();
        this.districtId = entity.getDistrict().getId();
        this.wardId = entity.getWard().getId();
    }
}
