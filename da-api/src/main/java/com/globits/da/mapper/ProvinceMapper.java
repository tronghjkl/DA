package com.globits.da.mapper;

import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;

public interface ProvinceMapper {
    public void map(ProvinceDto source, Province destination);
}
