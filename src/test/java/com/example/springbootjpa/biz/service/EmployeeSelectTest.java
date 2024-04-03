package com.example.springbootjpa.biz.service;

import com.example.springbootjpa.biz.domain.Employee;
import com.example.springbootjpa.biz.domain.QDept;
import com.example.springbootjpa.biz.domain.QEmployee;
import com.example.springbootjpa.biz.repository.EmployeeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeSelectTest {

    @Autowired
    EmployeeRepository employeeRepository;

    private JPAQueryFactory queryFactory;



    @Test
    @DisplayName("employee조회")
    @Transactional
    void test1() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotEmpty();
    }

    @Test
    @DisplayName("employee에서 dept조회 n+1")
    @Transactional
    void test2() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotEmpty();

        employees.forEach(it -> System.out.println("it.getDept().getDeptName() = " + it.getDept().getDeptName()));
    }

    @Test
    @DisplayName("queryDsl조회")
    void test3() {
        QEmployee employee = QEmployee.employee;
        QDept dept = QDept.dept;


    }
}
