package com.poly.bookmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.poly.bookmanager.Login.Login;
import com.poly.bookmanager.dbManager.DatabaseSQLite;
import com.poly.bookmanager.model.User;
import com.poly.bookmanager.projectDAO.UserDAO;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private EditText edUser, edPass;
    private Button btnLogin;
    private TextView tvSign, tvForgotPass;
    AlertDialog dialog;
    DatabaseSQLite databaseSQLite;
    UserDAO userDAO;
    AlertDialog.Builder dialogBuilder;
    CheckBox ckbSaveLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseSQLite = new DatabaseSQLite(this);
        initView();
        initOnClickButton();
    }

    public void initOnClickButton() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        if(!new Login(this).loginOut()) {
            autoLogin();
        }

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPass();
            }
        });
        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpAccount();
            }
        });
    }

    public void signUpAccount() {
        dialogBuilder =	new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater	= getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView	=	inflater.inflate(R.layout.dialog_signup, null);
        final EditText edUser = dialogView.findViewById(R.id.edUsername);
        final EditText edPhone = dialogView.findViewById(R.id.edPhone);
        final EditText edPass = dialogView.findViewById(R.id.edPassword);
        final EditText edRePass = dialogView.findViewById(R.id.edConfirmPassword);
        final EditText edFullName = dialogView.findViewById(R.id.edFullName);
        TextView tvBackLogin = dialogView.findViewById(R.id.tvBackLogin);
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final Button btnSignUp = dialogView.findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDAO = new UserDAO(LoginActivity.this);
                if(edUser.getText().toString().isEmpty() ||
                        edPhone.getText().toString().isEmpty() ||
                        edPass.getText().toString().isEmpty() ||
                        edRePass.getText().toString().isEmpty() ||
                        edFullName.getText().toString().isEmpty()) {
                    Toasty.error(LoginActivity.this, R.string.khong_duoc_de_trong, Toast.LENGTH_SHORT).show();
                } else if(!edPass.getText().toString().equals(edRePass.getText().toString())){
                    Toasty.error(LoginActivity.this, R.string.mat_khau_khong_trung, Toast.LENGTH_SHORT).show();
                }else{
                    if(edPass.getText().toString().equals(edRePass.getText().toString())) {
                        boolean checkUsername = userDAO.checkUsername(edUser.getText().toString());
                        boolean checkPhone = userDAO.checkPhone(edPhone.getText().toString());
                        if(checkUsername) {
                            if (checkPhone) {
                                User user = new User(edUser.getText().toString(), edPhone.getText().toString(), edPass.getText().toString(), edFullName.getText().toString());
                                userDAO.addUser(user);
                                Toasty.success(LoginActivity.this, R.string.tao_tai_khoan_thanh_cong, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                dialog.hide();
                                dialogBuilder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialog);
                                dialogBuilder.setTitle(R.string.sdt_ton_tai);
                                dialogBuilder.setIcon(R.drawable.logo);
                                dialogBuilder.setCancelable(false);
                                dialogBuilder.setPositiveButton(R.string.dang_nhap, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogBuilder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        dialog.show();
                                    }
                                });
                                AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();
                            }
                        } else {
                            dialog.hide();
                            dialogBuilder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialog);
                            dialogBuilder.setTitle(R.string.tk_ton_tai);
                            dialogBuilder.setIcon(R.drawable.logo);
                            dialogBuilder.setCancelable(false);
                            dialogBuilder.setPositiveButton(R.string.dang_nhap, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                }
                            });
                            dialogBuilder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    dialog.show();
                                }
                            });
                            AlertDialog alertDialog = dialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                }
            }
        });
        dialogBuilder.setView(dialogView);
        dialog = dialogBuilder.create();
        dialog.show();
    }
    public void forgotPass() {
        dialogBuilder =	new AlertDialog.Builder(LoginActivity.this);
        @SuppressLint("InflateParams") View dialogView	=	getLayoutInflater().inflate(R.layout.dialog_forgotpass, null);
        final EditText edPhoneForgot = dialogView.findViewById(R.id.edPhone);
        Button btnResetPass = dialogView.findViewById(R.id.btnResetPass);
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edPhoneForgot.getText().toString().isEmpty()) {
                    Toasty.error(getApplicationContext(), R.string.nhap_sdt, Toasty.LENGTH_LONG).show();
                } else {
                    userDAO = new UserDAO(LoginActivity.this);
                    ArrayList<String> password = userDAO.getPasswordByPhone(edPhoneForgot.getText().toString());
                    if(password.isEmpty()) {
                        Toasty.error(LoginActivity.this, R.string.sdt_chua_dang_ky, Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.info(LoginActivity.this, "Số điện thoại: " + edPhoneForgot.getText().toString() + "\nMật khẩu: " + password.get(0) + "", Toasty.LENGTH_LONG).show();
                    }
                }
            }
        });
        TextView tvBackLogin = dialogView.findViewById(R.id.tvBackLogin);
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        dialog = dialogBuilder.create();
        dialog.show();
    }
    public void login() {
        userDAO = new UserDAO(LoginActivity.this);
        if (edUser.getText().toString().isEmpty() || edPass.getText().toString().isEmpty()) {
            Toasty.error(getApplicationContext(), R.string.nhap_tk_va_mk, Toasty.LENGTH_LONG).show();
        } else {
            //check username when had account exits.
            if(userDAO.checkLogin(edUser.getText().toString().trim(),edPass.getText().toString().trim())){
                boolean cancel = false;
                View focusView = null;
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    //save pass when click checkbox
                    if (ckbSaveLogin.isChecked()) {
                        saveLogin(edUser.getText().toString().trim(),edPass.getText().toString().trim());
                    } else {
                        getUsername(edUser.getText().toString().trim());
                    }
                }
                Toasty.success(getApplicationContext(), R.string.dang_ky_thanh_cong, Toasty.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Animatoo.animateShrink(LoginActivity.this);//animation when click button
                LoginActivity.this.finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialog);
                builder.setTitle(R.string.sai_tk_mk);
                builder.setCancelable(false);
                builder.setIcon(R.drawable.logo);
                builder.setPositiveButton(R.string.dang_ky, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        signUpAccount();
                    }
                });
                builder.setNegativeButton(R.string.thu_lai, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }

    }

    private void initView() {
        edUser = findViewById(R.id.edUsername);
        edPass = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSign = findViewById(R.id.tvSign);
        tvForgotPass = findViewById(R.id.tvForgot);
        ckbSaveLogin = findViewById(R.id.ckbSave);
    }
    //save login
    private void saveLogin(String user, String pass) {
        new Login(this).SavePass(user, pass);
    }
    //get username show to text view
    private void getUsername(String user) {
        new Login(this).getUsername(user);
    }

    //auto login when to save pass
    private void autoLogin() {
        startActivity(new Intent(this, MainActivity.class));
        LoginActivity.this.finish();
    }
    //onclick logo
    public void imgClick(View view) {
                Toasty.warning(getApplicationContext(), R.string.vui_long_dang_nhap, Toasty.LENGTH_LONG).show();
    }


}
