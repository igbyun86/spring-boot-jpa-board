package com.example.springbootjpa.biz.repository;

import com.example.springbootjpa.biz.condition.EmployeeSearchCondition;
import com.example.springbootjpa.biz.dto.EmployeeDeptDTO;
import com.example.springbootjpa.biz.entity.Employee;
import com.example.springbootjpa.biz.entity.QDept;
import com.example.springbootjpa.biz.entity.QEmployee;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl extends QuerydslRepositorySupport implements EmployeeCustomRepository {

    @Autowired
    private final JPAQueryFactory queryFactory;

    QEmployee employee = QEmployee.employee;
    QDept dept = QDept.dept;

    public EmployeeRepositoryImpl(EntityManager entityManager, JPAQueryFactory queryFactory) {
        super(Employee.class);
        this.queryFactory = queryFactory;


        //jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Employee> findAllPaging(Pageable pageable) {
        JPAQuery<Employee> query = queryFactory.selectFrom(employee);

        JPQLQuery<Employee> pagination = getQuerydsl().applyPagination(pageable, query);


        return new PageImpl<>(pagination.fetch(), pageable, query.fetchCount());
    }

    public Page findAllWithCount(Predicate predicate, Pageable pageable) {
        List<?> employees = queryFactory.selectFrom(employee).where(predicate).fetch();

        JPAQuery<Long> employeeTotalCountQuery = queryFactory
                .select(employee.count())
                .from(employee)
                .where(predicate);

        return PageableExecutionUtils.getPage(employees, pageable, employeeTotalCountQuery::fetchOne);
    }

    @Override
    public List<Employee> findAllByCondition(Predicate predicate) {
        JPAQuery<Employee> query = queryFactory.selectFrom(employee).where(predicate);

        return query.fetch();
    }

    @Override
    public List<Employee> findAllByCondition(EmployeeSearchCondition condition) {
        JPAQuery<Employee> query = queryFactory.selectFrom(employee).where(getEmployeeQueryCondition(condition));

        return query.fetch();
    }

    @Override
    public Page<Employee> findAllPagingByCondition(Predicate predicate, Pageable pageable) {
        JPAQuery<?> query = queryFactory.from(employee).where(predicate);

        JPQLQuery<?> pagination = getQuerydsl().applyPagination(pageable, query);


        return new PageImpl(pagination.fetch(), pageable, query.fetchCount());
    }

    @Override
    public List<EmployeeDeptDTO> findEmployeeDeptByCondition(Predicate predicate) {


        return queryFactory.select(Projections.constructor(EmployeeDeptDTO.class,
                employee.name,
                employee.address,
                employee.birthDate,
                employee.salary,
                dept.deptName
                ))
                .from(employee)
                .innerJoin(employee.dept, dept)
                .where(predicate)
                .fetch();
    }


    private BooleanBuilder getEmployeeQueryCondition(EmployeeSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(condition.getName())) {
            builder.and(employee.name.eq(condition.getName()));
        }

        if (StringUtils.hasText(condition.getAddress())) {
            builder.and(employee.address.startsWith(condition.getAddress()));
        }

        return builder;
    }

}
