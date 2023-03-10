package com.dtt.thanhthuan.bottomnavigation.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtt.thanhthuan.bottomnavigation.R;


public class FragmentD extends Fragment {
    TextView profile_Email, profile_Password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);
        profile_Email = view.findViewById(R.id.profile_Email);
        profile_Password = view.findViewById(R.id.profile_Password);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    public void updateData(String email, String password) {
        profile_Email.setText(email);
        profile_Password.setText(password);
    }
}