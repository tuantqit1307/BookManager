package com.poly.bookmanager.projectDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.bookmanager.dbManager.DatabaseSQLite;

import com.poly.bookmanager.model.HoaDon;
import com.poly.bookmanager.model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HoaDonChiTietDAO {

    DatabaseSQLite databaseSQLite;
    SQLiteDatabase sqLiteDatabase;
    public static final String TABLE_HDCT = "HoaDonChiTiet";

    public HoaDonChiTietDAO(Context context) {
        databaseSQLite = new DatabaseSQLite(context);
    }


    public int addHoaDonCT(HoaDonChiTiet hd) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idHoaDon", hd.getIdHoaDon());
        values.put("nameHDCT", hd.getNameHDCT());
        values.put("amountNow", hd.getAmountNow());
        values.put("priceNow", hd.getPriceNow());
        values.put("dateNow", hd.getDateNow());
        try {
            if (sqLiteDatabase.insert(TABLE_HDCT, null, values) < 0) {
                return -1;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return 1;
    }
    public List<HoaDonChiTiet> getAllHoaDon(String maHD) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        List<HoaDonChiTiet> userList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonChiTiet where idHoaDon = '" + maHD + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HoaDonChiTiet hd = new HoaDonChiTiet();
            hd.setIdHDCT(cursor.getInt(0));
            hd.setIdHoaDon(cursor.getString(1));
            hd.setNameHDCT(cursor.getString(2));
            hd.setAmountNow(cursor.getString(3));
            hd.setPriceNow(cursor.getString(4));
            hd.setDateNow(cursor.getString(5));
            userList.add(hd);
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }
    public List<HoaDonChiTiet> getAllHoaDon() {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        List<HoaDonChiTiet> userList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonChiTiet";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HoaDonChiTiet hd = new HoaDonChiTiet();
            hd.setIdHDCT(cursor.getInt(0));
            hd.setIdHoaDon(cursor.getString(1));
            hd.setNameHDCT(cursor.getString(2));
            hd.setAmountNow(cursor.getString(3));
            hd.setPriceNow(cursor.getString(4));
            hd.setDateNow(cursor.getString(5));
            userList.add(hd);
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }
    public List<HoaDonChiTiet> sumMoneyHDCT (String sqlite) {
        sqLiteDatabase = databaseSQLite.getReadableDatabase();
        List<HoaDonChiTiet> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlite, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setIdHDCT(cursor.getInt(0));
            hoaDonChiTiet.setIdHoaDon(cursor.getString(1));
            hoaDonChiTiet.setNameHDCT(cursor.getString(2));
            hoaDonChiTiet.setAmountNow(cursor.getString(3));
            hoaDonChiTiet.setPriceNow(cursor.getString(4));
            hoaDonChiTiet.setDateNow(cursor.getString(5));
            list.add(hoaDonChiTiet);
            cursor.moveToNext();
        }
        return list;
    }
    public ArrayList<String> sumMoneyHDCTTT (String date) {
        sqLiteDatabase = databaseSQLite.getReadableDatabase();
        String sql = "SELECT SUM(priceNow) FROM HoaDonChiTiet WHERE dateNow =  '" + date + "' ";
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String password = cursor.getString(0);
                list.add(password);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }
    public boolean deleteHDCT(String mHD)
    {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        return sqLiteDatabase.delete("HoaDonChiTiet", "idHoaDon = ?", new String[]{mHD}) > 0;
    }

}