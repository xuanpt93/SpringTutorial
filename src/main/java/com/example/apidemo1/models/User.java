package com.example.apidemo1.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.*;

import javax.persistence.*;


@Entity
@Table(name = "usersdemo")
@NoArgsConstructor
@AllArgsConstructor
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String email;

        public Set<Role> getRoles() {
                return roles;
        }

        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }

        private String password;

        @Column(name = "full_name")
        private String fullName;

        private boolean enabled;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public boolean isEnabled() {
                return enabled;
        }

        public void setEnabled(boolean enabled) {
                this.enabled = enabled;
        }

        @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
        @JoinTable(
                name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )


        private Set<Role> roles = new HashSet<>();

        // constructors, getter and setters are not shown for brevity
    }

