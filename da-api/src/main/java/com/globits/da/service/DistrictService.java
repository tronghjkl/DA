package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.District;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.search.DistrictSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface DistrictService extends GenericService<District, UUID> {
    public Page<DistrictDto> getPage(int pageSize, int pageIndex);

    public DistrictDto saveOrUpdate(UUID id, DistrictDto dto);

    public Boolean deleteKho(UUID id);

    public DistrictDto getCertificate(UUID id);

    Page<DistrictDto> searchByPage(DistrictSearchDto dto);

    Boolean checkCode(UUID id, String code);

    public List<DistrictDto> getAllDistrict();

    public Boolean deleteCheckById(UUID id);

    public DistrictDto saveOrUpdate2(UUID id, DistrictDto dto);

    public DistrictDto update(UUID id, DistrictDto dto);


}
