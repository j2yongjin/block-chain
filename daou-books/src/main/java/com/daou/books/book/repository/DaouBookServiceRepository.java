package com.daou.books.repository;

import com.daou.books.domain.DaouBookTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaouBookServiceRepository extends JpaRepository<DaouBookTest, Long> {
}
