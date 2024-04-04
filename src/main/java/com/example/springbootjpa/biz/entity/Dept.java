package com.example.springbootjpa.biz.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "dept")
public class Dept {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_seq")
    private Long deptSeq;

    @Column(name = "dept_cd")
    private String deptCd;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "depth")
    private int depth;

    @Builder
    public Dept(String deptCd, String deptName, int depth) {
        this.deptCd = deptCd;
        this.deptName = deptName;
        this.depth = depth;
    }
}
