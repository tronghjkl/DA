package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "tbl_ward")
@XmlRootElement
public class Ward extends BaseObject {
    private static final long serialVersionUID = 1L;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "population")
    private BigInteger population;
    @Column(name = "area")
    private Double area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private District district;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL)
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
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
}
