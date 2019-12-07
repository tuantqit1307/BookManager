package com.poly.bookmanager.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.poly.bookmanager.R;
import com.poly.bookmanager.adapter.AdapterHoaDonChiTiet;
import com.poly.bookmanager.model.HoaDon;
import com.poly.bookmanager.model.HoaDonChiTiet;
import com.poly.bookmanager.projectDAO.HoaDonChiTietDAO;
import com.poly.bookmanager.projectDAO.HoaDonDAO;

import java.util.List;
import java.util.Locale;

public class HoaDonChiTietFragment extends Fragment {
    RecyclerView lv_hdct;
    TextView billDateBuy, idBill, billAmount, billMoney;
    Bundle bundle;
    HoaDonChiTietDAO chiTietDAO;
    HoaDonDAO hoaDonDAO;
    int value;
    HoaDonChiTiet hoaDonChiTiet;
    List<HoaDonChiTiet> hoaDonChiTiets;
    List<HoaDon> hoaDons;
    HoaDon hoaDon;
    public HoaDonChiTietFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa_don_chi_tiet, container, false);
        initView(view);
        getBundle();
        setView();
        setAdapterView();
        setTextView();
        return view;
    }

    private void setAdapterView() {
        AdapterHoaDonChiTiet adapterHoaDonChiTiet = new AdapterHoaDonChiTiet(chiTietDAO.getAllHoaDon(hoaDon.getIdHoaDon()), getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        lv_hdct.setLayoutManager(layoutManager);
        lv_hdct.setItemAnimator(new DefaultItemAnimator());
        lv_hdct.setAdapter(adapterHoaDonChiTiet);
    }

    private void setTextView() {
        billDateBuy.setText(hoaDon.getDateBuy());
        idBill.setText("ID"+hoaDon.getIdHoaDon());
        billAmount.setText(hoaDon.getSumAmount()+ " Cuốn");
        billMoney.setText(hoaDon.getSumPrice()+ " VND");
    }

    private void setView() {
        chiTietDAO = new HoaDonChiTietDAO(getContext());
        hoaDonDAO = new HoaDonDAO(getContext());
        hoaDons = hoaDonDAO.getAllHoaDon();
        hoaDon = hoaDons.get(value);
        hoaDonChiTiets = chiTietDAO.getAllHoaDon();
        hoaDonChiTiet = hoaDonChiTiets.get(value);
    }

    private void getBundle() {
        bundle = getArguments();
        value = bundle.getInt("position");
    }

    private void initView(View view) {
        lv_hdct = view.findViewById(R.id.lv_hdct);
        billDateBuy = view.findViewById(R.id.billDateBuy);
        idBill = view.findViewById(R.id.idBill);
        billAmount = view.findViewById(R.id.billAmount);
        billMoney = view.findViewById(R.id.billMoney);
    }

}
