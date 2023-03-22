package com.globits.da.repository;

import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CertificateReponsitory extends JpaRepository<Certificate, UUID> {

    @Query("select count(entity.id) from Certificate entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);
    @Query("select new com.globits.da.dto.CertificateDto(ed) from Certificate ed")
    Page<CertificateDto> getListPage(Pageable pageable);

    @Query("select new com.globits.da.dto.CertificateDto(ed) from Certificate ed")
    List<CertificateDto> getAllCertificate();

    Certificate findOneByCode(String code);
}
