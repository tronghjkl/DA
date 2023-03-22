package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_employee")
@XmlRootElement
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "certificate_id")
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private Certificate certificate;

//    public Set<Certificate> getCertificates() {
//        return certificates;
//    }
//
//    public void setCertificates(Set<Certificate> certificates) {
//        this.certificates = certificates;
//    }
//
//    @ManyToMany(mappedBy = "employees")
//    private Set<Certificate> certificates = new HashSet<>();



    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
