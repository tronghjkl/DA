package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.District;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.search.DistrictSearchDto;
import com.globits.da.domain.baseObject.ResponObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface DistrictService extends GenericService<District, UUID> {
    Page<DistrictDto> getPage(int pageSize, int pageIndex);

    ResponObject<DistrictDto> save( DistrictDto dto);

    ResponObject<Boolean> deleteKho(UUID id);

    ResponObject<DistrictDto> getCertificate(UUID id);

    Page<DistrictDto> searchByPage(DistrictSearchDto dto);

    ResponObject<Boolean> checkCode(UUID id, String code);

    ResponObject<List<DistrictDto>> getAll();

    ResponObject<Boolean> deleteCheckById(UUID id);

    ResponObject<DistrictDto> saveWithProvinceId( DistrictDto dto);

    ResponObject<DistrictDto> update(UUID id, DistrictDto dto);

    ResponObject<List<DistrictDto>> getDistrictByProvinceId(UUID id);
}
