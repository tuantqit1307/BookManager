package com.poly.bookmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.SumPathEffect;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.poly.bookmanager.Helper.NotificationHelper;
import com.poly.bookmanager.adapter.AdapterCart;

import com.poly.bookmanager.model.Cart;
import com.poly.bookmanager.model.HoaDon;

import com.poly.bookmanager.model.HoaDonChiTiet;
import com.poly.bookmanager.model.Sach;
import com.poly.bookmanager.model.Top10Buy;
import com.poly.bookmanager.projectDAO.HoaDonChiTietDAO;
import com.poly.bookmanager.projectDAO.HoaDonDAO;
import com.poly.bookmanager.projectDAO.SachDAO;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lv_carts;
    Toolbar toolbar;
    AlertDialog alertDialog;
    HoaDon hoaDon;
    HoaDonChiTietDAO hoaDonChiTiet;
    SachDAO sachDAO;
    HoaDonChiTietDAO chiTietDAO;
    HoaDonDAO hoaDonDAO;
    AlertDialog.Builder builder;
    NotificationHelper helper;

    String idHD = ""+System.currentTimeMillis();

    Button btnDelBillDetails, btnAddBillDetails, btnContinueBuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        hoaDon = new HoaDon();
        hoaDonChiTiet = new HoaDonChiTietDAO (getApplicationContext());
        sachDAO = new SachDAO(getApplicationContext());
        hoaDonDAO = new HoaDonDAO(getApplicationContext());
        chiTietDAO = new HoaDonChiTietDAO(getApplicationContext());
        initView();
        initToolbar();
        setOnClick();
        AdapterCart adapterCart = new AdapterCart(getApplicationContext(), MainActivity.cartArrayList);
        lv_carts.setAdapter(adapterCart);
    }

    private void initToolbar() {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onBackPressed();
                }
            });
    }

    @Override
    public void onBackPressed() {
        Animatoo.animateSlideRight(CartActivity.this);
        startActivity(new Intent(CartActivity.this, MainActivity.class));
    }

    private void setOnClick() {
        btnContinueBuy.setOnClickListener(this);
        btnAddBillDetails.setOnClickListener(this);
        btnDelBillDetails.setOnClickListener(this);
    }

    private void initView() {
        lv_carts = findViewById(R.id.lv_cart);
        toolbar = findViewById(R.id.toolbarCartDetails);
        btnDelBillDetails = findViewById(R.id.btnDelBillDetails);
        btnAddBillDetails = findViewById(R.id.btnAddBillDetails);
        btnContinueBuy = findViewById(R.id.btnContinueBuy);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddBillDetails:
                if(MainActivity.cartArrayList.size() == 0) {
                    builder = new AlertDialog.Builder(CartActivity.this, R.style.AlertDialog);
                    builder.setTitle(R.string.gio_hang_rong);
                    builder.setCancelable(false);
                    builder.setIcon(R.drawable.ic_changepass);
                    builder.setNegativeButton(R.string.quay_lai, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.cartArrayList.clear();
                            startActivity(new Intent(CartActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    createNotificationChannel();
                    addItemHoaDon();
                    MainActivity.cartArrayList.clear();
                }

                break;
            case R.id.btnDelBillDetails:
                builder = new AlertDialog.Builder(CartActivity.this, R.style.AlertDialog);
                builder.setTitle(R.string.gio_hang_rong);
                builder.setCancelable(false);
                builder.setIcon(R.drawable.ic_changepass);
                builder.setNegativeButton(R.string.quay_lai, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.cartArrayList.clear();
                        startActivity(new Intent(CartActivity.this, MainActivity.class));
                        finish();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.btnContinueBuy:
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                finish();
                break;
        }

    }

    private void addItemHoaDon() {
        int sumPrice = 0, sumAmount = 0;

        for(int position = 0; position < MainActivity.cartArrayList.size(); position++) {
            Cart cart = MainActivity.cartArrayList.get(position);
            sumAmount += Integer.parseInt(cart.getAmountItem());
//            for(int i = 0; i<MainActivity.Top10ArrayList.size(); i++) {
//                Top10Buy top10Buy = MainActivity.Top10ArrayList.get(i);
//                if(!top10Buy.getNameSach().equals(cart.getMasachItem())) {
//                    MainActivity.Top10ArrayList.add(new Top10Buy(cart.getMasachItem(), cart.getAmountItem(), cart.getNameItem()));
//                } else {}
//            }
//                if(!top10Buy.getMaSach().equals(cart.getMasachItem()) || top10Buy.getMaSach() == null) {
//                    MainActivity.Top10ArrayList.add(new Top10Buy(cart.getMasachItem(), cart.getAmountItem(), cart.getNameItem()));
//                } else if(top10Buy.getMaSach().equals(cart.getMasachItem())) {
//                    MainActivity.Top10ArrayList.set(i, new Top10Buy(sumAmount+""));
//                }
//            }

            //ParserInt
            String[] findPrice = cart.getPriceItem().split("\\s");
            sumPrice += Integer.parseInt(findPrice[0]);

            String dateNow = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            ////add HDCT


            chiTietDAO.addHoaDonCT(
                    new HoaDonChiTiet(
                            idHD+"",
                            cart.getNameItem()+"",
                            Integer.parseInt(cart.getAmountItem())+"",
                            Integer.parseInt(findPrice[0])+"",
                            dateNow+""
                            ));

            //update amount
            List<String> amountnow = sachDAO.getSoLuongByMaSach(cart.getMasachItem());
            sachDAO.updateAmount(cart.getMasachItem(), String.valueOf(Integer.parseInt(amountnow.get(0))-Integer.parseInt(cart.getAmountItem())));
            MainActivity.Top10ArrayList.add(new Top10Buy(cart.getMasachItem(), cart.getAmountItem(), cart.getNameItem()));

        }
        //add HD

        String dateBuy = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault()).format(new Date());
        hoaDonDAO.addHoaDon(
                new HoaDon(
                        idHD+"",
                        sumAmount+"",
                        sumPrice+"",
                        dateBuy+""));
    }

    private void createNotificationChannel() {
        helper = new NotificationHelper(this);
        Intent fullScreenIntent = new Intent(this, MainActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = helper.getEDMTChannelNotification(
                "ĐƠN HÀNG: ID"+idHD,
                "Đơn hàng của bạn đã được tạo thành công",
                "Đơn hàng của bạn đã được tạo thành công. Vui lòng kiểm tra lại trong mục hoá đơn!");
        builder.setFullScreenIntent(fullScreenPendingIntent, true);
        helper.getManager().notify(new Random().nextInt(),builder.build());
    }
}
