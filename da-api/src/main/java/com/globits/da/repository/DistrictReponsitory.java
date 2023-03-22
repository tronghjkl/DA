package com.globits.da.repository;

import com.globits.da.domain.Category;
import com.globits.da.domain.District;
import com.globits.da.dto.DistrictDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DistrictReponsitory extends JpaRepository<District, UUID> {
    @Query("select count(entity.id) from District entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);

    @Query("select new com.globits.da.dto.DistrictDto(ed) from District ed")
    Page<DistrictDto> getListPage(Pageable pageable);

    @Query("select new com.globits.da.dto.DistrictDto(ed) from District ed")
    List<DistrictDto> getAllDistrict();

    Category findOneByCode(String code);

    @Query("select new com.globits.da.dto.DistrictDto(ed) from District ed where ed.province.id = ?1")
    List<DistrictDto> getDistrictsByProvinceId(UUID id);
}
