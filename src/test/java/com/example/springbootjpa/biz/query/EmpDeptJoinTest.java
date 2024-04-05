package com.example.springbootjpa.biz.query;

import com.example.springbootjpa.biz.dto.EmployeeDeptDTO;
import com.example.springbootjpa.biz.entity.Employee;
import com.example.springbootjpa.biz.entity.QDept;
import com.example.springbootjpa.biz.entity.QEmployee;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmpDeptJoinTest {


    @Autowired
    private JPAQueryFactory queryFactory;

    QEmployee employee = QEmployee.employee;
    QDept dept = QDept.dept;

    @Test
    @DisplayName("employee, dept 조인 조회")
    void test() {
        List<Employee> employees = queryFactory.select(employee)
                .from(employee)
                .innerJoin(employee.dept, dept)
                .fetch();

        assertThat(employees).isNotEmpty();
        /*
        select
            employee0_.emp_no as emp_no1_1_,
            employee0_.address as address2_1_,
            employee0_.birth_date as birth_da3_1_,
            employee0_.dept_seq as dept_seq9_1_,
            employee0_.email as email4_1_,
            employee0_.join_date as join_dat5_1_,
            employee0_.name as name6_1_,
            employee0_.reti_date as reti_dat7_1_,
            employee0_.salary as salary8_1_
        from
            employee employee0_
        inner join
            dept dept1_
                on employee0_.dept_seq=dept1_.dept_seq
         */
    }

    @Test
    @DisplayName("employee, dept 조인 조회")
    void test2() {
        List<Tuple> tuples = queryFactory.select(employee, dept)
                .from(employee)
                .innerJoin(employee.dept, dept)
                .fetch();

        assertThat(tuples).isNotEmpty();

        /*
            select
            employee0_.emp_no as emp_no1_1_0_,
            dept1_.dept_seq as dept_seq1_0_1_,
            employee0_.address as address2_1_0_,
            employee0_.birth_date as birth_da3_1_0_,
            employee0_.dept_seq as dept_seq9_1_0_,
            employee0_.email as email4_1_0_,
            employee0_.join_date as join_dat5_1_0_,
            employee0_.name as name6_1_0_,
            employee0_.reti_date as reti_dat7_1_0_,
            employee0_.salary as salary8_1_0_,
            dept1_.dept_cd as dept_cd2_0_1_,
            dept1_.dept_name as dept_nam3_0_1_,
            dept1_.depth as depth4_0_1_
        from
            employee employee0_
        inner join
            dept dept1_
                on employee0_.dept_seq=dept1_.dept_seq
         */
    }

    @Test
    @DisplayName("Projections - 생성자 조회")
    void test3() {
        //DTO에 생성자가 있어야함
        List<EmployeeDeptDTO> employeeDeptDTOList = queryFactory.select(Projections.constructor(EmployeeDeptDTO.class,
                        employee.name,
                        employee.address,
                        employee.birthDate,
                        employee.salary,
                        dept.deptName
                ))
                .from(employee)
                .innerJoin(employee.dept, dept)
                .fetch();

        assertThat(employeeDeptDTOList).isNotEmpty();

        employeeDeptDTOList.forEach(it -> System.out.println("it.getName() = " + it.getName()));
        /*
    select
        employee0_.name as col_0_0_,
        employee0_.address as col_1_0_,
        employee0_.birth_date as col_2_0_,
        employee0_.salary as col_3_0_,
        dept1_.dept_name as col_4_0_ 
    from
        employee employee0_ 
    inner join
        dept dept1_ 
            on employee0_.dept_seq=dept1_.dept_seq
         */
    }

    @Test
    @DisplayName("Projections - bean 조회")
    void test4() {
        //DTO Getter, Setter를 이용해 필드에 접근
        List<EmployeeDeptDTO> employeeDeptDTOList = queryFactory.select(Projections.bean(EmployeeDeptDTO.class,
                        employee.name,
                        employee.address,
                        employee.birthDate,
                        employee.salary,
                        dept.deptName
                ))
                .from(employee)
                .innerJoin(employee.dept, dept)
                .fetch();

        assertThat(employeeDeptDTOList).isNotEmpty();

        employeeDeptDTOList.forEach(it -> System.out.println("it.getName() = " + it.getName()));
    }

    @Test
    @DisplayName("Projections - field 조회")
    void test5() {
        //DTO 필드에 접근
        List<EmployeeDeptDTO> employeeDeptDTOList = queryFactory.select(Projections.fields(EmployeeDeptDTO.class,
                        employee.name.as("name"),
                        employee.address,
                        employee.birthDate,
                        employee.salary,
                        dept.deptName
                ))
                .from(employee)
                .innerJoin(employee.dept, dept)
                .fetch();

        assertThat(employeeDeptDTOList).isNotEmpty();

        employeeDeptDTOList.forEach(it -> System.out.println("it.getName() = " + it.getName()));
    }
}
