package com.example.myaccount;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAccess {
    private SQLiteDatabase db;

    public SQLiteDatabase getDB() {
        return db;
    }

    public DBAccess(SQLiteOpenHelper dbHelper) {
        db = dbHelper.getWritableDatabase();
    }

    public void execQuery(List<Map<String, Object>> list, String strSQL) {
        try {
            Cursor cursor = db.rawQuery(strSQL, null);
            cursor.moveToFirst();
            list.clear();
            while (!cursor.isAfterLast()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sid", cursor.getInt(0));
                map.put("date",cursor.getString(1));
                map.put("type",cursor.getString(2));
                map.put("amount",cursor.getFloat(3));
                list.add(map);
                cursor.moveToNext();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
