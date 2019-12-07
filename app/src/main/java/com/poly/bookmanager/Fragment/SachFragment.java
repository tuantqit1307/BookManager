package com.poly.bookmanager.Fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poly.bookmanager.AddBookActivity;
import com.poly.bookmanager.MainActivity;
import com.poly.bookmanager.R;
import com.poly.bookmanager.adapter.AdapterSach;
import com.poly.bookmanager.adapter.AdapterTheLoai;
import com.poly.bookmanager.model.Sach;
import com.poly.bookmanager.projectDAO.SachDAO;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;


public class SachFragment extends Fragment {
    SachDAO sachDAO;
    EditText searchBook;
    public static final int REQUET_CODE = 101;
    AdapterSach a;
    RecyclerView rv_sach;
    FloatingActionButton floatingActionButton;
    RecyclerView.LayoutManager layoutManager;
    Intent i;
    public SachFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach, container,false);
        sachDAO = new SachDAO(getContext());
        initView(view);
        initAdapter(view);
        initSearch();
        initActionButton();
        return view;
    }

    private void initSearch() {
        searchBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                a.getFilter().filter(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                a.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                a.getFilter().filter(editable);
            }
        });
    }


    private void initActionButton() {
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getActivity(), AddBookActivity.class);
                Animatoo.animateZoom(getActivity());
                startActivityForResult(i,REQUET_CODE);
            }
        });
    }

    private void initAdapter(View view) {
        a = new AdapterSach(sachDAO.getAllSach(), view.getContext());
        layoutManager = new LinearLayoutManager(getContext());
        rv_sach.setLayoutManager(layoutManager);
        rv_sach.setItemAnimator(new DefaultItemAnimator());
        rv_sach.setAdapter(a);
    }

    private void initView(View view) {
        floatingActionButton = view.findViewById(R.id.addBook);
        rv_sach = view.findViewById(R.id.rv_book);
        searchBook = view.findViewById(R.id.searchBook);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUET_CODE && resultCode == RESULT_OK){
            a = new AdapterSach(sachDAO.getAllSach(), getActivity());
            rv_sach.setAdapter(a);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
