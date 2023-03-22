package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Student;
import com.globits.da.dto.StudentDto;
import com.globits.da.dto.search.StudentSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface StudentService extends GenericService<Student, UUID> {
    public Page<StudentDto> getPage(int pageSize, int pageIndex);

    public StudentDto saveOrUpdate(UUID id, StudentDto dto);

    public List<StudentDto> getAllStudent();

    public Boolean deleteCheckById(UUID id);

    public Boolean deleteKho(UUID id);

    public Page<StudentDto> searchByPage(StudentSearchDto studentDto);


}
