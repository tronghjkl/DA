package com.globits.da.repository;

import com.globits.da.domain.EmployeeCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeCertifcateResponsitory extends JpaRepository<EmployeeCertificate, UUID> {
    @Query("select ec from EmployeeCertificate ec where ec.employee.id =?1 and ec.province.id = ?2 and ec.certificate.id = ?3")
    Boolean checkCertificate(UUID employeeId, UUID provinceId, UUID certificateId);

}
