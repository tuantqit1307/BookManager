package com.poly.bookmanager.Fragment;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.poly.bookmanager.CartActivity;
import com.poly.bookmanager.MainActivity;
import com.poly.bookmanager.R;
import com.poly.bookmanager.model.Cart;
import com.poly.bookmanager.model.HoaDon;
import com.poly.bookmanager.projectDAO.HoaDonDAO;
import com.poly.bookmanager.projectDAO.SachDAO;

import es.dmoral.toasty.Toasty;

public class CartsFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    Button btnCreateBuyBook, btnTru, btnCong;
    TextView tvPriceBook, tvNameBook, tvNumberBook, tvShowPriceResult, tvAmount;
    EditText edtNamePersonBuy, edtAddressPersonBuy;
    int count = 0;
    int soLgSach, giaSach;
    String findSLSach[], findPrice[], masach;
    HoaDonDAO hoaDonDAO;
    HoaDon hoaDon;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    SachDAO sachDAO;
    Bundle bundle;
    public CartsFragment() {

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        hoaDonDAO = new HoaDonDAO(getContext());
        sachDAO = new SachDAO(getContext());
        initView(view);
        initBunlde();
        initSplit();
        initOnClickListener();
        return view;
    }

    private void initSplit() {
        findSLSach = tvNumberBook.getText().toString().split("\\s");
        findPrice = tvPriceBook.getText().toString().split("\\s");
        soLgSach = Integer.parseInt(findSLSach[1]);
        giaSach = Integer.parseInt(findPrice[2]);
    }

    public void initBunlde() {
        bundle = getArguments();
        if(bundle != null) {
            masach = bundle.getString("idBook");
            tvNameBook.setText("Tên sách: "+bundle.getString("nameBook"));
            tvPriceBook.setText(bundle.getString("priceBook"));
            tvNumberBook.setText("còn " +bundle.getInt("numberBook") + " cuốn.");
        } else {
            Toasty.error(getContext(), "Không nhận được dữ liệu", Toasty.LENGTH_LONG).show();
        }
    }

    private void initOnClickListener() {
        btnCreateBuyBook.setOnClickListener(this);
        btnCong.setOnClickListener(this);
        btnTru.setOnClickListener(this);
    }

    private void initView(View view) {
        btnCreateBuyBook = view.findViewById(R.id.btnCreateBuyBook);
        tvPriceBook = view.findViewById(R.id.tvPriceBook);
        tvNameBook = view.findViewById(R.id.tvNameBook);
        tvNumberBook = view.findViewById(R.id.tvNumberBook);
        btnTru = view.findViewById(R.id.btnTru);
        btnCong = view.findViewById(R.id.btnCong);
        tvAmount = view.findViewById(R.id.tvAmount);
        tvShowPriceResult = view.findViewById(R.id.tvShowPriceResult);
    }

    @Override
    public void onClick(final View view) {
        Button b = (Button) view;
        switch (b.getId()) {
            case R.id.btnCreateBuyBook:
                MainActivity.cartArrayList.add(new Cart(masach, tvNameBook.getText().toString(), tvAmount.getText().toString(), tvShowPriceResult.getText().toString()));
                tvNumberBook.setText("còn "+ String.valueOf(bundle.getInt("numberBook")-Integer.parseInt(tvAmount.getText().toString()))+" cuốn.");
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCong:
                tvAmount.setText(String.valueOf(count==soLgSach?soLgSach:++count));
                tvShowPriceResult.setText(String.valueOf(Integer.parseInt(tvAmount.getText().toString())*giaSach)+" VND");
                break;
            case R.id.btnTru:
                tvAmount.setText(String.valueOf(count==0?0:--count));
                tvShowPriceResult.setText(String.valueOf(Integer.parseInt(tvAmount.getText().toString())*giaSach)+" VND");
                break;
            default:
        }
    }
}
