package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.WardSearchDto;
import com.globits.da.service.impl.WardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @RequestMapping(value = "/get-all-ward", method = RequestMethod.GET)
    public ResponObject<List<WardDto>> getAllWard() {
        return wardService.getAll();
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-ward", method = RequestMethod.POST)
    public ResponObject<WardDto> addWard(@RequestBody WardDto wardDto) {
        return wardService.add(wardDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-ward/{id}", method = RequestMethod.PUT)
    public ResponObject<WardDto> updateWard(@RequestBody WardDto wardDto, @PathVariable UUID id) {
        return wardService.update(id, wardDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/search-ward", method = RequestMethod.POST)
    public ResponObject<Page<WardDto>> searchWard(@RequestBody WardSearchDto wardSearchDto) {
        return wardService.searchByPage(wardSearchDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/delete-ward/{id}", method = RequestMethod.DELETE)
    public ResponObject<Boolean> delete(@PathVariable UUID id) {
        return wardService.deleteKho(id);
    }
}
