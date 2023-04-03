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

    ProvinceDto saveOrUpdate(UUID id, ProvinceDto dto);

    ResponObject<Boolean> deleteKho(UUID id);

    ProvinceDto getCertificate(UUID id);

    ResponObject<Page<ProvinceDto>> searchByPage(ProvinceSearchDto dto);

    Boolean checkCode(UUID id, String code);

    List<ProvinceDto> getAllProvince();

    Boolean deleteCheckById(UUID id);

    ResponObject<ProvinceDto> updateProvince(UUID id, ProvinceDto dto);

    ResponObject<ProvinceDto> addProvince(UUID id, ProvinceDto dto);
}
