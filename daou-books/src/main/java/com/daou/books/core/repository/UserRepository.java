package com.daou.books.core.repository;

import com.daou.books.core.domain.Company;
import com.daou.books.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByCompany(Company company);

    List<User> findByCompanyAndRole(Company company, User.UserRole role);

    User findByLoginId(String loginId);

    List<User> findByUserAndRole(User.UserRole role);

}
