package com.example.dbdemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class PersonRepository(val context: Context) {

    private lateinit var dbHelper: DbHelper
    private lateinit var db: SQLiteDatabase

    fun open(): PersonRepository {
        dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase

        return this
    }

    fun close() {
        dbHelper.close()
    }

    fun add(person: Person) {
        val contentValues = ContentValues()
        contentValues.put(DbHelper.PERSON_FIRSTNAME, person.firstName)
        contentValues.put(DbHelper.PERSON_LASTNAME, person.lastName)
        db.insert(DbHelper.PERSON_TABLE_NAME, null, contentValues)
    }

    fun getAll(): List<Person> {
        val persons = ArrayList<Person>()

        val columns = arrayOf(DbHelper.PERSON_ID, DbHelper.PERSON_FIRSTNAME, DbHelper.PERSON_LASTNAME)

        val cursor = db.query(DbHelper.PERSON_TABLE_NAME, columns, null, null, null, null, null)

        while (cursor.moveToNext()) {
            persons.add(
                Person(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
            )
        }
        cursor.close()

        return persons
    }
}