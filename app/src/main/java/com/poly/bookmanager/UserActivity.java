package com.poly.bookmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.poly.bookmanager.adapter.AdapterUser;
import com.poly.bookmanager.projectDAO.UserDAO;

public class UserActivity extends AppCompatActivity {
    Toolbar tbUser;
    RecyclerView rv_user;
    UserDAO userDAO;
    AdapterUser adapterUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        initToolbar();
        initAdapter();
    }

    private void initAdapter() {
        userDAO = new UserDAO(UserActivity.this);
        adapterUser = new AdapterUser(userDAO.getAllUser(), getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_user.setLayoutManager(layoutManager);
        rv_user.setItemAnimator(new DefaultItemAnimator());
        rv_user.setAdapter(adapterUser);
        adapterUser.notifyDataSetChanged();
    }

    public void initToolbar() {
        tbUser.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(tbUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tbUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        tbUser = findViewById(R.id.toolbarUserDetails);
        rv_user = findViewById(R.id.rv_user);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.activity_scale_finish_enter, R.anim.activity_slide_finish_exit);
        super.onBackPressed();
    }
}
