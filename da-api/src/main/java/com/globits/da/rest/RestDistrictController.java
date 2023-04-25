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
    @GetMapping(value = "/get-all-district")
    public ResponObject<List<DistrictDto>> getAllDistrict() {
        return districtService.getAll();
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/add-district")
    public ResponObject<DistrictDto> addDistrict(@RequestBody DistrictDto districtDto) {
        return districtService.add(districtDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PutMapping(value = "/update/{id}")
    public ResponObject<DistrictDto> updateDistrict(@RequestBody DistrictDto districtDto, @PathVariable UUID id) {
        return districtService.update(id, districtDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/search-district")
    public ResponseEntity<Page<DistrictDto>> searchDistrict(@RequestBody DistrictSearchDto districtSearchDto) {
        Page<DistrictDto> page = districtService.searchByPage(districtSearchDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @DeleteMapping(value = "/delete-district/{id}")
    public ResponObject<Boolean> deleteDistrict(@PathVariable UUID id) {
        return districtService.deleteKho(id);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/add-district2")
    public ResponObject<DistrictDto> addDistrictWithProvinceId(@RequestBody DistrictDto districtDto) {
        return districtService.saveWithProvinceId(districtDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @GetMapping(value = "/search-district-by-provinceId")
    public ResponObject<List<DistrictDto>> getDistrictWithProvinceId(@RequestParam UUID provinceId) {
        return districtService.getDistrictByProvinceId(provinceId);
    }


}
