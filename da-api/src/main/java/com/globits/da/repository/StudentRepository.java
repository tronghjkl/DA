package com.globits.da.repository;

import com.globits.da.domain.Student;
import com.globits.da.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    @Query("select new com.globits.da.dto.StudentDto(student) from Student student")
    Page<StudentDto> getListPage(Pageable pageable);

    @Query("select new com.globits.da.dto.StudentDto(student) from Student student")
    List<StudentDto> getAllStudent();
}
