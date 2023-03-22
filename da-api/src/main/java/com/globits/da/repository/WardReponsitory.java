package com.globits.da.repository;

import com.globits.da.domain.Ward;
import com.globits.da.dto.WardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WardReponsitory extends JpaRepository<Ward, UUID> {
    @Query("select count(entity.id) from Ward entity where entity.code =?1 and (entity.id <> ?2 or ?2 is null) ")
    Long checkCode(String code, UUID id);

    @Query("select new com.globits.da.dto.WardDto(ed) from Ward ed")
    Page<WardDto> getListPage(Pageable pageable);

    @Query("select new com.globits.da.dto.WardDto(ed) from Ward ed")
    List<WardDto> getAllWard();

    Ward findOneByCode(String code);
}
