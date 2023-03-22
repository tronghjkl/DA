package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@Table(name = "tbl_certificate")
@XmlRootElement
public class Certificate extends BaseObject {
    private static final long serialVersionUID = 1L;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
//    @Column(name = "start_date", nullable = true)
//    private LocalDate startDate;
//    @Column(name = "end_date", nullable = true)
//    private LocalDate endDate;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL)
    private List<Employee> employees;


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

//    public LocalDate getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//
//    public LocalDate getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }
}
