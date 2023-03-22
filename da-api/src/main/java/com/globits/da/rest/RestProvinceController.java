package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;
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
    @RequestMapping(value = "/addProvince", method = RequestMethod.POST)
    public ResponseEntity<ProvinceDto> save(@RequestBody ProvinceDto provinceDto) {
        ProvinceDto result = provinceService.saveOrUpdate(null, provinceDto);
        return new ResponseEntity<ProvinceDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/addProvince/{id}", method = RequestMethod.POST)
    public ResponseEntity<ProvinceDto> save(@RequestBody ProvinceDto provinceDto, @PathVariable UUID id) {
        ProvinceDto result = provinceService.saveOrUpdate(id, provinceDto);
        return new ResponseEntity<ProvinceDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/getAllProvince", method = RequestMethod.GET)
    public ResponseEntity<List<ProvinceDto>> getALlProvince() {
        List<ProvinceDto> resutl = provinceService.getAllProvince();
        return new ResponseEntity<List<ProvinceDto>>(resutl, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/addProvince/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProvinceDto> update(@RequestBody ProvinceDto provinceDto, @PathVariable UUID id) {
        ProvinceDto result = provinceService.update(id, provinceDto);
        return new ResponseEntity<ProvinceDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/searchProvince", method = RequestMethod.POST)
    public ResponseEntity<Page<ProvinceDto>> searchProvince(@RequestBody ProvinceSearchDto provinceSearchDto) {
        Page<ProvinceDto> page = this.provinceService.searchByPage(provinceSearchDto);
        return new ResponseEntity<Page<ProvinceDto>>(page, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/deleteProvince", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProvince(@RequestParam UUID id) {
        Boolean result = provinceService.deleteKho(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/addProvince2", method = RequestMethod.POST)
    public ResponseEntity<ProvinceDto> save2(@RequestBody ProvinceDto provinceDto) {
        ProvinceDto result = provinceService.addProvince(null, provinceDto);
        return new ResponseEntity<ProvinceDto>(result, HttpStatus.OK);
    }

}
