package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.search.CertificateSearchDto;
import com.globits.da.service.impl.CertificateServiceImpl;
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
@RequestMapping("/api/certificate")
@XmlRootElement
public class RestCertificateController {
    @Autowired
    CertificateServiceImpl certificateService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/getAllCertificate", method = RequestMethod.GET)
    public ResponseEntity<List<CertificateDto>> getAllCertificate() {
        List<CertificateDto> result = certificateService.getAllCertificate();
        return new ResponseEntity<List<CertificateDto>>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/addCertificate", method = RequestMethod.POST)
    public ResponseEntity<CertificateDto> addCertificate(@RequestBody CertificateDto certificateDto) {
        CertificateDto result = certificateService.saveOrUpdate(null, certificateDto);
        return new ResponseEntity<CertificateDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/addCertificate/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CertificateDto> updateCertificate(@RequestBody CertificateDto certificateDto, @PathVariable UUID id) {
        CertificateDto result = certificateService.saveOrUpdate(id, certificateDto);
        return new ResponseEntity<CertificateDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/searchCertificate", method = RequestMethod.POST)
    public ResponseEntity<Page<CertificateDto>> searchCertificate(@RequestBody CertificateSearchDto certificateSearchDto) {
        Page<CertificateDto> page = certificateService.searchByPage(certificateSearchDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/deleteCertificate/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        Boolean result = certificateService.deleteKho(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }
}
