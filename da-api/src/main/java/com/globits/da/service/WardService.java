package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Ward;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.WardSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WardService extends GenericService<Ward, UUID> {
    Page<WardDto> getPage(int pageSize, int pageIndex);

    WardDto saveOrUpdate(UUID id, WardDto dto);

    Boolean deleteKho(UUID id);

    WardDto getCertificate(UUID id);

    Page<WardDto> searchByPage(WardSearchDto dto);

    Boolean checkCode(UUID id, String code);

    List<WardDto> getAllWard();

    Boolean deleteCheckById(UUID id);
}
