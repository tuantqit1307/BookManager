package com.poly.bookmanager.dbManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.poly.bookmanager.projectDAO.HoaDonChiTietDAO;
import com.poly.bookmanager.projectDAO.HoaDonDAO;
import com.poly.bookmanager.projectDAO.SachDAO;
import com.poly.bookmanager.projectDAO.TheLoaiDAO;
import com.poly.bookmanager.projectDAO.UserDAO;

public class DatabaseSQLite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BookManager.db";

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //TABLE USER
        sqLiteDatabase.execSQL("Create table User (" +
                "username text primary key not null," +
                "phone text not null," +
                "password text not null," +
                "name text not null);");

        //TABLE THỂ LOẠI
        sqLiteDatabase.execSQL("Create table TheLoai (" +
                "idTheLoai integer primary key autoincrement," +
                "tenTheLoai text not null);");

        //TABLE SÁCH
        sqLiteDatabase.execSQL("Create table Sach (" +
                "masach text primary key not null," +
                "tenTheLoai text not null," +
                "tieude text," +
                "tacgia text," +
                "nxb text," +
                "giaban integer not null,"+
                "soluong integer not null,"+
                "hinhanh blob not null,"+
                "foreign key(tenTheLoai) references TheLoai(tenTheLoai));");

        //TABLE HOÁ ĐƠN
        sqLiteDatabase.execSQL("Create table HoaDon (" +
                "idHoaDon text primary key not null," +
                "sumAmount text not null," +
                "sumPrice text not null," +
                "dateHoaDon text not null);");

        //TABLE HOÁ ĐƠN CHI TIẾT
        sqLiteDatabase.execSQL("Create table HoaDonChiTiet (" +
                "idHDCT integer primary key autoincrement," +
                "idHoaDon text not null," +
                "nameHDCT text not null," +
                "amountNow text not null," +
                "priceNow text not null," +
                "dateNow text not null," +
                "foreign key (idHoaDon) references HoaDon(idHoaDon));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ UserDAO.TABLE_User);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TheLoaiDAO.TABLE_TheLoai);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ SachDAO.TABLE_Sach);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ HoaDonDAO.TABLE_HD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ HoaDonChiTietDAO.TABLE_HDCT);
        onCreate(sqLiteDatabase);

    }
}
