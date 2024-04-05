package com.example.springbootjpa.biz.repository;

import com.example.springbootjpa.biz.dto.EmployeeDeptDTO;
import com.example.springbootjpa.biz.entity.Employee;
import com.example.springbootjpa.biz.entity.QEmployee;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("employee 목록 조회")
    void test1() {
        List<Employee> employees = employeeRepository.findAll();
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
         */
    }

    @Test
    @DisplayName("employee에서 dept조회 @Transactional없어서 LazyInitializationException: could not initialize proxy 발생")
    void test3Error() {
        assertThatThrownBy(() -> {
            List<Employee> employees = employeeRepository.findAll();
            employees.forEach(it -> System.out.println("it.getDept().getDeptName() = " + it.getDept().getDeptName()));
        }).isInstanceOf(LazyInitializationException.class);
    }

    @Test
    @DisplayName("employee에서 dept조회 n+1 발생")
    @Transactional
    void test3() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotEmpty();

        employees.forEach(it -> System.out.println("it.getDept().getDeptName() = " + it.getDept().getDeptName()));
    }

    @Test
    @DisplayName("employee, dept fetch 조인 후 DTO 반환")
    void test5() {
        List<EmployeeDeptDTO> employeeDeptByCondition = employeeRepository.findEmployeeDeptByCondition(null);
        assertThat(employeeDeptByCondition).isNotEmpty();
    }

    @Test
    @DisplayName("employee 페이징 조회")
    void test2() {
        Page<Employee> employeePage = employeeRepository.findAllPaging(PageRequest.of(0, 5));
        List<Employee> employees = employeePage.getContent();
        assertThat(employees).isNotEmpty();
    }

    @Test
    @DisplayName("employee 조건 조회[지역이 서울이고 1990년생이하인 사원]")
    void test4() {
        String city = "서울";
        String year = "1990";
        BooleanExpression conditionCity = QEmployee.employee.address.startsWith(city);      //like 서울%
        BooleanExpression conditionYear = QEmployee.employee.birthDate.goe(year + "0101");  // >= 19900101
        BooleanExpression combinedCondition = conditionCity.and(conditionYear);

        List<Employee> employees = employeeRepository.findAllByCondition(combinedCondition);
        assertThat(employees).hasSize(3);
    }


    @Test
    @DisplayName("subQuery 테스트")
    @Disabled
    void subQueryTest() {

    }


    @Test
    @DisplayName("subQuery 테스트")
    @Disabled
    void subQueryTest2() {

    }

}
