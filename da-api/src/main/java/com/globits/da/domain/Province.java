package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "tbl_province")
@XmlRootElement
public class Province extends BaseObject {

    private static final long serialVersionUID = 1L;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "population")
    private BigInteger population;
    @Column(name = "area")
    private Double area;
    @Column(name = "gdp")
    private Double gdp;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<District> districts;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    private List<EmployeeCertificate> employeeCertificates;

    public List<EmployeeCertificate> getEmployeeCertificates() {
        return employeeCertificates;
    }

    public void setEmployeeCertificates(List<EmployeeCertificate> employeeCertificates) {
        this.employeeCertificates = employeeCertificates;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

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

    public BigInteger getPopulation() {
        return population;
    }

    public void setPopulation(BigInteger population) {
        this.population = population;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getGDP() {
        return gdp;
    }

    public void setGDP(Double gdp) {
        this.gdp = gdp;
    }
}
