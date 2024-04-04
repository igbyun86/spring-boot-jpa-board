package com.example.springbootjpa.biz.service;

import com.example.springbootjpa.biz.entity.Employee;
import com.example.springbootjpa.biz.entity.QDept;
import com.example.springbootjpa.biz.entity.QEmployee;
import com.example.springbootjpa.framework.config.QuerydslConfig;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
public class EmployeeQuerydslSelectTest {

    @Autowired
    private JPAQueryFactory query;

    QEmployee employee = QEmployee.employee;
    QDept dept = QDept.dept;


    @Test
    @DisplayName("queryDsl조회")
    void test1() {


        List<Employee> employees = query.selectFrom(employee).fetch();


        employees.forEach(System.out::println);
    }
}
