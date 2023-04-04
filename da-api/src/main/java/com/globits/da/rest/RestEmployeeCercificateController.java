package com.globits.da.rest;

import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.service.EmployeeCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ec")
public class RestEmployeeCercificateController {
    @Autowired
    EmployeeCertificateService employeeCertificateService;

    @RequestMapping(value = "/add-certificate", method = RequestMethod.POST)
    ResponObject<EmployeeCertificateDto> add(@RequestBody EmployeeCertificateDto dto) {
        return employeeCertificateService.save(dto);
    }
}
