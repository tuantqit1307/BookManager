package com.poly.bookmanager;



import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.navigation.NavigationView;
import com.poly.bookmanager.Fragment.HoaDonFragment;
import com.poly.bookmanager.Fragment.SachFragment;
import com.poly.bookmanager.Fragment.TheLoaiFragment;
import com.poly.bookmanager.Fragment.ThongKeFragment;
import com.poly.bookmanager.Helper.NotificationHelper;
import com.poly.bookmanager.model.Cart;
import com.poly.bookmanager.model.Top10Buy;
import com.poly.bookmanager.projectDAO.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;

import static com.poly.bookmanager.Login.Login.NAME_LOGIN;
import static com.poly.bookmanager.Login.Login.USERNAME;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static ArrayList<Top10Buy> Top10ArrayList = new ArrayList<>();
    public static ArrayList<Cart> cartArrayList = new ArrayList<>();
    SharedPreferences hello;
    AlertDialog alertDialog;
    UserDAO userDAO;
    Fragment fragment;
    View headerView;
    NavigationView navigationView;
    DrawerLayout drawer;
    String username;
    Toolbar toolbar;
    NotificationHelper helper;
    SharedPreferences sharedPreferences;
    TextView titleToolbar, linkWebToolbar, profileName, infoPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(cartArrayList.size() >=1 ) {
            createNotificationChannel();
        }
        userDAO = new UserDAO(this);
        addFragment(new SachFragment());
        initView();
        initTille();
        initAccount();
        initNavigation();
    }
    private void createNotificationChannel() {
        helper = new NotificationHelper(this);
        Intent fullScreenIntent = new Intent(this, CartActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = helper.getEDMTChannelNotification(
                "GIỎ HÀNG CHƯA ĐƯỢC THANH TOÁN",
                "Có một đơn hàng của bạn chưa thanh toán.",
                "Có một đơn hàng của bạn chưa thanh toán. Vui lòng xem lại hoặc tiến hành thanh toán");
        builder.setFullScreenIntent(fullScreenPendingIntent, true);
        helper.getManager().notify(new Random().nextInt(),builder.build());
    }
    private void initNavigation() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initAccount() {
        sharedPreferences = getApplicationContext().getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(USERNAME, "");
        ArrayList<String> fullName = userDAO.getNamePhoneByUsername(username);
        if (fullName.size() > 0 ) {
            profileName.setText("Xin chào: " + fullName.get(0));
            infoPhone.setText(fullName.get(1));
        } else {
            logout();
        }

    }

    private void logout() {
        hello = getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE);
        hello.edit().clear().commit();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void initTille() {
        if(titleToolbar != null && toolbar != null) {
            titleToolbar.setText(R.string.title_toolbar);
            linkWebToolbar.setText(R.string.link_web);
            setSupportActionBar(toolbar);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        titleToolbar = findViewById(R.id.titleToolbar);
        linkWebToolbar = findViewById(R.id.linkWebToolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        profileName = headerView.findViewById(R.id.infoLogin);
        infoPhone = headerView.findViewById(R.id.infoPhone);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                this.thoat();
            } else {
                if (getFragmentManager().getBackStackEntryCount() > 1) {
                    getFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cart) {
            if(cartArrayList.size() == 0) {
                Toasty.info(getApplicationContext(), "Giỏ hàng của bạn đang rỗng", Toasty.LENGTH_LONG).show();
            }
            startActivity(new Intent(this, CartActivity.class));
            return true;
        } else if(id == R.id.listUser) {
            startActivity(new Intent(this, UserActivity.class));
            return true;
        } else if(id == R.id.introduce) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.doiMk:
                this.changePassword();
                break;
            case R.id.dangXuat:
                this.out();
                break;
            case R.id.thoat:
                this.thoat();
                break;
            case R.id.theLoai:
                fragment = new TheLoaiFragment();
                loadFragment(fragment);
                break;
            case R.id.book:
                fragment = new SachFragment();
                loadFragment(fragment);
                break;
            case R.id.hoaDon:
                fragment = new HoaDonFragment();
                loadFragment(fragment);
                break;
            case R.id.thongKe:
                fragment = new ThongKeFragment();
                loadFragment(fragment);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //SIGN OUT APPLICATION
    private void out() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle(R.string.dang_xuat_khong);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_changepass);
        builder.setPositiveButton(R.string.dang_xuat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
                Animatoo.animateInAndOut(MainActivity.this);
                Toasty.info(getApplicationContext(), R.string.dang_xuat_thanh_cong, Toast.LENGTH_LONG).show();
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
    //EXIT APPLICATION
    private void thoat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle(R.string.thoat_app);
        builder.setIcon(R.drawable.ic_exit);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.thoat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
        builder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
    //CHANGE PASSWORD
    private void changePassword() {
        AlertDialog.Builder dialogBuilder =	new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater	= getLayoutInflater();
        View dialogView	=	inflater.inflate(R.layout.dialog_changepass, null);
        final EditText pwOld = dialogView.findViewById(R.id.edPassOld);
        final EditText pwNew = dialogView.findViewById(R.id.edPassNew);
        final EditText pwNewConfirm = dialogView.findViewById(R.id.edPassNewConfirm);
        Button btnChangePassword = dialogView.findViewById(R.id.btnChangePassword);
        TextView tvBackLogin = dialogView.findViewById(R.id.tvBackLogin);
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDAO = new UserDAO(getApplicationContext());
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE);
                String username = sharedPreferences.getString(USERNAME,"");
                List<String> password = userDAO.getPasswordByUsername(username);
                if (!pwOld.getText().toString().equals(password.get(0))) {
                    Toasty.error(getApplicationContext(), R.string.mk_khong_dung, Toast.LENGTH_LONG).show();
                } else {
                    if (pwNew.getText().toString().equals(pwNewConfirm.getText().toString())) {
                        userDAO.changePassword(username, pwNew.getText().toString());
                        Toasty.success(getApplicationContext(), R.string.thay_doi_mk, Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    } else {
                        Toasty.error(getApplicationContext(), R.string.mat_khau_khong_trung, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    //LOAD FRAGMENT WHEN CLICK
    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).add(R.id.frame_container, fragment).addToBackStack(null).commit();
    }
    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.frame_container, fragment).addToBackStack(null).commit();
    }
}