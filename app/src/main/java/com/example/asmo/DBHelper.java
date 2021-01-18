package com.example.asmo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "asmoAppDB.db" ;
    public static final String TBNAME = "asmoUsers" ;
    public static final int DB_VERSION= 1 ;
    
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBNAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TBNAME+ "(_id Integer Primary Key AutoIncrement,date text, time text, peakFlow text, medication text, comment text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TBNAME);
        onCreate(db);
    }

    public boolean addData(String date, String time, String flow,String medication,String comment){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase() ;
        ContentValues cv = new ContentValues() ;
        cv.put("date",date);
        cv.put("time",time);
        cv.put("peakFlow",flow);
        cv.put("medication",medication);
        cv.put("comment",comment);
        long result =  sqLiteDatabase.insert(TBNAME,null,cv);
        if (result==-1) {
            sqLiteDatabase.close();
            return false;
        }
        else {
            sqLiteDatabase.close();
            return true;
        }

    }

    public void delete(Integer id){
        SQLiteDatabase db = this.getWritableDatabase() ;
//        db.execSQL("DELETE FROM "+TBNAME+ " WHERE userEmail="+email+";");
        db.delete(TBNAME,"_id=?",new String[]{id.toString()});
        db.close();
    }
    public boolean update(Integer id, String date, String time, String flow,String medication,String comment){
        SQLiteDatabase db = this.getWritableDatabase();
//        //Cursor cur = db.rawQuery("update "+TBNAME+" set userName ='"+name+"',"+ "userPass = '"+pass+"';",null);
//        db.execSQL("UPDATE " +TBNAME+ " SET userName=" +name+ ", userPass=" +pass+ ", userEmail=" +newEmail+ " WHERE userEmail=" +email+ ";");
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase() ;
        ContentValues cv = new ContentValues() ;
        cv.put("date",date);
        cv.put("time",time);
        cv.put("peakFlow",flow);
        cv.put("medication",medication);
        cv.put("comment",comment);
        long result = db.update(TBNAME,cv,"_id=?",new String[]{id.toString()});
        if (result==-1) {
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }



    public Cursor display (){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TBNAME;
        Cursor cur = db.rawQuery(query,null );
        return cur;
    }
    public Cursor displayOne (Integer id){
        SQLiteDatabase db = this.getReadableDatabase();
        //String query = "SELECT * FROM " + TBNAME + "WHERE _id=?;",new String[]{id};
        Cursor cur = db.rawQuery("SELECT * FROM " + TBNAME + " WHERE _id=?", new String[]{id.toString()});
        //Cursor cur = db.rawQuery(query,null );
        if (cur.getCount() > 0) {
            cur.moveToFirst();
        }
        return cur;
    }

//    public Cursor returnDate(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selQuery = "SELECT date FROM " + TBNAME;
//        Cursor cur = db.rawQuery(selQuery,null);
//        return cur;
//    }
//    public Cursor returnTime(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selQuery = "SELECT time FROM " + TBNAME;
//        Cursor cur = db.rawQuery(selQuery,null);
//        return cur;
//    }
    public Cursor returnFlow(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selQuery = "SELECT peakFlow FROM " + TBNAME;
        Cursor cur = db.rawQuery(selQuery,null);
        return cur;
    }



}
