package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.excel.EmployeeExcelExporter;
import com.globits.da.excel.ResponObject;
import com.globits.da.service.EmployeeService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
public class RestEmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Page<EmployeeDTO>> getpage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<EmployeeDTO> results = employeeService.getPage(pageSize, pageIndex);
        return new ResponseEntity<Page<EmployeeDTO>>(results, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponObject<EmployeeDTO> save(@RequestBody @Valid EmployeeDTO dto) {
        ResponObject<EmployeeDTO> employeeDTOResponObject = employeeService.addEmployee2(dto);
        return employeeDTOResponObject;
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/getAllEmployee", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        List<EmployeeDTO> result = employeeService.getAllEmployee();
        return new ResponseEntity<List<EmployeeDTO>>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/searchEmployee", method = RequestMethod.POST)
    public ResponseEntity<Page<EmployeeDTO>> searchByPage(@RequestBody EmployeeSearchDTO employeeSearchDTO) {
        Page<EmployeeDTO> page = employeeService.searchEmployee(employeeSearchDTO);
        return new ResponseEntity<Page<EmployeeDTO>>(page, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        Boolean result = employeeService.deleteEmpployee(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/exportEmployee", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportEmployees() throws IOException {
        List<EmployeeDTO> result = employeeService.getAllEmployee();
        EmployeeExcelExporter exporter = new EmployeeExcelExporter();
        exporter.export(result, "employees.xlsx");

        byte[] bytes = FileUtils.readFileToByteArray(new File("employees.xlsx"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "employees.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ResponObject<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO dto) {
        return employeeService.addEmployee(dto);
    }


    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponObject<EmployeeDTO> update(@PathVariable UUID id, @RequestBody EmployeeDTO dto){
        return employeeService.update(id, dto);
    }
}
