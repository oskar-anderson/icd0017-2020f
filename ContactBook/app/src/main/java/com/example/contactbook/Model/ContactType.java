package com.example.contactbook.Model;

import androidx.annotation.NonNull;

public class ContactType {
    public String id;
    public String text;

    public ContactType(String id, String text) {
        this.id = id;
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }

    @NonNull
    public String toStringAllInfo() {
        return "ContactType{" +
                "id=" + id +
                ", text=" + text + "\"" +
                "}";
    }
}
