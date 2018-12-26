package com.daou.books.core.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyModel {

    private Long companyId;

    private String companyCode;

    private String companyName;

    private String adminName;   // superAdmin

    private String adminId;     // superAdmin

    private String adminPw;     // superAdmin

    //나중에 모델 정리하고 알려주세요.
    private String userName;

    private String userId;

    private String userPw;

}
