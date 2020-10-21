package com.example.contactbook.Model;

import androidx.annotation.NonNull;

public class Person {
    public String id;
    public String firstName;
    public String lastName;

    public Person(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @NonNull
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @NonNull
    public String toStringAllInfo() {
        return "Person{" +
                "id=" + id +
                ", firstname=" + firstName + "\"" +
                ", lastname=" + lastName + "\"" +
                "}";
    }

}
