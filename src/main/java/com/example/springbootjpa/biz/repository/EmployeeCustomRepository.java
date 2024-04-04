package com.example.springbootjpa.biz.repository;

import com.example.springbootjpa.biz.condition.EmployeeSearchCondition;
import com.example.springbootjpa.biz.entity.Employee;
import com.example.springbootjpa.biz.dto.EmployeeDeptDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeCustomRepository {

    Page<Employee> findAllPaging(Pageable pageable);
    List<Employee> findAllByCondition(EmployeeSearchCondition condition);
    List<Employee> findAllByCondition(Predicate predicate);
    Page<Employee> findAllPagingByCondition(Predicate predicate, Pageable pageable);

    List<EmployeeDeptDTO> findEmployeeDeptByCondition(Predicate predicate);
}
