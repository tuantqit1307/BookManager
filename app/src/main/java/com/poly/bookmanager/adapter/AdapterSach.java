package com.poly.bookmanager.adapter;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.poly.bookmanager.BookDetailsActivity;

import com.poly.bookmanager.R;
import com.poly.bookmanager.model.Sach;

import com.poly.bookmanager.projectDAO.SachDAO;

import java.io.ByteArrayInputStream;

import java.util.ArrayList;
import java.util.List;

import static com.poly.bookmanager.Fragment.SachFragment.REQUET_CODE;


public class AdapterSach  extends RecyclerView.Adapter<AdapterSach.ViewHolder> implements Filterable {
    private List<Sach> sachList;
    List<Sach> getListFilter;
    public Context context;
    public SachDAO sachDAO;
    public Sach sach;
    Intent intent;
    Bundle values;
    LayoutInflater inflater;

    public AdapterSach(List<Sach> sachList, Context context) {
        this.sachList = sachList;
        this.context = context;
        this.getListFilter = sachList;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        sachDAO = new SachDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.item_book, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSach.ViewHolder holder, final int position) {
            sach = sachList.get(position);
            holder.nameBook.setText(sach.getTieuDe());
            holder.priceBook.setText(sach.getGiasach());
            holder.athorBook.setText(sach.getTacGia());
            holder.imgBook.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(sach.getHinhanh())));
    }

    @Override
    public int getItemCount() {
        return sachList.size();
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()){
                    sachList = getListFilter;
                }else {
                    ArrayList<Sach> sachArrayList = new ArrayList<>();
                    for (Sach sach: getListFilter){
                        if (sach.getTacGia().toLowerCase().contains(charString) ||
                                sach.getGiasach().toLowerCase().contains(charString) ||
                                sach.getTieuDe().toLowerCase().contains(charString)){
                            sachArrayList.add(sach);
                        }
                    }
                    sachList = sachArrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sachList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                sachList = (List<Sach>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBook;
        public TextView nameBook, priceBook, athorBook, detailsBook;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            nameBook = itemView.findViewById(R.id.nameBook);
            priceBook = itemView.findViewById(R.id.giaBook);
            athorBook = itemView.findViewById(R.id.tacGiaBook);
            detailsBook = itemView.findViewById(R.id.chitietBook);
            detailsBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sach = sachList.get(getAdapterPosition());
                    intent = new Intent(context, BookDetailsActivity.class);
                    values = new Bundle();
                    values.putString("masach", sach.getIdSach());
                    values.putString("tieude", sach.getTieuDe());
                    values.putString("tenTheLoai", sach.getTheLoai());
                    values.putString("tacgia", sach.getTacGia());
                    values.putString("nxb",sach.getNxb());
                    values.putString("giaban", sach.getGiasach());
                    values.putInt("soluong", sach.getSoluong());
                    intent.putExtras(values);
                    ((Activity) context).startActivityForResult(intent, REQUET_CODE);
                }

            });
        }
    }
}

