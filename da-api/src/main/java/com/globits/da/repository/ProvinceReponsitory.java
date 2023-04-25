package com.globits.da.repository;

import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProvinceReponsitory extends JpaRepository<Province, UUID> {
    @Query("select count(entity.id) from Province entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);

    @Query("select new com.globits.da.dto.ProvinceDto(ed) from Province ed")
    Page<ProvinceDto> getListPage(Pageable pageable);

    @Query("select new com.globits.da.dto.ProvinceDto(ed) from Province ed")
    List<ProvinceDto> getAllProvince();

}
