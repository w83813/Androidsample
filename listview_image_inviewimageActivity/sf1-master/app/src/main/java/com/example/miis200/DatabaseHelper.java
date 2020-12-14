package com.example.miis200;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.opencv.calib3d.StereoBM;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Initialize Variable
    private static final String DATABASE_NAME = "product_database";
    private static final String TABLE1 = "table1";
    private static final String TABLE2 = "table2";
    private static final String TABLE3 = "table3";

    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Tables
        String table1 = "CREATE TABLE "+TABLE1+"(id INTEGER PRIMARY KEY,NAME TEXT,PATIENTID TEXT,PHONENUMBER TEXT,BIRTHDAY TEXT,GENDER TEXT,STATUS TEXT,CHECKTIME TEXT,AGE TEXT)";
        String table2 = "CREATE TABLE "+TABLE2+"(id INTEGER PRIMARY KEY,PATIENTID TEXT,IMAGEPATH TEXT,MEMO TEXT)";
        String table3 = "CREATE TABLE "+TABLE3+"(id INTEGER PRIMARY KEY,price TEXT)";
        db.execSQL(table1);
        db.execSQL(table2);
        db.execSQL(table3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Exiting Table
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE3);
        onCreate(db);
    }


    //Create Insert Method
    public boolean insertpatientinfo(String Name, String Patientid, String Phonenumber, String Birthday, String Gender, String Status){
        //Get WriteAble Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Create ContentValues
        ContentValues values1 = new ContentValues();
        values1.put("name",Name);
        values1.put("patientid",Patientid);
        values1.put("phonenumber",Phonenumber);
        values1.put("birthday",Birthday);
        values1.put("gender",Gender);
        values1.put("status",Status);
        values1.put("checktime","");
        values1.put("age","");
        //Insert Data into Database
        sqLiteDatabase.insert(TABLE1,null,values1);
        return true;
    }

    //Create Insert Method
    public boolean insertchecktime(String Checktime){
        //Get WriteAble Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Create ContentValues
        ContentValues values1 = new ContentValues();
        values1.put("Checktime",Checktime);
        //Insert Data into Database
        sqLiteDatabase.insert(TABLE1,null,values1);
        return true;
    }


    //Create Insert Method
    public boolean insertImagePath(String patientid, String imagepath){
        //Get WriteAble Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Create ContentValues
        ContentValues values2 = new ContentValues();
        values2.put("patientid",patientid);
        values2.put("imagepath",imagepath);

        //Insert Data into Database
        sqLiteDatabase.insert(TABLE2,null,values2);

        return true;
    }



    //Create getProduct Method
    public ArrayList getchecktime(String patientid){
        //Get Readtable Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform RawQuery
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE1+" WHERE PATIENTID="+patientid, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("CHECKTIME")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getImagePath(String patientid){
        //Get Readtable Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        //Perform RawQuery
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE2+" WHERE PATIENTID="+patientid, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("IMAGEPATH")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    //query for 0 status
    public Cursor getListContents0() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE1+" WHERE STATUS==0", null);
        return data;
    }

    //query for 1 status
    public Cursor getListContents1() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE1+" WHERE STATUS==1", null);
        return data;
    }

    //query for 2 status
    public Cursor getListContents2() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE1+" WHERE STATUS==2", null);
        return data;
    }

    public void updateatientstatus(String patientid,String checktime){
        SQLiteDatabase idDB = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("STATUS","1");
        values.put("CHECKTIME",checktime);
        idDB.update("TABLE1",values, "PATIENTID=?", new String[]{patientid});
    }

}
