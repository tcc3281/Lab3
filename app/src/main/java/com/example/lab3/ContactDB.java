package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactDB extends SQLiteOpenHelper {
    public static final String TableName="ContactTable";
    public static final String Id="Id";
    public static final String Name="Name";
    public static final String Phone="Phone";
    public static final String Image="Image";
    protected ArrayList<Contact> contacts;

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ContactDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        contacts=new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists "+TableName+"(" +
                Id+" Integer Primary key," +
                Name+" text," +
                Phone+" text," +
                Image+" text)";
        db.execSQL(sqlCreate);
        System.out.println(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+TableName);
        onCreate(db);
    }

    public void addContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Id,contact.getId());
        values.put(Name,contact.getFullname());
        values.put(Phone,contact.getPhone());
        values.put(Image,contact.getUriImage());
        db.insert(TableName,null,values);
        db.close();
    }

    public void editContact(Contact contact, int id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Id,contact.getId());
        values.put(Name,contact.getFullname());
        values.put(Phone,contact.getPhone());
        values.put(Image,contact.getUriImage());
        db.update(TableName,values,Id + " =? ",new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName, Id + " = ?", new String[] { String.valueOf(contact.getId()) });
        db.close();
    }
    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName, Id + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }


    public ArrayList<Contact>getAllContacts(){
        contacts.clear();
        String query="Select * from "+TableName;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery(query,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                Contact contact=new Contact(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                contacts.add(contact);
            }
        }
        return contacts;
    }
}
