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
    @RequestMapping(value = "/get-all-province", method = RequestMethod.GET)
    public ResponObject<List<ProvinceDto>> getALlProvince() {
        return provinceService.getAll();
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/search-province", method = RequestMethod.POST)
    public ResponObject<Page<ProvinceDto>> searchProvince(@RequestBody ProvinceSearchDto provinceSearchDto) {
        return this.provinceService.searchByPage(provinceSearchDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/delete-province", method = RequestMethod.DELETE)
    public ResponObject<Boolean> deleteProvince(@RequestParam UUID id) {
        return provinceService.deleteKho(id);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-province", method = RequestMethod.POST)
    public ResponObject<ProvinceDto> addProvince(@RequestBody ProvinceDto provinceDto) {
        return provinceService.add(provinceDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-province/{id}", method = RequestMethod.PUT)
    public ResponObject<ProvinceDto> updateProvince(@RequestBody ProvinceDto provinceDto, @PathVariable UUID id) {
        return provinceService.update(id, provinceDto);
    }

}
