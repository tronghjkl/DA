package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.search.CertificateSearchDto;
import com.globits.da.domain.baseObject.ResponObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CertificateService extends GenericService<Certificate, UUID> {
    public Page<CertificateDto> getPage(int pageSize, int pageIndex);

    public ResponObject<CertificateDto> saveOrUpdate(UUID id, CertificateDto dto);

    public ResponObject<Boolean> deleteKho(UUID id);

    public CertificateDto getCertificate(UUID id);

    ResponObject<Page<CertificateDto>> searchByPage(CertificateSearchDto dto);

    Boolean checkCode(UUID id, String code);

    public ResponObject<List<CertificateDto>> getAllCertificate();

    public Boolean deleteCheckById(UUID id);
}
