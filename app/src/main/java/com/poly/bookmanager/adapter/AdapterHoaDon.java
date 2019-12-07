package com.poly.bookmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.poly.bookmanager.R;
import com.poly.bookmanager.model.HoaDon;

import com.poly.bookmanager.projectDAO.HoaDonChiTietDAO;
import com.poly.bookmanager.projectDAO.HoaDonDAO;


import java.util.List;

import es.dmoral.toasty.Toasty;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.ViewHolder> {
    public List<HoaDon> hoaDonList;
    public Context context;
    public HoaDonDAO hoaDonDAO;
    public HoaDonChiTietDAO chiTietDAO;
    public LayoutInflater inflater;
    public HoaDon hoaDon;

    public AdapterHoaDon(List<HoaDon> hoaDonList, Context context) {
        this.hoaDonList = hoaDonList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        hoaDonDAO = new HoaDonDAO(context);
        chiTietDAO = new HoaDonChiTietDAO(context);
    }

    @NonNull
    @Override
    public AdapterHoaDon.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_hoadon, parent, false);
        return new AdapterHoaDon.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHoaDon.ViewHolder holder, int position) {
        hoaDon = hoaDonList.get(position);
        holder.tvMaHD.setText(" ID"+hoaDon.getIdHoaDon());
        holder.tvDateHD.setText(" "+hoaDon.getDateBuy());
    }

    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMaHD, tvDateHD;
        public Button btnDelHoaDon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateHD = itemView.findViewById(R.id.tvDate);
            tvMaHD = itemView.findViewById(R.id.tvHD);
            btnDelHoaDon = itemView.findViewById(R.id.btnDelHoaDon);
            btnDelHoaDon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hoaDonDAO = new HoaDonDAO(view.getContext());
                    HoaDon hoaDon = hoaDonList.get(getAdapterPosition());
                    hoaDonDAO.deleteHoaDon(hoaDon);
                    chiTietDAO.deleteHDCT(hoaDon.getIdHoaDon());
                    hoaDonList = hoaDonDAO.getAllHoaDon();
                    Toasty.success(view.getContext(), R.string.xoa_thanh_cong, Toast.LENGTH_LONG).show();
                    hoaDonList.remove(hoaDon);
                    Animatoo.animateFade(view.getContext());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
