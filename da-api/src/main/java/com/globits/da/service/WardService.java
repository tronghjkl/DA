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
    public Page<WardDto> getPage(int pageSize, int pageIndex);

    public WardDto saveOrUpdate(UUID id, WardDto dto);

    public Boolean deleteKho(UUID id);

    public WardDto getCertificate(UUID id);

    Page<WardDto> searchByPage(WardSearchDto dto);

    Boolean checkCode(UUID id, String code);

    public List<WardDto> getAllWard();

    public Boolean deleteCheckById(UUID id);
}
