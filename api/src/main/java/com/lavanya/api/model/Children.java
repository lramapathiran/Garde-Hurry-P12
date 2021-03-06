package com.lavanya.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lavanya.api.enums.School;

import javax.persistence.*;
import java.util.List;

/**
 * Bean representing a Children.
 * Children object is declared as a JPA entity with the corresponding annotation.
 * @author lavanya
 */
@Entity
public class Children {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    @ManyToOne
    @JoinColumn(name="user_id",  nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    School school;

    @ManyToMany
    @JoinTable(name="children_to_watch",
            joinColumns = @JoinColumn(name="children_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "childcare_id", referencedColumnName="id"))
    private List<Childcare> childcares;

    public Children() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Childcare> getChildcares() {
        return childcares;
    }

    public void setChildcares(List<Childcare> childcares) {
        this.childcares = childcares;
    }
}
