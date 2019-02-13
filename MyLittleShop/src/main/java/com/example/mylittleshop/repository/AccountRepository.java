package com.example.mylittleshop.repository;
import com.example.mylittleshop.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.role=(:role) AND a.username NOT IN (SELECT e.account from Employee e)")
    List<Account> findAvailableAccount(@Param("role") String role);
}
