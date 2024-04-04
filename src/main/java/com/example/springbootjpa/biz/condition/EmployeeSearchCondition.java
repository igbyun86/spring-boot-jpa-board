package com.example.springbootjpa.biz.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @Getter
@NoArgsConstructor @AllArgsConstructor
public class EmployeeSearchCondition {
    private String name;
    private String address;
    private String birthDate;


}
