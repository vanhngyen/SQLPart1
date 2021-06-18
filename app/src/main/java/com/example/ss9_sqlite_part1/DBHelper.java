package com.example.ss9_sqlite_part1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "User.db";
    public static final String TABLE_NAME = "TBL_USER";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE " + TABLE_NAME +"(" +TBL_USER.id+"INTERGER PRIMARY KEY,"+
            TBL_USER.name+"TEXT,"+TBL_USER.age+"INTERGER,"+TBL_USER.gender+"TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }

    public String insertDB(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TBL_USER.name,user.getName());
        cv.put(TBL_USER.age, user.getAge());
        cv.put(TBL_USER.gender,user.getGender());
        long isSuccess = db.insert(TABLE_NAME,null,cv);
        if (isSuccess > 0){
            return "Success";
        }else {
            return "Fail";
        }
    }

    public List<User> getAllUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+ TABLE_NAME;
        List<User> listUser = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                User user = new User();
                user.setName(cursor.getString(cursor.getColumnIndex(TBL_USER.name)));
                user.setGender(cursor.getString(cursor.getColumnIndex(TBL_USER.gender)));
                user.setId(cursor.getInt(cursor.getColumnIndex(TBL_USER.id)));
                user.setAge(cursor.getInt(cursor.getColumnIndex(TBL_USER.age)));
            }while (cursor.moveToNext());

        }
        return listUser;
    }

    public String updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TBL_USER.name,user.getName());
        cv.put(TBL_USER.age,user.getAge());
        long isSuccess = db.update(TABLE_NAME, cv,"id="+user.getId(),null);
        if(isSuccess > 0){
            return "Update Success";
        }else {
            return "Update fail";
        }
    }
    public String deleteUser(int user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long isSuccess = db.delete(TABLE_NAME,"id="+user_id,null);
        if(isSuccess > 0){
            return "Update Success";
        }else {
            return "Update fail";
        }
    }
}
