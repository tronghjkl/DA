package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.search.CertificateSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CertificateService extends GenericService<Certificate, UUID> {
    public Page<CertificateDto> getPage(int pageSize, int pageIndex);

    public CertificateDto saveOrUpdate(UUID id, CertificateDto dto);

    public Boolean deleteKho(UUID id);

    public CertificateDto getCertificate(UUID id);

    Page<CertificateDto> searchByPage(CertificateSearchDto dto);

    Boolean checkCode(UUID id, String code);

    public List<CertificateDto> getAllCertificate();

    public Boolean deleteCheckById(UUID id);
}
