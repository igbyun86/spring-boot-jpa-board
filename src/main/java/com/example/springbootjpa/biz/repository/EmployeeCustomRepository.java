package com.example.springbootjpa.biz.repository;

import com.example.springbootjpa.biz.domain.Employee;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeCustomRepository {

    Page<Employee> findAll(Predicate predicate, Pageable pageable);
}
