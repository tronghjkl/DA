package com.globits.da.repository;

import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.dto.EmployeeCertificateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeCertifcateResponsitory extends JpaRepository<EmployeeCertificate, UUID> {
    @Query("select ec from EmployeeCertificate ec where ec.employee.id =?1 and ec.province.id = ?2 and ec.certificate.id = ?3")
    List<EmployeeCertificateDto> checkCertificateProvince(UUID employeeId, UUID provinceId, UUID certificateId);

    @Query("select ec from EmployeeCertificate ec where ec.employee.id =?1  and ec.certificate.id = ?2")
    List<EmployeeCertificateDto> checkCertificate(UUID employeeId,UUID certificateId);
}
