package com.unipi.anastasis.mypois;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "poi.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table poidetails(name TEXT primary key, descriptionofyourpoi TEXT, loc TEXT, timestamp TEXT, category TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists poidetails");
    }
    public Boolean insertpoidata(String name, String descriptionofyourpoi,String loc,String timestamp,String category)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("descriptionofyourpoi", descriptionofyourpoi);
        contentValues.put("loc",loc);
        contentValues.put("timestamp",timestamp);
        contentValues.put("category",category);
        long result=DB.insert("poidetails", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean updatepoidata(String name, String descriptionofyourpoi,String loc,String timestamp, String category)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("descriptionofyourpoi", descriptionofyourpoi);
        contentValues.put("loc",loc);
        contentValues.put("timestamp",timestamp);
        contentValues.put("category",category);
        Cursor cursor = DB.rawQuery("Select * from poidetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("poidetails", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deletedata (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from poidetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("poidetails", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from poidetails", null);
        return cursor;
    }

}