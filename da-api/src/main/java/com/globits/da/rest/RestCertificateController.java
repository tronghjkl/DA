package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.search.CertificateSearchDto;
import com.globits.da.service.impl.CertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/certificate")
@XmlRootElement
public class RestCertificateController {
    @Autowired
    CertificateServiceImpl certificateService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/get-all-certificate", method = RequestMethod.GET)
    public ResponObject<List<CertificateDto>> getAllCertificate() {
        return certificateService.getAllCertificate();
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-certicate", method = RequestMethod.POST)
    public ResponObject<CertificateDto> addCertificate(@RequestBody CertificateDto certificateDto) {
        return certificateService.add( certificateDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/add-certificate/{id}", method = RequestMethod.PUT)
    public ResponObject<CertificateDto> updateCertificate(@RequestBody CertificateDto certificateDto, @PathVariable UUID id) {
        return certificateService.update(id, certificateDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/search-certidicate", method = RequestMethod.POST)
    public ResponObject<Page<CertificateDto>> searchCertificate(@RequestBody CertificateSearchDto certificateSearchDto) {
        return certificateService.searchByPage(certificateSearchDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/delete-certificate/{id}", method = RequestMethod.DELETE)
    public ResponObject<Boolean> delete(@PathVariable UUID id) {
        return certificateService.deleteKho(id);
    }
}
