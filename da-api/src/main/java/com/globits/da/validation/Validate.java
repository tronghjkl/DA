package com.globits.da.validation;

import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.domain.Ward;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

import static com.globits.da.constants.Constants.EMAIL_PATTERN;

@Component
public class Validate {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProvinceReponsitory provinceReponsitory;

    public ResponObject validateEmployee(EmployeeDTO dto) {

        if (dto.getCode().contains(" ")) {
            return new ResponObject(false, "the code does not contain spaces");
        }
        if (dto.getCode().length() < 6 || dto.getCode().length() > 10) {
            return new ResponObject(false, "code length from 6 to 10 characters");
        }
        if (existsByCode(dto.getCode())) {
            return new ResponObject(false, ": code already exist");
        }
        if (dto.getName() == null) {
            return new ResponObject(false, " name is required");
        }
        if (dto.getEmail() == null && !Pattern.matches(EMAIL_PATTERN, dto.getEmail())) {
            return new ResponObject(false, "email is required");
        }

        if (dto.getPhone() == null) {
            return new ResponObject(false, "Input phone");
        }
        if (dto.getPhone().length() > 11) {
            return new ResponObject(false, "phone contains max 11 numbers");
        }
        if (dto.getPhone().length() < 10) {
            return new ResponObject(false, "phone contains min 10 numbers");
        }

        if (dto.getAge() < 0) {
            return new ResponObject(false, "age cannot be negative");
        }

        return new ResponObject(true, "ok");

    }

    public boolean existsByCode(String code) {
        try {
            Employee employee = employeeRepository.getByCode(code);
            return employee != null;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean validateProvince(EmployeeDTO dto) {

        Province province = provinceReponsitory.findById(dto.getProvinceId()).orElse(null);

        if (province != null) {
            List<District> districts = province.getDistricts();
            for (District district : districts) {
                if (dto.getDistrictId().equals(district.getId())) {
                    List<Ward> wards = district.getWards();
                    for (Ward ward : wards) {
                        if (dto.getWardId().equals(ward.getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
