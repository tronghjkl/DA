package com.globits.da.dto.search;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class EmployeeSearchDTO {
    private UUID id;
    private int pageIndex;
    private int pageSize;
    private String keyword;
    private Boolean voided;
    private UUID khoId;
    private String orderBy;
    private String text;
    private UUID productCategory;
    private Date fromDate;
    private Date toDate;
    private String name;
    private String code;
    private String age;
    private String email;
    private String phone;

}
