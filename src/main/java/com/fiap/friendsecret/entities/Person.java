package com.fiap.friendsecret.entities;

import com.pengrad.telegrambot.model.User;

public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;

    public Person(Integer id, String firstName, String lastName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public Person() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUser(User owner) {
        this.firstName = owner.firstName();
        this.lastName = owner.lastName();
        this.id = owner.id();
        this.username = owner.username();
    }

}
