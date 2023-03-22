package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Certificate;
import com.globits.da.domain.Employee;
import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.domain.Province;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.repository.CertificateReponsitory;
import com.globits.da.repository.EmployeeCertifcateResponsitory;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceReponsitory;
import com.globits.da.service.EmployeeCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeCertificateServiceImpl extends GenericServiceImpl<EmployeeCertificate, UUID> implements EmployeeCertificateService {
    @Autowired
    EmployeeCertifcateResponsitory responsitory;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProvinceReponsitory provinceReponsitory;
    @Autowired
    CertificateReponsitory certificateReponsitory;

    @Override
    public Page<EmployeeCertificateDto> getPage(int pageSize, int pageIndex) {
        return null;
    }

    @Override
    public EmployeeCertificateDto saveOrUpdate(UUID id, EmployeeCertificateDto dto) {
        if (dto != null) {
            EmployeeCertificate entity = null;
            if (dto.getId() != null) {
                if (dto.getId() != null && !dto.getId().equals(id)) {
                    return null;
                }
                entity = responsitory.getOne(dto.getId());
            }
            if (entity == null) {
                entity = new EmployeeCertificate();
            }

            LocalDate startDate = dto.getStartDate();
            LocalDate endDate = dto.getEndDate();
            int checkStillTime = endDate.compareTo(LocalDate.now());

            if (responsitory.checkCertificate(dto.getEmployeeId(), dto.getProvinceId(), dto.getCertificateId()) && startDate != null && checkStillTime > 0) {
                Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
                Certificate certificate = certificateReponsitory.findById(dto.getCertificateId()).orElse(null);
                Province province = provinceReponsitory.findById(dto.getProvinceId()).orElse(null);

                entity.setEmployee(employee);
                entity.setCertificate(certificate);
                entity.setProvince(province);
                entity.setStartDate(dto.getStartDate());
                entity.setEndDate(dto.getEndDate());

                entity = responsitory.save(entity);
                if (entity != null) {
                    return new EmployeeCertificateDto(entity);
                }
            }

        }
        return null;
    }

    @Override
    public Boolean deleteKho(UUID id) {
        return null;
    }

    @Override
    public EmployeeCertificateDto getCertificate(UUID id) {
        return null;
    }

    @Override
    public Boolean checkCode(UUID id, String code) {
        return null;
    }

    @Override
    public List<EmployeeCertificateDto> getAllEmployeeCertificate() {
        return null;
    }

    @Override
    public Boolean deleteCheckById(UUID id) {
        return null;
    }
}
