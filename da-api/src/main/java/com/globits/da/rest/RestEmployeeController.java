package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.domain.baseObject.ResponObject;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.service.EmployeeService;
import com.globits.da.service.ExcelService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    ExcelService excelService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @GetMapping(value = "/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<EmployeeDTO>> getpage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<EmployeeDTO> results = employeeService.getPage(pageSize, pageIndex);
        if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/save")
    public ResponObject<EmployeeDTO> save(@RequestBody @Valid EmployeeDTO dto) {
        return employeeService.addEmployeeWithAddress(dto);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @GetMapping(value = "/get-all-employee")
    public ResponObject<List<EmployeeDTO>> getAllEmployee() {
        return employeeService.getAll();
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @GetMapping(value = "/search-employee")
    public ResponObject<Page<EmployeeDTO>> searchByPage(@RequestBody EmployeeSearchDTO employeeSearchDTO) {
        return employeeService.searchByPage(employeeSearchDTO);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @DeleteMapping(value = "/{id}")
    public ResponObject<Boolean> deleteEmployee(@PathVariable UUID id) {
        return employeeService.deleteKho(id);
    }

    @GetMapping(value = "/export-employee")
    public ResponObject<byte[]> exportEmployees2(@RequestParam String fileName) throws IOException {
        List<EmployeeDTO> result = (List<EmployeeDTO>) employeeService.getAll();
        excelService.export(result, fileName);
        byte[] bytes = FileUtils.readFileToByteArray(new File(fileName));
        return new ResponObject<>("export succesfully", bytes);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PostMapping(value = "/add-employee")
    public ResponObject<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO dto) {
        return employeeService.add(dto);
    }


    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @PutMapping(value = "/update/{id}")
    public ResponObject<EmployeeDTO> updateEmployee(@PathVariable UUID id, @RequestBody EmployeeDTO dto) {
        return employeeService.update(id, dto);
    }

    @PostMapping("/upload")
    public ResponObject<List<EmployeeDTO>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return excelService.readExcelData(file);
    }
}
