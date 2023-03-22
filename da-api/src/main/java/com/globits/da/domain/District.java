package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "tbl_district")
@XmlRootElement
public class District extends BaseObject {
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
    private Double GDP;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Province province;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Ward> wards;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
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
        return GDP;
    }

    public void setGDP(Double GDP) {
        this.GDP = GDP;
    }


}
