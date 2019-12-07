package com.poly.bookmanager.projectDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.poly.bookmanager.dbManager.DatabaseSQLite;
import com.poly.bookmanager.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    DatabaseSQLite databaseSQLite;
    SQLiteDatabase sqLiteDatabase;
    public static final String TABLE_User = "User";
    public UserDAO(Context context) {
        databaseSQLite = new DatabaseSQLite(context);
    }

    public boolean addUser(User m) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", m.getUsername());
        values.put("phone", m.getPhone());
        values.put("password", m.getPassword());
        values.put("name", m.getName());
        long ins = sqLiteDatabase.insert(TABLE_User, null, values);
        if(ins==1) return false; else return true;
    }

    public boolean checkUsername (String username) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        String check = "SELECT * FROM User where username = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(check, new String[]{username});
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        if(cursorCount > 0) {
            return false;
        }
        return true;
    }
    public boolean checkPhone (String Phone) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        String check = "SELECT * FROM User where phone = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(check, new String[]{Phone});
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        if(cursorCount > 0) {
            return false;
        }
        return true;
    }
    public boolean checkLogin(String username, String password) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        String query = "select username, password from User where username=? and password=?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{username, password});
        int cursorCount = cursor.getCount();
        cursor.close();
        sqLiteDatabase.close();
        if(cursorCount > 0) {
            return true;
        }
        return false;
    }

    public ArrayList<String> getPasswordByPhone(String Phone) {
        sqLiteDatabase = databaseSQLite.getReadableDatabase();
        ArrayList<String> listPhone = new ArrayList<>();
        String sql = "SELECT password FROM User WHERE phone LIKE '" + Phone + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String password = cursor.getString(0);
                listPhone.add(password);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return listPhone;
    }

    public ArrayList<String> getNamePhoneByUsername(String fullname) {
        sqLiteDatabase = databaseSQLite.getReadableDatabase();
        ArrayList<String> listFullname = new ArrayList<>();
        String sql = "SELECT name,phone FROM User WHERE username LIKE '" + fullname + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String phone = cursor.getString(1);
                listFullname.add(name);
                listFullname.add(phone);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return listFullname;
    }

    public List<User> getAllUser() {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM User";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User m = new User();
            m.setUsername(cursor.getString(0));
            m.setPhone(cursor.getString(1));
            m.setPassword(cursor.getString(2));
            m.setName(cursor.getString(3));
            userList.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return userList;
    }

    public int changePassword(String username, String password) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", password);
        return sqLiteDatabase.update("User",values, "username = ?", new String[]{username});
    }

    public int changeUser(String username, String fullname, String phone) {
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone",phone);
        values.put("name", fullname);
        return sqLiteDatabase.update("User",values, "username = ?", new String[]{username});
    }

    public void deleteUser(String username){
        sqLiteDatabase = databaseSQLite.getWritableDatabase();
        sqLiteDatabase.delete("User","username = ?",new String[]{String.valueOf(username)});
    }
    public ArrayList<String> getPasswordByUsername(String username) {
        sqLiteDatabase = databaseSQLite.getReadableDatabase();
        ArrayList<String> listUsername = new ArrayList<>();
        String sql = "SELECT password FROM User WHERE username LIKE '" + username + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String pass = cursor.getString(0);
                listUsername.add(pass);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return listUsername;
    }

}
