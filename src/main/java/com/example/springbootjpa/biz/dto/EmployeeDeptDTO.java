package com.example.springbootjpa.biz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class EmployeeDeptDTO {

    private String name;
    private String address;
    private String birthDate;
    private int salary;
    private String deptName;
}
