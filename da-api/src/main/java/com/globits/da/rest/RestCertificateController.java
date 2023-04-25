package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.search.CertificateSearchDto;
import com.globits.da.service.CertificateService;
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
    CertificateService certificateService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @GetMapping(value = "/get-all-certificate")
    public ResponObject<List<CertificateDto>> getAllCertificate() {
        return certificateService.getAllCertificate();
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/add-certicate")
    public ResponObject<CertificateDto> addCertificate(@RequestBody CertificateDto certificateDto) {
        return certificateService.add( certificateDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PutMapping(value = "/add-certificate/{id}")
    public ResponObject<CertificateDto> updateCertificate(@RequestBody CertificateDto certificateDto, @PathVariable UUID id) {
        return certificateService.update(id, certificateDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/search-certidicate")
    public ResponObject<Page<CertificateDto>> searchCertificate(@RequestBody CertificateSearchDto certificateSearchDto) {
        return certificateService.searchByPage(certificateSearchDto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @DeleteMapping(value = "/delete-certificate/{id}")
    public ResponObject<Boolean> delete(@PathVariable UUID id) {
        return certificateService.deleteKho(id);
    }
}
