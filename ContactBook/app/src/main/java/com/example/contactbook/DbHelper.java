package com.example.contactbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.contactbook.Model.Contact;
import com.example.contactbook.Model.ContactType;
import com.example.contactbook.Model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("UnnecessaryLocalVariable")
public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app.db";
    private static final Integer DATABASE_VERSION = 2;  // cannot be downgraded

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // region TABLE NAMES
    private static final String PERSON_TABLE_NAME = "PERSON";
    private static final String CONTACT_TABLE_NAME = "CONTACT";
    private static final String CONTACT_TYPE_TABLE_NAME = "CONTACT_TYPE";
    // endregion

    // region TABLE FIELDS
    // PERSON
    private static final String PERSON_ID_PK = "_id";
    private static final String PERSON_FIRSTNAME = "firstname";
    private static final String PERSON_LASTNAME = "lastname";

    // CONTACT_TYPE
    private static final String CONTACT_TYPE_ID_PK = "_id";
    private static final String CONTACT_TYPE_TEXT = "text";

    // CONTACT
    private static final String CONTACT_ID_PK = "_id";
    private static final String CONTACT_PERSON_ID_FK = "person_id";
    private static final String CONTACT_TYPE_ID_FK = "contact_type_id";
    private static final String CONTACT_TEXT = "contact_text";
    // endregion

    // region DATABASE CREATION STRINGS
    private static final String SQL_PERSON_CREATE_TABLE =
            "create table " + PERSON_TABLE_NAME +
            " ( " +
                    PERSON_ID_PK        + " TEXT PRIMARY KEY, " +
                    PERSON_FIRSTNAME    + " TEXT NOT NULL, " +
                    PERSON_LASTNAME     + " TEXT NOT NULL " +
            " );";


    private static final String SQL_CONTACT_TYPE_CREATE_TABLE =
            "create table " + CONTACT_TYPE_TABLE_NAME +
            " ( " +
                    CONTACT_TYPE_ID_PK  + " TEXT PRIMARY KEY, " +
                    CONTACT_TYPE_TEXT   + " TEXT NOT NULL " +
            " );";


    private static final String SQL_CONTACT_CREATE_TABLE =
            "create table " + CONTACT_TABLE_NAME +
            " ( " +
                    CONTACT_ID_PK           + " TEXT PRIMARY KEY, " +
                    CONTACT_PERSON_ID_FK    + " TEXT NOT NULL, " +
                    CONTACT_TYPE_ID_FK      + " TEXT NOT NULL, " +
                    CONTACT_TEXT            + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + CONTACT_PERSON_ID_FK + ") REFERENCES " + PERSON_TABLE_NAME + "(" + PERSON_ID_PK +")" +
                    "FOREIGN KEY(" + CONTACT_TYPE_ID_FK + ") REFERENCES " + CONTACT_TYPE_TABLE_NAME + "(" + CONTACT_TYPE_ID_PK +")" +
            " );";
    // endregion


    private static final String[] TABLES = new String[] {
            SQL_PERSON_CREATE_TABLE, SQL_CONTACT_TYPE_CREATE_TABLE, SQL_CONTACT_CREATE_TABLE };


    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String table : TABLES) {
            Log.d("G", "Creating table " + table);
            db.execSQL(table);
        }
        UUID id = java.util.UUID.randomUUID();
        ContactType Telephone = new ContactType(id.toString(), "Telephone");
        id = java.util.UUID.randomUUID();
        ContactType Address = new ContactType(id.toString(), "Address");
        ContactTypeInsert(Telephone);
        ContactTypeInsert(Address);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String table : TABLES) {
            db.execSQL("DROP TABLE IF EXISTS " + table + ";");
        }
        onCreate(db);
    }


    public boolean PersonInsert(Person person) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PERSON_ID_PK, person.id);
        cv.put(PERSON_FIRSTNAME, person.firstName);
        cv.put(PERSON_LASTNAME, person.lastName);

        long insert = db.insert(PERSON_TABLE_NAME, null, cv);
        db.close();
        return insert != -1;
    }


    // region PERSON
    public Person PersonGet(String id) {
        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT " +
                PERSON_ID_PK + " , " +
                PERSON_FIRSTNAME + " , " +
                PERSON_LASTNAME +
                " FROM " + PERSON_TABLE_NAME + " WHERE " + PERSON_ID_PK + " == " + id  + " ;";
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            String personId = cursor.getString(0);
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);

            cursor.close();
            Person person = new Person(personId, firstName, lastName);
            return person;
        } else {
            assert true;
        }
        cursor.close();
        db.close();
        return null;
    }

    public List<Person> PersonGetAll() {
        List<Person> returnList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT " +
                PERSON_ID_PK + " , " +
                PERSON_FIRSTNAME + " , " +
                PERSON_LASTNAME +
                " FROM " + PERSON_TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String personId = cursor.getString(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);

                Person person = new Person(personId, firstName, lastName);
                returnList.add(person);

            } while (cursor.moveToNext());
        } else {
            assert true;
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public boolean PersonDelete(String id){
        ArrayList<Contact> contacts = (ArrayList<Contact>) ContactGetAll();
        for (Contact contact : contacts) {
            if (id.equals(contact.personIdFK)) {
                return false;
            }
        }

        String selection = PERSON_ID_PK + " == ?";
        String[] selectionArgs = { id };
        SQLiteDatabase db = getWritableDatabase();
        int deletedRows = db.delete(PERSON_TABLE_NAME, selection, selectionArgs);

        db.close();
        return deletedRows == 1;
    }
    // endregion



    // region CONTACT_TYPE
    public boolean ContactTypeInsert(ContactType contactType) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CONTACT_TYPE_ID_PK, contactType.id);
        cv.put(CONTACT_TYPE_TEXT, contactType.text);

        long insert = db.insert(CONTACT_TYPE_TABLE_NAME, null, cv);
        db.close();
        return insert != -1;
    }

    public ContactType ContactTypeGet(String id) {
        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT " +
                CONTACT_TYPE_ID_PK + " , " +
                CONTACT_TYPE_TEXT +
                " FROM " + CONTACT_TYPE_TABLE_NAME + " WHERE " + CONTACT_TYPE_ID_PK + " == " + id  + " ;";
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            String contactTypeId = cursor.getString(0);
            String text = cursor.getString(1);

            cursor.close();
            ContactType contactType = new ContactType(contactTypeId, text);
            return contactType;
        } else {
            assert true;
        }
        cursor.close();
        db.close();
        return null;
    }

    public List<ContactType> ContactTypeGetAll() {
        List<ContactType> returnList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT " +
                CONTACT_TYPE_ID_PK + " , " +
                CONTACT_TYPE_TEXT +
                " FROM " + CONTACT_TYPE_TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String contactTypeId = cursor.getString(0);
                String text = cursor.getString(1);

                ContactType contactType = new ContactType(contactTypeId, text);
                returnList.add(contactType);

            } while (cursor.moveToNext());
        } else {
            assert true;
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public boolean ContactTypeDelete(String id){
        ArrayList<Contact> contacts = (ArrayList<Contact>) ContactGetAll();
        for (Contact contact : contacts) {
            if (id.equals(contact.contactTypeIdFK)) {
                return false;
            }
        }

        String selection = CONTACT_TYPE_ID_PK + " == ?";
        String[] selectionArgs = { id };
        SQLiteDatabase db = getWritableDatabase();
        int deletedRows = db.delete(CONTACT_TYPE_TABLE_NAME, selection, selectionArgs);

        db.close();
        return deletedRows == 1;
    }
    // endregion



    // region CONTACT
    public boolean ContactInsert(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CONTACT_ID_PK, contact.id);
        cv.put(CONTACT_TYPE_ID_FK, contact.contactTypeIdFK);
        cv.put(CONTACT_PERSON_ID_FK, contact.personIdFK);
        cv.put(CONTACT_TEXT, contact.text);

        long insert = db.insert(CONTACT_TABLE_NAME, null, cv);
        db.close();
        return insert != -1;
    }

    public Contact ContactGet(String id) {
        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT " +
                CONTACT_ID_PK + " , " +
                CONTACT_TYPE_ID_FK + " , " +
                CONTACT_PERSON_ID_FK + " , " +
                CONTACT_TEXT +
                " FROM " + CONTACT_TABLE_NAME + " WHERE " + CONTACT_ID_PK + " == " + id  + " ;";
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            String contactIdPK = cursor.getString(0);
            String contactTypeIdFK = cursor.getString(1);
            String contactPersonIdFK = cursor.getString(2);
            String text = cursor.getString(3);

            cursor.close();
            Contact contact = new Contact(contactIdPK, contactTypeIdFK, contactPersonIdFK, text);
            return contact;
        } else {
            assert true;
        }
        cursor.close();
        db.close();
        return null;
    }

    public List<Contact> ContactGetAll() {
        List<Contact> returnList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT " +
                CONTACT_ID_PK + " , " +
                CONTACT_TYPE_ID_FK + " , " +
                CONTACT_PERSON_ID_FK + " , " +
                CONTACT_TEXT +
                " FROM " + CONTACT_TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String contactIdPK = cursor.getString(0);
                String contactTypeIdFK = cursor.getString(1);
                String contactPersonIdFK = cursor.getString(2);
                String text = cursor.getString(3);

                Contact contact = new Contact(contactIdPK, contactTypeIdFK, contactPersonIdFK, text);
                returnList.add(contact);

            } while (cursor.moveToNext());
        } else {
            assert true;
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public boolean ContactDelete(String id){
        String selection = CONTACT_ID_PK + " == ?";
        String[] selectionArgs = { id };
        SQLiteDatabase db = getWritableDatabase();
        int deletedRows = db.delete(CONTACT_TABLE_NAME, selection, selectionArgs);

        db.close();
        return deletedRows == 1;
    }
    // endregion

}
