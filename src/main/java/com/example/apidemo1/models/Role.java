package com.example.apidemo1.models;


import javax.persistence.*;

@Entity
@Table(name = "rolesDemo")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    // constructors, getter and setters are not shown for brevity
}
