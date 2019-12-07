package com.poly.bookmanager.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.poly.bookmanager.MainActivity;
import com.poly.bookmanager.R;
import com.poly.bookmanager.model.TheLoai;
import com.poly.bookmanager.projectDAO.SachDAO;
import com.poly.bookmanager.projectDAO.TheLoaiDAO;

import java.util.List;
import java.util.zip.Inflater;

import es.dmoral.toasty.Toasty;


public class AdapterTheLoai  extends RecyclerView.Adapter<AdapterTheLoai.ViewHolder> {
    public List<TheLoai> theLoaiList;
    public Context context;
    public TheLoaiDAO theLoaiDAO;
    public LayoutInflater inflater;
    public SachDAO sachDAO;
    AlertDialog alertDialog;

    public AdapterTheLoai(List<TheLoai> theLoaiList, Context context) {
        this.theLoaiList = theLoaiList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        theLoaiDAO = new TheLoaiDAO(context);
        sachDAO = new SachDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_theloai, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheLoai theLoai = theLoaiList.get(position);
        holder.idtheloai.setText(String.valueOf(position+1));
        holder.nametheloai.setText(theLoai.getTentheloai());
    }

    @Override
    public int getItemCount() {
        return theLoaiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idtheloai, nametheloai;
        public Button btnDeltheloai;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            idtheloai = itemView.findViewById(R.id.idTheLoai);
            nametheloai = itemView.findViewById(R.id.nameTheLoai);
            btnDeltheloai = itemView.findViewById(R.id.btnDelTheLoai);
            btnDeltheloai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    thoat(view);
                }
            });
        }
        private void thoat(final View item) {
            AlertDialog.Builder builder = new AlertDialog.Builder(item.getContext(), R.style.AlertDialog);
            builder.setTitle("Bạn có chắc chắc không?");
            builder.setMessage("Sẽ xoá toàn bộ sách của Thể Loại này trong hệ thống !");
            builder.setIcon(R.drawable.ic_exit);
            builder.setCancelable(false);
            builder.setPositiveButton("Tôi đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    theLoaiDAO = new TheLoaiDAO(item.getContext());
                    TheLoai theLoai = theLoaiList.get(getAdapterPosition());

                    sachDAO.deleteHDCT(theLoai.getTentheloai());
                    theLoaiDAO.deleteKind(theLoai);
                    theLoaiList = theLoaiDAO.getAllKind();
                    Toasty.success(item.getContext(), R.string.xoa_thanh_cong, Toast.LENGTH_LONG).show();
                    theLoaiList.remove(theLoai);
                    Animatoo.animateFade(item.getContext());
                    notifyDataSetChanged();
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
    }
}