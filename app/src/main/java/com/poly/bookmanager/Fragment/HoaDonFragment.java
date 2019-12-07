package com.poly.bookmanager.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.poly.bookmanager.Interface.RecyclerItemClickListener;
import com.poly.bookmanager.R;
import com.poly.bookmanager.adapter.AdapterHoaDon;
import com.poly.bookmanager.projectDAO.HoaDonDAO;

public class HoaDonFragment extends Fragment {

    HoaDonDAO hoaDonDAO;
    RecyclerView rv_hoadon;
    AdapterHoaDon adapterHoaDon;
    public HoaDonFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa_don, container,false);
        hoaDonDAO = new HoaDonDAO(getContext());
        rv_hoadon = view.findViewById(R.id.rv_hoadon);
        adapterHoaDon = new AdapterHoaDon(hoaDonDAO.getAllHoaDon(), view.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_hoadon.setLayoutManager(layoutManager);
        rv_hoadon.setItemAnimator(new DefaultItemAnimator());
        rv_hoadon.setAdapter(adapterHoaDon);

        adapterHoaDon.notifyDataSetChanged();
        rv_hoadon.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rv_hoadon, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle arg = new Bundle();
                arg.putInt("position", position);
                HoaDonChiTietFragment hoaDonChiTietFragment = new HoaDonChiTietFragment();
                hoaDonChiTietFragment.setArguments(arg);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                        replace(R.id.frame_container, hoaDonChiTietFragment).addToBackStack(null).commit();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                //TODO NOTHING
            }
        }));
        return view;
    }

}
