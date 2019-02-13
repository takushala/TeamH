package com.example.mylittleshop.repository;

import com.example.mylittleshop.entity.Employee;
import com.example.mylittleshop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,String>{
    List<Employee> findByShop(Shop shop);
}
