package com.poly.bookmanager.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.bookmanager.LoginActivity;
import com.poly.bookmanager.MainActivity;
import com.poly.bookmanager.R;
import com.poly.bookmanager.adapter.AdapterTheLoai;
import com.poly.bookmanager.model.TheLoai;
import com.poly.bookmanager.projectDAO.TheLoaiDAO;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class TheLoaiFragment extends Fragment {
    AlertDialog alertDialog;
    TheLoaiDAO theLoaiDAO;
    List<TheLoai> theLoaiList = new ArrayList<>();
    EditText edNameTheLoai;

    public TheLoaiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_loai, container, false);
        TextView btnAddTheLoai = view.findViewById(R.id.idTheLoai);
        theLoaiDAO = new TheLoaiDAO(getContext());
        final RecyclerView rv_theloai = view.findViewById(R.id.rv_theloai);
        theLoaiList = theLoaiDAO.getAllKind();
        AdapterTheLoai a = new AdapterTheLoai(theLoaiList, view.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_theloai.setLayoutManager(layoutManager);
        rv_theloai.setItemAnimator(new DefaultItemAnimator());
        rv_theloai.setAdapter(a);
        btnAddTheLoai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_addtheloai, null);
                    edNameTheLoai = dialogView.findViewById(R.id.edNameTheLoai);
                    Button btnTheLoai = dialogView.findViewById(R.id.btnTheLoai);
                    TextView btnBackLogin = dialogView.findViewById(R.id.tvBackLogin);
                    btnTheLoai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean checkName = theLoaiDAO.checkTheLoai(edNameTheLoai.getText().toString());
                            if (edNameTheLoai.getText().toString().isEmpty()) {
                                Toasty.error(getContext(), R.string.khong_duoc_de_trong, Toasty.LENGTH_LONG).show();
                            } else if(checkName) {
                                TheLoai theLoai = new TheLoai(edNameTheLoai.getText().toString());
                                theLoaiDAO.addKind(theLoai);
                                theLoaiList = theLoaiDAO.getAllKind();
                                AdapterTheLoai a = new AdapterTheLoai(theLoaiList, view.getContext());
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                rv_theloai.setLayoutManager(layoutManager);
                                rv_theloai.setItemAnimator(new DefaultItemAnimator());
                                rv_theloai.setAdapter(a);
                                Toasty.success(getActivity(), R.string.them_thanh_cong, Toast.LENGTH_SHORT).show();
                            } else {
                                alertDialog.hide();
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity(), R.style.AlertDialog);
                                dialogBuilder.setTitle("Thể loại này đã tồn tại!");
                                dialogBuilder.setIcon(R.drawable.logo);
                                dialogBuilder.setCancelable(false);
                                dialogBuilder.setPositiveButton("Thay đổi", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        alertDialog.show();
                                    }
                                });
                                dialogBuilder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                alertDialog = dialogBuilder.create();
                                alertDialog.show();
                            }
                        }
                    });
                    btnBackLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    dialogBuilder.setView(dialogView);
                    alertDialog = dialogBuilder.create();
                    alertDialog.show();
                }
            });

        return view;
    }
}
