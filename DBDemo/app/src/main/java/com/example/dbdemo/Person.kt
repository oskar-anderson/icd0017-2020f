package com.example.dbdemo

class Person {
    var id: Int = 0
    var firstName: String = ""
    var lastName: String = ""


    constructor(firstName: String, lastName: String) : this(0, firstName, lastName ) {

    }

    constructor(id: Int, firstName: String, lastName: String) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName

    }
}