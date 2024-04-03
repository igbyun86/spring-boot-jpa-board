package com.example.springbootjpa.biz.repository;

import com.example.springbootjpa.biz.domain.Employee;
import com.example.springbootjpa.biz.domain.QEmployee;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl extends QuerydslRepositorySupport implements EmployeeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public EmployeeRepositoryImpl(EntityManager entityManager) {
        super(Employee.class);

        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Employee> findAll(Predicate predicate, Pageable pageable) {

        QEmployee employee = QEmployee.employee;
        JPAQuery<?> query = jpaQueryFactory.from(employee).where(predicate);

        JPQLQuery<?> pagination = getQuerydsl().applyPagination(pageable, query);


        return new PageImpl(pagination.fetch(), pageable, query.fetchCount());
    }

    public Page findAllWithCount(Predicate predicate, Pageable pageable) {
        QEmployee employee = QEmployee.employee;
        List<?> employees = jpaQueryFactory.from(employee).where(predicate).fetch();

        JPAQuery<Long> employeeTotalCountQuery = jpaQueryFactory
                .select(employee.count())
                .from(employee)
                .where(predicate);

        return PageableExecutionUtils.getPage(employees, pageable, employeeTotalCountQuery::fetchOne);
    }
}
