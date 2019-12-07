package com.poly.bookmanager.projectDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.bookmanager.dbManager.DatabaseSQLite;
import com.poly.bookmanager.model.TheLoai;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TheLoaiDAO {
    DatabaseSQLite databaseSQLite;
    SQLiteDatabase sqLiteDatabase;
    public static final String TABLE_TheLoai = "TheLoai";

    public TheLoaiDAO(Context context) {
        databaseSQLite = new DatabaseSQLite(context);
    }

    public int addKind(TheLoai theLoai) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenTheLoai", theLoai.getTentheloai());
        try {
            if (sqLiteDatabase.insert(TABLE_TheLoai, null, values) < 0) {
                return -1;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return 1;
    }
    public boolean checkTheLoai (String theLoai) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        String check = "SELECT * FROM TheLoai where tenTheLoai = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(check, new String[]{theLoai});
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        if(cursorCount > 0) {
            return false;
        }
        return true;
    }
    public List<TheLoai> getAllKind() {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        List<TheLoai> theLoaiList = new ArrayList<>();
        String sql = "SELECT * FROM TheLoai";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TheLoai theLoai = new TheLoai();
            theLoai.setIdtheloai(Integer.parseInt(cursor.getString(0)));
            theLoai.setTentheloai(cursor.getString(1));
            theLoaiList.add(theLoai);
            cursor.moveToNext();
        }
        cursor.close();
        return theLoaiList;
    }

    public int deleteKind(TheLoai theLoai) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        int sql = sqLiteDatabase.delete("TheLoai", "idTheLoai = ?", new String[]{String.valueOf(theLoai.getIdtheloai())});
        if (sql == 0) return -1;
        return 1;
    }
}
