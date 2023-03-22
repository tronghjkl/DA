package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProvinceService extends GenericService<Province, UUID> {
    public Page<ProvinceDto> getPage(int pageSize, int pageIndex);

    public ProvinceDto saveOrUpdate(UUID id, ProvinceDto dto);

    public Boolean deleteKho(UUID id);

    public ProvinceDto getCertificate(UUID id);

    Page<ProvinceDto> searchByPage(ProvinceSearchDto dto);

    Boolean checkCode(UUID id, String code);

    public List<ProvinceDto> getAllProvince();

    public Boolean deleteCheckById(UUID id);

    public ProvinceDto update(UUID id, ProvinceDto dto);

    public ProvinceDto addProvince(UUID id,ProvinceDto dto);
}
