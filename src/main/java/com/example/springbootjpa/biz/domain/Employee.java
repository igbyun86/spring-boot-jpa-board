package com.example.springbootjpa.biz.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "employee")
public class Employee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_no")
    private Long empNo;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "birth_date")
    private String birthDate;
    @Column(name = "email")
    private String email;
    @Column(name = "join_date")
    private LocalDate joinDate;
    @Column(name = "reti_date")
    private LocalDate retiDate;
    @Column(name = "salary")
    private int salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_seq")
    private Dept dept;

    @Builder
    public Employee(String name, String address, String birthDate, String email, LocalDate joinDate, LocalDate retiDate, int salary) {
        this.name = name;
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
        this.joinDate = joinDate;
        this.retiDate = retiDate;
        this.salary = salary;
    }
}
