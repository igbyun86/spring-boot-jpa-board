package com.example.springbootjpa.biz.query;

import com.example.springbootjpa.biz.dto.EmployeeDTO;
import com.example.springbootjpa.biz.dto.EmployeeDeptDTO;
import com.example.springbootjpa.biz.entity.Employee;
import com.example.springbootjpa.biz.entity.QDept;
import com.example.springbootjpa.biz.entity.QEmployee;
import com.example.springbootjpa.common.jpa.OrderByNull;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmployeeSelectTest {

    @Autowired
    private JPAQueryFactory query;

    QEmployee employee = QEmployee.employee;
    QDept dept = QDept.dept;


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
                .orderBy(OrderByNull.DEFAULT)   //mysql, mariadb에서 group by 시 발생하는 filesort를 제거함(쿼리 성능향상)
                .fetch();

        assertThat(result).isNotEmpty();
    }


    @Test
    @DisplayName("커버링 인덱스 조회")
    void selectCoveringIndexTest() {
        //인덱스 컬럼을 조회컬럼으로 먼저 조회후 인덱스 조건으로 다시한번 조회한다.
        List<Long> ids = query.select(employee.empNo)
                .from(employee)
                .where(employee.address.contains("서울"))
                .orderBy(employee.joinDate.asc())
                .limit(5L)
                .offset(0L)
                .fetch();

        if (ids.isEmpty()) {
            //Empty 리스트 리턴
            //return new ArrayList<>();
        }

        List<EmployeeDTO> employeeDTOList = query
                .select(Projections.fields(EmployeeDTO.class,
                        employee.name.as("name"),
                        employee.address,
                        employee.birthDate,
                        employee.salary
                ))
                .from(employee)
                .where(employee.empNo.in(ids))
                .orderBy(employee.joinDate.asc())
                .fetch();

        assertThat(employeeDTOList).hasSizeGreaterThan(1);

    }


    @Test
    @DisplayName("select subquery 조회")
    void selectSubQueryTest() {
        Expression<String> subQueryDeptName = ExpressionUtils.as(
                JPAExpressions.select(dept.deptName)
                        .from(dept)
                        .where(dept.deptSeq.eq(employee.dept.deptSeq)),
                "deptName"
        );

        List<EmployeeDeptDTO> employeeDeptDTOList = query
                .select(Projections.fields(EmployeeDeptDTO.class,
                        employee.name,
                        employee.address,
                        employee.birthDate,
                        employee.salary,
                        subQueryDeptName
                ))
                .from(employee)
                .where(employee.address.contains("경기"))
                .fetch();

        assertThat(employeeDeptDTOList).hasSizeGreaterThan(1);

        /*
        select
            employee0_.name as col_0_0_,
            employee0_.address as col_1_0_,
            employee0_.birth_date as col_2_0_,
            employee0_.salary as col_3_0_,
            (select
                dept1_.dept_name
            from
                dept dept1_
            where
                dept1_.dept_seq=employee0_.dept_seq) as col_4_0_
        from
            employee employee0_
        where
            employee0_.address like ? escape '!'
         */
    }

    @Test
    @DisplayName("select subquery where절 조회")
    void selectSubQueryWhereTest() {
        JPQLQuery<Long> subQueryWhere = JPAExpressions
                .select(employee.empNo)
                .from(employee)
                .where(employee.birthDate.goe("19900101"));
        List<Employee> employees = query
                .selectFrom(employee)
                .where(employee.empNo.in(
                        subQueryWhere
                )).fetch();

        assertThat(employees).hasSizeGreaterThan(1);

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
        where
            employee0_.emp_no in (
                select
                    employee1_.emp_no
                from
                    employee employee1_
                where
                    employee1_.birth_date>='19900101'
            )
         */
    }
}
