package com.poly.bookmanager.Fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.poly.bookmanager.R;
import com.poly.bookmanager.model.HoaDon;
import com.poly.bookmanager.model.HoaDonChiTiet;
import com.poly.bookmanager.model.Sach;
import com.poly.bookmanager.model.User;
import com.poly.bookmanager.projectDAO.HoaDonChiTietDAO;
import com.poly.bookmanager.projectDAO.HoaDonDAO;
import com.poly.bookmanager.projectDAO.SachDAO;
import com.poly.bookmanager.projectDAO.UserDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThongKeFragment extends Fragment {
    List<HoaDon> hoaDonList;
    List<Sach> sachList;
    List<User> userList;
    List<HoaDonChiTiet> hoaDonChiTietList;
    List<HoaDonChiTiet> KCsumTheoNam = new ArrayList<>();
    List<HoaDonChiTiet> KCsumTheoThang = new ArrayList<>();
    List<String> TTDays = new ArrayList<>();
    private int sumkttheothang = 0,sumkttheonam = 0;
    HoaDonChiTietDAO chiTietDAO;
    HoaDonDAO hoaDonDAO;
    UserDAO userDAO;
    SachDAO sachDAO;
    TextView tvSumHD, tvSumYears, tvSumDay, tvSumMonth, tvSumUser, tvBookALl, tvBookBuy;
    private String sqlSumTheoNam = "SELECT * FROM HoaDonChiTiet";
    private String sqlSumTheoThang = "SELECT * FROM HoaDonChiTiet WHERE strftime('%m',dateNow) = strftime('%m',date('now')) " +
            "AND strftime('%Y',dateNow) = strftime('%Y',date('now'))";
    public ThongKeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        setSum();
        KCsumTheoNam = chiTietDAO.sumMoneyHDCT(sqlSumTheoNam);
        KCsumTheoThang = chiTietDAO.sumMoneyHDCT(sqlSumTheoThang);

        TTDays = chiTietDAO.sumMoneyHDCTTT(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        initView(view);
        for (HoaDonChiTiet hoaDonChiTiet : KCsumTheoNam) {
            sumkttheonam+=Integer.parseInt(hoaDonChiTiet.getPriceNow());
        }
        for (HoaDonChiTiet hoaDonChiTiet : KCsumTheoThang) {
            sumkttheothang+=Integer.parseInt(hoaDonChiTiet.getPriceNow());

        }
        setTextView();
        return view;
    }

    private void setTextView() {
        tvBookBuy.setText(hoaDonChiTietList.size()+" Cuốn");
        tvBookALl.setText(sachList.size()+ "Cuốn");
        tvSumHD.setText(hoaDonList.size()+" Hoá Đơn");
        tvSumUser.setText(userList.size()+" User");
        tvSumYears.setText(sumkttheonam+" VND");
        tvSumMonth.setText(sumkttheothang+" VND");
        if(TTDays.get(0) == null) {
            tvSumDay.setText(0+" VND");
        } else {
            tvSumDay.setText(TTDays.get(0) + " VND");
        }
    }

    private void setSum() {
        hoaDonDAO = new HoaDonDAO(getContext());
        chiTietDAO = new HoaDonChiTietDAO(getContext());
        sachDAO = new SachDAO(getContext());
        userDAO = new UserDAO(getContext());
        hoaDonList = hoaDonDAO.getAllHoaDon();
        sachList = sachDAO.getAllSach();
        userList = userDAO.getAllUser();
        hoaDonChiTietList = chiTietDAO.getAllHoaDon();
    }

    private void initView(View view) {
        tvSumHD = view.findViewById(R.id.tvSumHD);
        tvBookALl = view.findViewById(R.id.tvSumBooksAll);
        tvBookBuy = view.findViewById(R.id.tvSumBooks);
        tvSumUser = view.findViewById(R.id.tvSumAccount);
        tvSumDay = view.findViewById(R.id.tvSumDay);
        tvSumMonth = view.findViewById(R.id.tvSumMonth);
        tvSumYears = view.findViewById(R.id.tvSumYears);
    }
}


