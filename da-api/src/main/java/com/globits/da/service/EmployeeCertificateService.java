package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.domain.baseObject.ResponObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EmployeeCertificateService extends GenericService<EmployeeCertificate, UUID> {
    public Page<EmployeeCertificateDto> getPage(int pageSize, int pageIndex);

    public ResponObject<EmployeeCertificateDto> save(EmployeeCertificateDto dto);

    public Boolean deleteKho(UUID id);

    public EmployeeCertificateDto getCertificate(UUID id);

    Boolean checkCode(UUID id, String code);

    public List<EmployeeCertificateDto> getAllEmployeeCertificate();

    public Boolean deleteCheckById(UUID id);
}