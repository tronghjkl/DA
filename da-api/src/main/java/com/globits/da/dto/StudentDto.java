package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Student;

public class StudentDto extends BaseObjectDto {
    private String name;
    private String code;
    private byte age;
    private String universityName;
    private float gpa;
    private String faculty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public StudentDto() {
        super();
    }

    public StudentDto(Student student) {
        if (student != null) {
            this.setId(student.getId());
            this.name = student.getName();
            this.code = student.getCode();
            this.age = student.getAge();
            this.universityName = student.getUniversityName();
            this.gpa = student.getGpa();
            this.faculty = student.getFaculty();
        }
    }

}
