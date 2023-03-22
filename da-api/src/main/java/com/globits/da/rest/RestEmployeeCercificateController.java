package com.globits.da.rest;

import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.service.EmployeeCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ec")
public class RestEmployeeCercificateController {
    @Autowired
    EmployeeCertificateService employeeCertificateService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<EmployeeCertificateDto> add(@RequestBody EmployeeCertificateDto dto) {
        EmployeeCertificateDto result = employeeCertificateService.saveOrUpdate(null, dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
