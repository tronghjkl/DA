package com.globits.da.rest;

import com.globits.da.AFFakeConstants;
import com.globits.da.dto.StudentDto;
import com.globits.da.dto.search.StudentSearchDto;
import com.globits.da.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
public class RestStudentController {
    @Autowired
    StudentService studentService;

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<Page<StudentDto>> getPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        Page<StudentDto> result = studentService.getPage(pageSize, pageIndex);
        return new ResponseEntity<Page<StudentDto>>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/getAllStudent", method = RequestMethod.GET)
    public ResponseEntity<List<StudentDto>> getAllStudent() {
        List<StudentDto> result = studentService.getAllStudent();
        return new ResponseEntity<List<StudentDto>>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public ResponseEntity<StudentDto> save(@RequestBody StudentDto dto) {
        StudentDto result = studentService.saveOrUpdate(null, dto);
        return new ResponseEntity<StudentDto>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/searchStudent", method = RequestMethod.POST)
    public ResponseEntity<Page<StudentDto>> searchStudent(@RequestBody StudentSearchDto dto) {
        Page<StudentDto> result = studentService.searchByPage(dto);
        return new ResponseEntity<Page<StudentDto>>(result, HttpStatus.OK);
    }

    @Secured({AFFakeConstants.ROLE_ADMIN, AFFakeConstants.ROLE_SUPER_ADMIN})
    @RequestMapping(value = "/deleteStudent", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteStudent(@RequestParam UUID id) {
        Boolean result = studentService.deleteKho(id);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

}
