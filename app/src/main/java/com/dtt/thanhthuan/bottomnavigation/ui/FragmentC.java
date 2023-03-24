package com.dtt.thanhthuan.bottomnavigation.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtt.thanhthuan.bottomnavigation.ADAPTER.ADAPTER_THONGBAO;
import com.dtt.thanhthuan.bottomnavigation.R;
import com.dtt.thanhthuan.bottomnavigation.model.THONGBAO;

import java.util.ArrayList;

public class FragmentC extends Fragment {

    RecyclerView rvthongbao;
    ArrayList<THONGBAO> data = new ArrayList<>();
    ADAPTER_THONGBAO adapter_thongbao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);

        rvthongbao = view.findViewById(R.id.rvthongbao);

        data.add(new THONGBAO(R.drawable.notification, "Đây là thông báo khuyến mãi"));
        data.add(new THONGBAO(R.drawable.notification, "Đây là thông báo khuyến mãi"));
        data.add(new THONGBAO(R.drawable.notification, "Đây là thông báo khuyến mãi"));
        data.add(new THONGBAO(R.drawable.notification, "Đây là thông báo khuyến mãi"));
        data.add(new THONGBAO(R.drawable.notification, "Đây là thông báo khuyến mãi"));

        adapter_thongbao = new ADAPTER_THONGBAO(getContext(), data);
        rvthongbao.setAdapter(adapter_thongbao);
        rvthongbao.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        return view;
    }
}