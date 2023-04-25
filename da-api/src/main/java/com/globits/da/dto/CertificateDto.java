package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Certificate;

import java.util.UUID;

public class CertificateDto extends BaseObjectDto {

    private String name;

    private String code;


    private UUID provinceId;

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


    public CertificateDto() {
        super();
    }

    public CertificateDto(Certificate certificate) {
        if (certificate != null) {
            this.setId(certificate.getId());
            this.name = certificate.getName();
            this.code = certificate.getCode();

        }
    }
}
