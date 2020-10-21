package com.example.contactbook.Model;

import androidx.annotation.NonNull;

public class Contact {
    public String id;
    public String contactTypeIdFK;
    public String personIdFK;
    public String text;
    public String contactTypeName;
    public String personFirstName;
    public String personLastName;

    public Contact(String id, String contactTypeIdFK, String personIdFK, String text) {
        this.id = id;
        this.contactTypeIdFK = contactTypeIdFK;
        this.personIdFK = personIdFK;
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", contactTypeIdFK=" + contactTypeIdFK + "\"" +
                ", contactTypeName=" + contactTypeName + "\"" +
                ", personIdFK=" + personIdFK + "\"" +
                ", personFirstName=" + personFirstName + "\"" +
                ", personLastName=" + personLastName + "\"" +
                ", text=" + text + "\"" +
                "}";
    }
}
