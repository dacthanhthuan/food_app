package com.dtt.thanhthuan.bottomnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.dtt.thanhthuan.bottomnavigation.ui.FragmentA;
import com.dtt.thanhthuan.bottomnavigation.ui.FragmentB;
import com.dtt.thanhthuan.bottomnavigation.ui.FragmentD;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigation extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        // ánh xạ
        bottomNavigationView = findViewById(R.id.bottomnavigation);

        OpenFragment(new FragmentA());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.mnuHome:
                        fragment = new FragmentA();
                        break;
                    case R.id.mnuDashboard:
                        fragment = new FragmentB();
                        break;
                    case R.id.mnuNotifications:
                        fragment = new FragmentD();
                        break;
                    case R.id.mnuSuppervíor:
                        fragment = new FragmentD();
                        break;
                }
                OpenFragment(fragment);
                return true;
            }
        });
    }

    void OpenFragment(Fragment f) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.bottom_nav_framelayout, f);
        transaction.commit();
    }
}