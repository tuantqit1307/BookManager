package com.poly.bookmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.poly.bookmanager.Fragment.CartsFragment;
import com.poly.bookmanager.projectDAO.SachDAO;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class BookDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView txtNameBook, txtIdBook, txtAuthorBook, txtKindOfBook, tvAmount, tvNXB, tvPrice;
    ImageView imgBookDetails;
    Bundle bundle;
    Button btnBuyBook, btnDelBook, btnEditBook, btnShareBook;
    String masach;
    int soluong;
    AlertDialog alertDialog;
    SachDAO sachDAO;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        initView();
        initToolbar();
        initBundle();
        initOnClickListener();
    }

    public void initView() {
        toolbar = findViewById(R.id.toolbarBookDetails);
        btnShareBook = findViewById(R.id.btnShareBook);
        txtNameBook = findViewById(R.id.txtNameBook);
        txtIdBook = findViewById(R.id.txtIdBook);
        txtAuthorBook = findViewById(R.id.txtAuthorBook);
        txtKindOfBook = findViewById(R.id.txtKindOfBook);
        tvAmount = findViewById(R.id.tvAmount);
        tvNXB = findViewById(R.id.tvNXB);
        tvPrice = findViewById(R.id.tvPrice);
        imgBookDetails = findViewById(R.id.imgBookDetails);
        btnBuyBook = findViewById(R.id.btnBuyBook);
        btnDelBook = findViewById(R.id.btnDelBook);
        btnEditBook = findViewById(R.id.btnEditBook);
    }

    public void initToolbar() {
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

    public void initBundle() {
        sachDAO = new SachDAO(getApplicationContext());
        bundle = getIntent().getExtras();
        masach = bundle.getString("masach");
        txtNameBook.setText(bundle.getString("tieude")+" ");
        txtIdBook.setText("("+masach+")");
        txtAuthorBook.setText(bundle.getString("tacgia"));
        txtKindOfBook.setText(bundle.getString("tenTheLoai"));
        soluong = bundle.getInt("soluong");
        tvAmount.setText("Số lượng: "+soluong+" cuốn");
        tvNXB.setText("NXB: "+bundle.getString("nxb"));
        tvPrice.setText("Giá bán: "+bundle.getString("giaban")+" VND");
        ArrayList<byte[]> listImg = sachDAO.getDetailsByIDBook(masach);
        imgBookDetails.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(listImg.get(0))));
    }

    public void initOnClickListener() {
        btnBuyBook.setOnClickListener(this);
        btnDelBook.setOnClickListener(this);
        btnEditBook.setOnClickListener(this);
        btnShareBook.setOnClickListener(this);
        }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.activity_scale_finish_enter, R.anim.activity_slide_finish_exit);
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        switch(b.getId()) {
            case R.id.btnBuyBook:
               this.intentBuyBook();
                break;
            case R.id.btnDelBook:
                this.deleteBookDetails();
                break;
            case R.id.btnEditBook:
                this.editBook();
                break;
            case R.id.btnShareBook:
                this.shareApplication();
                break;
        }
    }

    private void intentBuyBook() {
        CartsFragment bottomSheetFragment = new CartsFragment();
        Bundle bundleBottomSheet = new Bundle();
        bundleBottomSheet.putString("idBook", masach);
        bundleBottomSheet.putString("nameBook", txtNameBook.getText().toString());
        bundleBottomSheet.putString("priceBook", tvPrice.getText().toString());
        bundleBottomSheet.putInt("numberBook", soluong);
        bottomSheetFragment.setArguments(bundleBottomSheet);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private void deleteBookDetails() {
        builder = new AlertDialog.Builder(BookDetailsActivity.this, R.style.AlertDialog);
        builder.setTitle(R.string.ban_co_muon_xoa);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_changepass);
        builder.setPositiveButton(R.string.xoa, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sachDAO.deleteBookDetails(masach);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Animatoo.animateFade(BookDetailsActivity.this);
                finish();
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

    private void editBook() {
        AlertDialog.Builder dialogBuilder =	new AlertDialog.Builder(BookDetailsActivity.this);
        LayoutInflater inflater	= getLayoutInflater();
        View dialogView	=	inflater.inflate(R.layout.dialog_editbook, null);
        final EditText edMaSach = dialogView.findViewById(R.id.edMaSach);
        final Spinner idTheLoaiSach = dialogView.findViewById(R.id.idTheLoaiSach);
        final EditText edEditTenSach = dialogView.findViewById(R.id.edEditTenSach);
        final EditText edEditTacGiaSach = dialogView.findViewById(R.id.edEditTacGiaSach);
        final EditText edEditNXB = dialogView.findViewById(R.id.edEditNXB);
        final EditText edEditGiaBanSach = dialogView.findViewById(R.id.edEditGiaBanSach);
        final EditText edEditSoLuongSach = dialogView.findViewById(R.id.edEditSoLuongSach);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,sachDAO.getNameBookByID());
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        idTheLoaiSach.setAdapter(arrayAdapter);
        edMaSach.setText(masach);
        edEditTenSach.setText(bundle.getString("tieude"));
        edEditTacGiaSach.setText(bundle.getString("tacgia"));
        edEditNXB.setText(bundle.getString("nxb"));
        edEditGiaBanSach.setText(bundle.getString("giaban"));
        edEditSoLuongSach.setText(soluong+"");
        Button btnEditSach = dialogView.findViewById(R.id.btnEditSach);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        btnEditSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNameBook.setText(edEditTenSach.getText().toString()+" ");
                txtAuthorBook.setText(idTheLoaiSach.getSelectedItem().toString());
                txtKindOfBook.setText(edEditTacGiaSach.getText().toString());
                tvAmount.setText("Số lượng: "+edEditSoLuongSach.getText().toString()+" cuốn");
                tvNXB.setText("NXB: "+edEditNXB.getText().toString());
                tvPrice.setText("Giá bán: "+edEditGiaBanSach.getText().toString()+" VND");
                sachDAO.updateBookbyID(masach, idTheLoaiSach.getSelectedItem().toString(), edEditTenSach.getText().toString(),
                        edEditTacGiaSach.getText().toString(), edEditNXB.getText().toString(), edEditGiaBanSach.getText().toString(),
                        edEditSoLuongSach.getText().toString());
                Toasty.success(getApplicationContext(), "Update thành công!", Toasty.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    private void shareApplication() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Mời bạn mua sách "+bundle.getString("tieude")+" với giá " +bundle.getString("giaban")+" VND.";
        String shareSub = ""+bundle.getString("tieude");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Chia sẻ sách: "+bundle.getString("tieude")));
    }


}
