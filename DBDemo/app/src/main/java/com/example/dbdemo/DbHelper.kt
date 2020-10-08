package com.example.dbdemo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "app.db"
        const val DATABASE_VERSION = 1

        const val PERSON_TABLE_NAME = "PERSONS"

        const val PERSON_ID = "_id"
        const val PERSON_FIRSTNAME = "firstname"
        const val PERSON_LASTNAME = "lastname"

        const val SQL_PERSON_CREATE_TABLE =
            "create table $PERSON_TABLE_NAME (" +
                    "$PERSON_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$PERSON_FIRSTNAME TEXT NOT NULL, " +
                    "$PERSON_LASTNAME TEXT NOT NULL " +
                    ");"

        const val SQL_DELETE_TABLES = "DROP TABLE IF EXISTS $PERSON_TABLE_NAME;"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_PERSON_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLES)
        onCreate(db)
    }
}