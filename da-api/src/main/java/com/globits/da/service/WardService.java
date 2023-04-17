package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Ward;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.WardSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;


public interface WardService extends GenericService<Ward, UUID> {
    Page<WardDto> getPage(int pageSize, int pageIndex);

    ResponObject<WardDto> add( WardDto dto);

    ResponObject<WardDto> update(UUID id, WardDto dto);

    ResponObject<Boolean> deleteKho(UUID id);

    ResponObject<Page<WardDto>> searchByPage(WardSearchDto dto);

    ResponObject<List<WardDto>> getAll();

}
