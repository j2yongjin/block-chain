package com.daou.books.core.domain.model;

import com.daou.books.core.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserModel {

    private Long id;

    private String loginId;

    private String password;

    private String name;

    private CompanyModel company;

    private User.UserRole role;

    public UserModel(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.loginId = user.getLoginId();
        this.role = user.getRole();
        this.company = new CompanyModel(user.getCompany());
    }

    public Long getCompanyId() {
        return this.company.getId();
    }
}
