package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.domain.baseObject.ResponObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProvinceService extends GenericService<Province, UUID> {
    Page<ProvinceDto> getPage(int pageSize, int pageIndex);

    ResponObject<Boolean> deleteKho(UUID id);


    ResponObject<Page<ProvinceDto>> searchByPage(ProvinceSearchDto dto);


    ResponObject<List<ProvinceDto>> getAll();


    ResponObject<ProvinceDto> update(UUID id, ProvinceDto dto);

    ResponObject<ProvinceDto> add( ProvinceDto dto);
}
