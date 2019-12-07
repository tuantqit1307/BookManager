package com.poly.bookmanager.projectDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.bookmanager.dbManager.DatabaseSQLite;
import com.poly.bookmanager.model.Sach;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SachDAO {
    DatabaseSQLite databaseSQLite;
    SQLiteDatabase sqLiteDatabase;
    public static final String TABLE_Sach = "Sach";

    public SachDAO(Context context) {
        databaseSQLite = new DatabaseSQLite(context);
    }
    public int addSach(Sach sach) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masach", sach.getIdSach());
        values.put("tenTheLoai", sach.getTheLoai());
        values.put("tieude",sach.getTieuDe());
        values.put("tacgia", sach.getTacGia());
        values.put("nxb",sach.getNxb());
        values.put("giaban", sach.getGiasach());
        values.put("soluong", sach.getSoluong());
        values.put("hinhanh", sach.getHinhanh());
        try {
            if (sqLiteDatabase.insert(TABLE_Sach, null, values) < 0) {
                return -1;
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return 1;
    }
    public List<Sach> getAllSach() {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        List<Sach> sachList = new ArrayList<>();
        String sql = "SELECT * FROM Sach";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sach sach = new Sach();
            sach.setIdSach(cursor.getString(0));
            sach.setTheLoai(cursor.getString(1));
            sach.setTieuDe(cursor.getString(2));
            sach.setTacGia(cursor.getString(3));
            sach.setNxb(cursor.getString(4));
            sach.setGiasach(cursor.getString(5));
            sach.setSoluong(cursor.getInt(6));
            sach.setHinhanh(cursor.getBlob(7));
            sachList.add(sach);
            cursor.moveToNext();
        }
        cursor.close();
        return sachList;
    }

    public List<String> getNameBookByID() {
        List<String> lst = new ArrayList<>();
        String sqla= "SELECT tenTheLoai FROM TheLoai";
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqla, null);
        if (cursor.moveToFirst()) {
            do {
                String tenTheLoai = cursor.getString(0);
                lst.add(tenTheLoai);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return lst;
    }

    public boolean checkIDBook (String idBook) {
        sqLiteDatabase = databaseSQLite.getReadableDatabase();
        String check = "SELECT * FROM Sach where masach = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(check, new String[]{idBook});
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        if(cursorCount > 0) {
            return false;
        }
        return true;
    }
    public List<String> getSoLuongByMaSach(String aa) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        List<String> listsoluong = new ArrayList<>();
        String sql = "SELECT soluong FROM Sach WHERE masach LIKE '" + aa + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String idsach = cursor.getString(0);
                listsoluong.add(idsach);

            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return listsoluong;
    }
    public ArrayList<byte[]> getDetailsByIDBook(String masach) {
        sqLiteDatabase = databaseSQLite.getReadableDatabase();
        ArrayList<byte[]> listDetailsBook = new ArrayList<>();
        String sql = "SELECT hinhanh FROM Sach WHERE masach LIKE '" + masach + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                byte[] hinhanh = cursor.getBlob(0);
                listDetailsBook.add(hinhanh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return listDetailsBook;
    }

    public int updateBookbyID (String masach, String tenTheLoai, String tieude, String tacgia, String nxb, String giaban, String soluong) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenTheLoai", tenTheLoai);
        values.put("tieude",tieude);
        values.put("tacgia", tacgia);
        values.put("nxb",nxb);
        values.put("giaban", giaban);
        values.put("soluong", soluong);
        return sqLiteDatabase.update("Sach",values, "masach = ?", new String[]{masach});
    }

    public int deleteBookDetails(String maSach) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        int sql = sqLiteDatabase.delete("Sach", "masach = ?", new String[]{maSach});
        if (sql == 0) return -1;
        return 1;
    }
    public int updateAmount(String masach, String soluong) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluong", soluong);
        return sqLiteDatabase.update("Sach",values, "masach = ?", new String[]{masach});
    }
    public boolean deleteHDCT(String tenTheLoai)
    {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        return sqLiteDatabase.delete("Sach", "tenTheLoai = ?", new String[]{tenTheLoai}) > 0;
    }
}
