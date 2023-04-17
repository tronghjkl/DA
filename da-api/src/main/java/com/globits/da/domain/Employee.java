package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@Table(name = "tbl_employee")
@XmlRootElement
@Setter
@Getter
public class Employee extends BaseObject {

    @Column(name = "code")
    String code;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Column(name = "phone")
    String phone;
    @Column(name = "age")
    int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Ward ward;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmployeeCertificate> employeeCertificates;

//    public List<EmployeeCertificate> getEmployeeCertificate() {
//        return employeeCertificates;
//    }
//
//    public void setEmployeeCertificate(List<EmployeeCertificate> employeeCertificates) {
//        this.employeeCertificates = employeeCertificates;
//    }
//
//    public Province getProvince() {
//        return province;
//    }
//
//    public void setProvince(Province province) {
//        this.province = province;
//    }
//
//    public District getDistrict() {
//        return district;
//    }
//
//    public void setDistrict(District district) {
//        this.district = district;
//    }
//
//    public Ward getWard() {
//        return ward;
//    }
//
//    public void setWard(Ward ward) {
//        this.ward = ward;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//
//        this.code = code;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
}
