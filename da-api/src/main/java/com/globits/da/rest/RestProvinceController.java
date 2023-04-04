package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @RequestMapping(value = "/add-province", method = RequestMethod.POST)
    public ResponseEntity<ProvinceDto> save(@RequestBody ProvinceDto provinceDto) {
        ProvinceDto result = provinceService.saveOrUpdate(null, provinceDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-province/{id}", method = RequestMethod.POST)
    public ResponseEntity<ProvinceDto> save(@RequestBody ProvinceDto provinceDto, @PathVariable UUID id) {
        ProvinceDto result = provinceService.saveOrUpdate(id, provinceDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/get-all-province", method = RequestMethod.GET)
    public ResponObject<List<ProvinceDto>> getALlProvince() {
        return provinceService.getAllProvince();
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
    @RequestMapping(value = "/add-province2", method = RequestMethod.POST)
    public ResponObject<ProvinceDto> save2(@RequestBody ProvinceDto provinceDto) {
        return provinceService.addProvince(provinceDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-province/{id}", method = RequestMethod.PUT)
    public ResponObject<ProvinceDto> update(@RequestBody ProvinceDto provinceDto, @PathVariable UUID id) {
        return provinceService.updateProvince(id, provinceDto);
    }

}
