package com.poly.bookmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.poly.bookmanager.Fragment.HoaDonFragment;
import com.poly.bookmanager.model.Sach;
import com.poly.bookmanager.projectDAO.SachDAO;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import es.dmoral.toasty.Toasty;


public class AddBookActivity extends AppCompatActivity {
    Spinner tenTheLoai;
    SachDAO sachDAO;
    Toolbar toolbar;
    Button btnAddSach;
    AlertDialog.Builder builder;
    Bitmap bitmap;
    AlertDialog alertDialog;
    BitmapDrawable bitmapDrawable;
    InputStream inputStream;
    Intent intent;
    RecyclerView rv_sach;
    ByteArrayOutputStream byteArrayOutputStream;
    EditText edTenSach, edTacGiaSach, edNXB, edGiaBanSach, edSoLuongSach, edMaSach;
    String loai, ten, gia, nxb, tacgia, soluong, masach;
    ImageView addImageFolder, addImageCamera, imgAddBook;
    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;
    final int QUALITY_IMAGE = 80;
    byte[] imgBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        sachDAO = new SachDAO(AddBookActivity.this);
        initView();
        initToolbar();
        getSpinner();
    }
    //onlick button add book
    public void btnAddSach(View view) {
        try {
            addSach();
        }
        catch (NullPointerException ex) {
            Toasty.error(getApplicationContext(), R.string.ban_chua_chon_hinh, Toasty.LENGTH_LONG).show();
        }
    }
    //convert picture to bitmap
    public void convertHinhtoBitmap() {
            bitmapDrawable = (BitmapDrawable) imgAddBook.getDrawable();
            bitmap = bitmapDrawable.getBitmap();
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_IMAGE, byteArrayOutputStream);
            imgBook = byteArrayOutputStream.toByteArray();
    }
    //function add book
    private void addSach() {
        initString();
        if(loai.isEmpty() ||
        ten.isEmpty() || gia.isEmpty() || masach.isEmpty() ||
        nxb.isEmpty() || tacgia.isEmpty() ||
        soluong.isEmpty() ) {
            Toasty.error(getApplicationContext(), R.string.khong_duoc_de_trong, Toasty.LENGTH_LONG).show();
        } else {
            sachDAO = new SachDAO(AddBookActivity.this);
            boolean checkMaSach = sachDAO.checkIDBook(masach);
            if(checkMaSach){
                try {
                    sachDAO.addSach(new Sach(masach, loai,ten, tacgia, nxb, gia, Integer.parseInt(soluong), imgBook));
                    Toasty.success(getApplicationContext(),R.string.them_thanh_cong, Toasty.LENGTH_LONG).show();
                    setResult(RESULT_OK, new Intent());
                    finish();
                } catch (SQLiteBlobTooBigException exx) {
                    Toasty.error(getApplicationContext(), "Dung lượng ảnh của bạn quá lớn!", Toasty.LENGTH_LONG).show();
                }
            } else {
                builder = new AlertDialog.Builder(AddBookActivity.this, R.style.AlertDialog);
                builder.setTitle(R.string.ma_sach_ton_tai);
                builder.setIcon(R.drawable.logo);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.kiem_tra, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton(R.string.huy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }

    private void initString() {
        loai = tenTheLoai.getSelectedItem().toString();
        ten = edTenSach.getText().toString();
        gia = edGiaBanSach.getText().toString();
        nxb = edNXB.getText().toString();
        tacgia = edTacGiaSach.getText().toString();
        masach = edMaSach.getText().toString();
        soluong = edSoLuongSach.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA &&
        resultCode == RESULT_OK && data != null) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imgAddBook.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER &&
                resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgAddBook.setImageBitmap(bitmap);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUEST_CODE_CAMERA:
                if(grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAMERA);
                } else {
                    Toasty.error(getApplicationContext(), R.string.cap_quyen_app, Toasty.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_FOLDER:
                if(grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CODE_FOLDER);
                } else {
                    Toasty.error(getApplicationContext(), R.string.cap_quyen_app, Toasty.LENGTH_LONG).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initView() {
        rv_sach = findViewById(R.id.rv_book);
        edMaSach = findViewById(R.id.edMaSach);
        edTenSach = findViewById(R.id.edTenSach);
        edTacGiaSach = findViewById(R.id.edTacGiaSach);
        edNXB = findViewById(R.id.edNXB);
        edGiaBanSach= findViewById(R.id.edGiaBanSach);
        edSoLuongSach = findViewById(R.id.edSoLuongSach);
        tenTheLoai = findViewById(R.id.idTheLoaiSach);
        toolbar = findViewById(R.id.toolbar);
        addImageCamera = findViewById(R.id.addImageCamera);
        imgAddBook = findViewById(R.id.imgAddBook);
        addImageFolder = findViewById(R.id.addImageFolder);
        btnAddSach = findViewById(R.id.btnAddSach);
    }

    private void getSpinner() {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sachDAO.getNameBookByID());
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        tenTheLoai.setAdapter(arrayAdapter);
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
        overridePendingTransition(R.anim.animate_slide_in_left, R.anim.animate_slide_out_right);
        super.onBackPressed();
    }

    public void addImageFolder(View view) {
        ActivityCompat.requestPermissions(AddBookActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_FOLDER);
    }

    public void addImageCamera(View view) {
        ActivityCompat.requestPermissions(AddBookActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
    }


}
