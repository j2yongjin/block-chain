package com.daou.books.core.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyModel {

    private String companyCode;

    private String companyName;

    private String adminName;   // superAdmin

    private String adminId;     // superAdmin

    private String adminPw;     // superAdmin

}
