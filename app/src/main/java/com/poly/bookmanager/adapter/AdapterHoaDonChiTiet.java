package com.poly.bookmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.bookmanager.R;
import com.poly.bookmanager.model.HoaDonChiTiet;
import com.poly.bookmanager.projectDAO.HoaDonChiTietDAO;


import java.util.List;


public class AdapterHoaDonChiTiet extends RecyclerView.Adapter<AdapterHoaDonChiTiet.ViewHolder> {
    public List<HoaDonChiTiet> hoaDonChiTietList;
    public Context context;
    public HoaDonChiTietDAO chiTietDAO;
    public LayoutInflater inflater;
    public HoaDonChiTiet hoaDonChiTiet;

    public AdapterHoaDonChiTiet(List<HoaDonChiTiet> hoaDonChiTietList, Context context) {
        this.hoaDonChiTietList = hoaDonChiTietList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        chiTietDAO = new HoaDonChiTietDAO(context);
    }

    @NonNull
    @Override
    public AdapterHoaDonChiTiet.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_hdct, parent, false);
        return new AdapterHoaDonChiTiet.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHoaDonChiTiet.ViewHolder holder, int position) {
        hoaDonChiTiet = hoaDonChiTietList.get(position);
        holder.nameItemBuy.setText(hoaDonChiTiet.getNameHDCT());
        holder.amountItemBuy.setText("Số lượng mua: "+hoaDonChiTiet.getAmountNow());
        holder.priceItemBuy.setText("Số tiền: "+hoaDonChiTiet.getPriceNow()+" VNĐ");
    }

    @Override
    public int getItemCount() {
        return hoaDonChiTietList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameItemBuy, amountItemBuy, priceItemBuy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameItemBuy = itemView.findViewById(R.id.nameItemBuy);
            amountItemBuy = itemView.findViewById(R.id.amountItemBuy);
            priceItemBuy = itemView.findViewById(R.id.priceItemBuy);
        }
    }
}
