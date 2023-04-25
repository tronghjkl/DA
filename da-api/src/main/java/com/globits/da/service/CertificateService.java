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
    Page<CertificateDto> getPage(int pageSize, int pageIndex);

    ResponObject<CertificateDto> update(UUID id, CertificateDto dto);

    ResponObject<CertificateDto> add(CertificateDto dto);

    ResponObject<Boolean> deleteKho(UUID id);

    ResponObject<Page<CertificateDto>> searchByPage(CertificateSearchDto dto);

    ResponObject<List<CertificateDto>> getAllCertificate();


}
