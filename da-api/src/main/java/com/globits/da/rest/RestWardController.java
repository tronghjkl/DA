package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.WardDto;
import com.globits.da.dto.search.WardSearchDto;
import com.globits.da.service.WardService;
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
    WardService wardService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @GetMapping(value = "/get-all-ward")
    public ResponObject<List<WardDto>> getAllWard() {
        return wardService.getAll();
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/add-ward")
    public ResponObject<WardDto> addWard(@RequestBody WardDto wardDto) {
        return wardService.add(wardDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PutMapping(value = "/add-ward/{id}")
    public ResponObject<WardDto> updateWard(@RequestBody WardDto wardDto, @PathVariable UUID id) {
        return wardService.update(id, wardDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/search-ward")
    public ResponObject<Page<WardDto>> searchWard(@RequestBody WardSearchDto wardSearchDto) {
        return wardService.searchByPage(wardSearchDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @DeleteMapping(value = "/delete-ward/{id}")
    public ResponObject<Boolean> delete(@PathVariable UUID id) {
        return wardService.deleteKho(id);
    }
}
