package com.example.managestudent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.bloco.faker.Faker;

public class Database {

    static SQLiteDatabase db = null;
    private final String TABLE_NAME = "Info";
    private final String ID = "Code";
    private final String FULL_NAME = "full_name";
    private final String EMAIL = "email";
    private final String BIRTHDAY = "birthday";

    public Database(Context context) {
        if (db == null) {
            String DATABASE_NAME = "students";
            String path = context.getFilesDir().getPath() + "/" + DATABASE_NAME;
            try {
                db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createTable() {
        db.beginTransaction();
        try {
            db.execSQL("create table " + TABLE_NAME + "(" +
                    ID + " integer PRIMARY KEY," +
                    FULL_NAME + " text," +
                    EMAIL + " text," +
                    BIRTHDAY + " text)");
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID}, null, null, null, null, null);
        int count = cursor.getCount() ;
        if (count == 0) {
            db.beginTransaction();
            try {
                for (int i = 0; i <= 50; i++) {
                    Faker faker = new Faker();
                    ContentValues values = new ContentValues();
                    values.put(ID, faker.number.positive());
                    values.put(FULL_NAME, faker.name.name());
                    values.put(EMAIL, faker.internet.email());
                    values.put(BIRTHDAY, faker.date.birthday().toString());
                    db.insert(TABLE_NAME, null, values);
                }
                db.setTransactionSuccessful();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
        cursor.close();
    }

    public void addStudent(Model model) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ID, model.code);
            values.put(FULL_NAME, model.fullName);
            values.put(EMAIL, model.email);
            values.put(BIRTHDAY, model.birthday);
            db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateStudent(Model model) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ID, model.code);
            values.put(FULL_NAME, model.fullName);
            values.put(EMAIL, model.email);
            values.put(BIRTHDAY, model.birthday);
            db.update(TABLE_NAME, values, ID + "==" + model.code, null);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void deleteStudent(int code) {
        db.beginTransaction();
        try {
            db.delete(TABLE_NAME, ID + "==" + code, null);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getListName() {
        return db.query(TABLE_NAME, new String[]{ID, FULL_NAME}, null, null, null, null, null);
    }

    public Cursor getListName(String preName) {
        return db.query(TABLE_NAME, new String[]{ID, FULL_NAME}, FULL_NAME +"like " + preName + "%", null, null, null, null);
    }

    public Cursor getListName(int preCode) {
        return db.query(TABLE_NAME, new String[]{ID, FULL_NAME}, ID +"like " + preCode + "%", null, null, null, null);
    }


    public void closeDatabase(){
        db.close();
    }
}
