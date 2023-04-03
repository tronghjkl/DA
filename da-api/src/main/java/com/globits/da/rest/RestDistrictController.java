package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.search.DistrictSearchDto;
import com.globits.da.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/district/")
@XmlRootElement
public class RestDistrictController {
    @Autowired
    DistrictService districtService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/get-all-district", method = RequestMethod.GET)
    public ResponseEntity<List<DistrictDto>> getAllDistrict() {
        List<DistrictDto> result = districtService.getAllDistrict();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-district", method = RequestMethod.POST)
    public ResponseEntity<DistrictDto> addDistrict(@RequestBody DistrictDto districtDto) {
        DistrictDto result = districtService.saveOrUpdate(null, districtDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-district/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DistrictDto> updateDistrict(@RequestBody DistrictDto districtDto, @PathVariable UUID id) {
        DistrictDto result = districtService.saveOrUpdate(id, districtDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DistrictDto> update(@RequestBody DistrictDto districtDto, @PathVariable UUID id) {
        DistrictDto result = districtService.update(id, districtDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/search-district", method = RequestMethod.POST)
    public ResponseEntity<Page<DistrictDto>> searchDistrict(@RequestBody DistrictSearchDto districtSearchDto) {
        Page<DistrictDto> page = districtService.searchByPage(districtSearchDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/delete-district/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        Boolean result = districtService.deleteKho(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-district2", method = RequestMethod.POST)
    public ResponObject<DistrictDto> addDistrict2(@RequestBody DistrictDto districtDto) {
        return districtService.saveOrUpdate2(null, districtDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/search-district-by-provinceId", method = RequestMethod.GET)
    public ResponObject<List<DistrictDto>> addDistrict2(@RequestParam UUID provinceId) {
        ResponObject result = districtService.getDistrictByProvinceId(provinceId);
        return new ResponObject<>(result.getMessager(), result.getStatus(), result.getCode(), (List<DistrictDto>) result.getData());
    }


}
