package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Certificate;
import com.globits.da.domain.Employee;
import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.domain.Province;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.repository.CertificateReponsitory;
import com.globits.da.repository.EmployeeCertifcateResponsitory;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceReponsitory;
import com.globits.da.service.EmployeeCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponObject<EmployeeCertificateDto> save(EmployeeCertificateDto dto) {

        EmployeeCertificate entity = new EmployeeCertificate();

        List<EmployeeCertificateDto> employeeCertificateDtoList = responsitory.checkCertificateProvince(dto.getEmployeeId(), dto.getProvinceId(), dto.getCertificateId());

        List<EmployeeCertificateDto> employeeCertificateDtoList2 = responsitory.checkCertificate(dto.getEmployeeId(), dto.getCertificateId());

        if (!validateEmployeeCertificate(employeeCertificateDtoList) && !validateCertificate(employeeCertificateDtoList2)) {
            return new ResponObject<>("Add Failed", "BAD REQUEST", 400);
        }
        Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElse(null);
        Certificate certificate = certificateReponsitory.findById(dto.getCertificateId()).orElse(null);
        Province province = provinceReponsitory.findById(dto.getProvinceId()).orElse(null);

        if (employee == null && certificate == null && province == null) {
            return new ResponObject<>("Add Failed", "BAD REQUEST", 400);
        }
        entity.setEmployee(employee);
        entity.setCertificate(certificate);
        entity.setProvince(province);
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());

        entity = responsitory.save(entity);

        return new ResponObject<>("Add successfuly", "OK", 200, new EmployeeCertificateDto(entity));
    }

    //check still has time
    public Boolean validateEmployeeCertificate(List<EmployeeCertificateDto> dtos) {
        for (EmployeeCertificateDto dto : dtos) {
            LocalDate endDate = dto.getEndDate();
            int checkStillTime = endDate.compareTo(LocalDate.now());
            if (checkStillTime > 0) {
                return false;
            }
        }
        return true;
    }

    // check certificate of the same type
    public Boolean validateCertificate(List<EmployeeCertificateDto> dtos) {
        int count = 0;
        if (dtos.size() >= 3) {
            for (EmployeeCertificateDto dto : dtos) {
                LocalDate endDate = dto.getEndDate();
                int checkStillTime = endDate.compareTo(LocalDate.now());

                if (checkStillTime > 0) {
                    count++;
                }
                if (count >= 3) {
                    return false;
                }
            }
        }
        return true;
    }

}
