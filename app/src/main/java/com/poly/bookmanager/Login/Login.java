package com.poly.bookmanager.Login;

import android.content.Context;
import android.content.SharedPreferences;

public class Login {
    Context context;
    SharedPreferences sharedPreferences;
    public static String NAME_LOGIN = "LoginDetails";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public Login(Context context) {
        this.context = context;
    }
    //lưu mật khẩu
    public void SavePass (String username, String password) {
        sharedPreferences = context.getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD,password);
        editor.commit();
    }
    //lấy thông tin user
    public void getUsername(String username) {
        sharedPreferences = context.getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.commit();
    }
    //thoát
    public boolean loginOut() {
        sharedPreferences = context.getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE);
        boolean isUsername = sharedPreferences.getString(USERNAME, "").isEmpty();
        boolean isPassword = sharedPreferences.getString(PASSWORD,"").isEmpty();
        return isUsername || isPassword;
    }
}

