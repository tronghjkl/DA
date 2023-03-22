package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.WardSearchDto;
import com.globits.da.service.impl.WardServiceImpl;
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
@RequestMapping("/api/ward")
@XmlRootElement
public class RestWardController {
    @Autowired
    WardServiceImpl wardService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/getAllWard", method = RequestMethod.GET)
    public ResponseEntity<List<WardDto>> getAllWard() {
        List<WardDto> result = wardService.getAllWard();
        return new ResponseEntity<List<WardDto>>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/addWard", method = RequestMethod.POST)
    public ResponseEntity<WardDto> addWard(@RequestBody WardDto wardDto) {
        WardDto result = wardService.saveOrUpdate(null, wardDto);
        return new ResponseEntity<WardDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/addWard/{id}", method = RequestMethod.PUT)
    public ResponseEntity<WardDto> updateWard(@RequestBody WardDto wardDto, @PathVariable UUID id) {
        WardDto result = wardService.saveOrUpdate(id, wardDto);
        return new ResponseEntity<WardDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/searchWard", method = RequestMethod.POST)
    public ResponseEntity<Page<WardDto>> searchWard(@RequestBody WardSearchDto wardSearchDto) {
        Page<WardDto> page = wardService.searchByPage(wardSearchDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/deleteWard/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        Boolean result = wardService.deleteKho(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
}
