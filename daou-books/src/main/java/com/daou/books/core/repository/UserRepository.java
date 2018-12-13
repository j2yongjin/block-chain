package com.daou.books.core.repository;

import com.daou.books.core.domain.Company;
import com.daou.books.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByRole(User.UserRole role, Pageable pageable);

    Page<User> findByCompany(Company company, Pageable pageable);

    Page<User> findByCompanyAndRole(Company company, User.UserRole role, Pageable pageable);

    User findByLoginId(String loginId);

}
