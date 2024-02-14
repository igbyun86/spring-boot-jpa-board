package com.example.springbootjpa.biz.user.model;

import javax.persistence.*;

@Entity
@Table(name = "users") // Optional: Specify the table name in the database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    // Other user attributes (e.g., name, date of birth, etc.)

    // Constructors, getters, and setters

    // You might also want to include additional annotations such as @OneToMany, @ManyToOne, etc.
}