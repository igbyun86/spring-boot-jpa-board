package com.example.springbootjpa.biz.query;

import com.example.springbootjpa.biz.entity.Employee;
import com.example.springbootjpa.biz.entity.QEmployee;
import com.example.springbootjpa.common.jpa.OrderByNull;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeSelectTest {

    @Autowired
    private JPAQueryFactory query;

    QEmployee employee = QEmployee.employee;


    @Test
    @DisplayName("querydsl 조회")
    void selectEmployeeTest() {
        List<Employee> employees = query.selectFrom(employee).fetch();
        assertThat(employees).isNotEmpty();
    }

    @Test
    @DisplayName("exists 조회")
    void selectEmployeeExistsTest() {
        boolean exists = query.selectFrom(employee)
                .where(employee.address.contains("안양"))
                .fetchOne() != null;

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("정렬 조회")
    void selectEmployeeOrderTest() {
        List<Employee> employees = query.selectFrom(employee)
                .orderBy(employee.joinDate.asc(), employee.birthDate.desc())
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
    order by
        employee0_.join_date asc,
        employee0_.birth_date desc

         */
    }

    @Test
    @DisplayName("그룹핑 조회")
    void selectGroupingTest() {
        List<Long> result = query.
                select(employee.dept.deptSeq)
                .from(employee)
                .groupBy(employee.dept.deptSeq)
                .orderBy(OrderByNull.DEFAULT)
                .fetch();

        assertThat(result).isNotEmpty();
    }


}
