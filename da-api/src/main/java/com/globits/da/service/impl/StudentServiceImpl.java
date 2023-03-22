package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Student;
import com.globits.da.dto.StudentDto;
import com.globits.da.dto.search.StudentSearchDto;
import com.globits.da.repository.StudentRepository;
import com.globits.da.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl extends GenericServiceImpl<Student, UUID> implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Page<StudentDto> getPage(int pageSize, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        return studentRepository.getListPage(pageable);
    }

    @Override
    public StudentDto saveOrUpdate(UUID id, StudentDto dto) {
        if (dto != null) {
            Student student = null;
            if (dto.getId() != null) {
                if (!dto.getId().equals(id)) {
                    return null;
                }
                student = studentRepository.getOne(id);
            }
            if (student == null) {
                student = new Student();
            }

            student.setCode(dto.getCode());
            student.setName(dto.getName());
            student.setAge(dto.getAge());
            student.setGpa(dto.getGpa());
            student.setUniversityName(dto.getUniversityName());
            student.setFaculty(dto.getFaculty());

            student = studentRepository.save(student);

            if (student != null) {
                return new StudentDto(student);
            }
        }
        return null;
    }

    @Override
    public List<StudentDto> getAllStudent() {
        List<StudentDto> studentList = studentRepository.getAllStudent();
        return studentList;
    }

    @Override
    public Boolean deleteCheckById(UUID id) {
       return null;
    }

    @Override
    public Boolean deleteKho(UUID id) {
        if (id != null){
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<StudentDto> searchByPage(StudentSearchDto studentDto) {
        if (studentDto == null) {
            return null;
        }

        int pageIndex = studentDto.getPageIndex();
        int pageSize = studentDto.getPageSize();

        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }

        String whereClause = "";

        String orderBy = " ORDER BY entity.createDate DESC";

        String sqlCount = "select count(entity.id) from  Student as entity where (1=1)";

        String sql = "select new com.globits.da.dto.StudentDto(entity) from  Student as entity where (1=1)  ";

        if (studentDto.getKeyword() != null && StringUtils.hasText(studentDto.getKeyword())) {
            whereClause += "AND (entity.name LIKE :text OR entity.code LIKE :text OR entity.age LIKE :text OR entity.universityName LIKE :text OR entity.gpa LIKE :text OR entity.faculty LIKE :text )";
        }

        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query query = manager.createQuery(sql, StudentDto.class);
        Query qCount = manager.createQuery(sqlCount);
        if (studentDto.getKeyword() != null && StringUtils.hasText(studentDto.getKeyword())) {
            query.setParameter("text", '%' + studentDto.getKeyword() + '%');
            qCount.setParameter("text", '%' + studentDto.getKeyword() + '%');
        }
        int startPosition = pageIndex * pageSize;
        query.setFirstResult(startPosition);
        query.setMaxResults(pageSize);
        List<StudentDto> entities = query.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<StudentDto> result = new PageImpl<StudentDto>(entities, pageable, count);
        return result;
    }
}
