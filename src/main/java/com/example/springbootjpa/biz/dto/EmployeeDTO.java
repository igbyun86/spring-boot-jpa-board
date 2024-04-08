package com.example.springbootjpa.biz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class EmployeeDTO {

    private String name;
    private String address;
    private String birthDate;
    private int salary;
}
