package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Certificate;

public class CertificateDto extends BaseObjectDto {

    private String name;

    private String code;
//
//    private LocalDate startDate;
//
//    private LocalDate endDate;

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

//    public LocalDate getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//
//    public LocalDate getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }

    public CertificateDto() {
        super();
    }

    public CertificateDto(Certificate certificate) {
        if (certificate != null) {
            this.setId(certificate.getId());
            this.name = certificate.getName();
            this.code = certificate.getCode();
//            this.startDate = certificate.getStartDate();
//            this.endDate = certificate.getEndDate();
        }
    }
}
