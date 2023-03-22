package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.EmployeeCertificate;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeCertificateDto extends BaseObjectDto {
    LocalDate startDate;
    LocalDate endDate;
    UUID provinceId;
    UUID employeeId;
    UUID certificateId;


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public UUID getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(UUID provinceId) {
        this.provinceId = provinceId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(UUID certificateId) {
        this.certificateId = certificateId;
    }


    public EmployeeCertificateDto() {
        super();
    }

    public EmployeeCertificateDto(EmployeeCertificate entity) {
        if (entity != null) {
            this.setId(entity.getId());
            this.startDate = entity.getStartDate();
            this.endDate = entity.getEndDate();
            this.provinceId = entity.getProvince().getId();
            this.employeeId = entity.getEmployee().getId();
            this.certificateId = entity.getCertificate().getId();
        }
    }
}
