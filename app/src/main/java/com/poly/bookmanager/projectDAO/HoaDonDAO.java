package com.poly.bookmanager.projectDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.bookmanager.dbManager.DatabaseSQLite;
import com.poly.bookmanager.model.HoaDon;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HoaDonDAO {

    DatabaseSQLite databaseSQLite;
    SQLiteDatabase sqLiteDatabase;
    public static final String TABLE_HD = "HoaDon";

    public HoaDonDAO(Context context) {
        databaseSQLite = new DatabaseSQLite(context);
    }

    public int addHoaDon(HoaDon hd) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idHoaDon", hd.getIdHoaDon());
        values.put("sumAmount", hd.getSumAmount());
        values.put("sumPrice", hd.getSumPrice());
        values.put("dateHoaDon", hd.getDateBuy());
        try {
            if (sqLiteDatabase.insert(TABLE_HD, null, values) < 0) {
                return -1;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return 1;
    }
    public List<HoaDon> getAllHoaDon() {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        List<HoaDon> userList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HoaDon hd = new HoaDon();
            hd.setIdHoaDon(cursor.getString(0));
            hd.setSumAmount(cursor.getString(1));
            hd.setSumPrice(cursor.getString(2));
            hd.setDateBuy(cursor.getString(3));
            userList.add(hd);
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }
    public int deleteHoaDon(HoaDon hoaDon) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        int sql = sqLiteDatabase.delete("HoaDon", "idHoaDon = ?", new String[]{String.valueOf(hoaDon.getIdHoaDon())});
        if (sql == 0) return -1;
        return 1;
    }


}
