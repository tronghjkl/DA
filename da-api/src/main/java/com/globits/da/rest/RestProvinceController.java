package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/province")
public class RestProvinceController {
    @Autowired
    ProvinceService provinceService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @GetMapping(value = "/get-all-province")
    public ResponObject<List<ProvinceDto>> getALlProvince() {
        return provinceService.getAll();
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/search-province")
    public ResponObject<Page<ProvinceDto>> searchProvince(@RequestBody ProvinceSearchDto provinceSearchDto) {
        return this.provinceService.searchByPage(provinceSearchDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @DeleteMapping(value = "/delete-province")
    public ResponObject<Boolean> deleteProvince(@RequestParam UUID id) {
        return provinceService.deleteKho(id);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/add-province")
    public ResponObject<ProvinceDto> addProvince(@RequestBody ProvinceDto provinceDto) {
        return provinceService.add(provinceDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PutMapping(value = "/add-province/{id}")
    public ResponObject<ProvinceDto> updateProvince(@RequestBody ProvinceDto provinceDto, @PathVariable UUID id) {
        return provinceService.update(id, provinceDto);
    }

}
