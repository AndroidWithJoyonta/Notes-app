package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {




    public static final int DB_VERSION = 1;
    public static  String DB_NAME = "my_table";






    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE expense (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, subTitle TEXT, notes TEXT, time TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists expense");

    }

    public void edit(String idToUpdate, String newTitle, String newSubTitle, String newNotes, String newTime){
       try{
           SQLiteDatabase db = this.getWritableDatabase();

           String updateQuery = "UPDATE expense SET title = '" + newTitle + "', subTitle = '" + newSubTitle + "', notes = '" + newNotes + "', time = '" + newTime + "' WHERE id = " + idToUpdate;
           db.execSQL(updateQuery);

       } catch (Exception e){
           Log.d("DataUpdate", "edit: "+e.getMessage());
       }



    }





    public void addNotes(String title, String subTitle, String notes){

        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE , dd MMMM yyyy HH:mm a", Locale.getDefault());
        String formattedTime = sdf.format(new Date(currentTimeMillis));

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("subTitle",subTitle);
        contentValues.put("notes",notes);
        contentValues.put("time",formattedTime);

        database.insert("expense", null, contentValues);

    }



    //====================================
    public Cursor getallData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from expense ", null);
        return cursor;
    }


    public void deleteNotes(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM expense WHERE id LIKE "+id);

    }
    public Cursor searchNotes(String key){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM expense WHERE title  LIKE '"+key+"%' OR subTitle LIKE '"+key+"%' OR notes LIKE '"+key+"%'",null);
        return cursor;
    }

}
