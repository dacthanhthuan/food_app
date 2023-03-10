package com.dtt.thanhthuan.bottomnavigation.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dtt.thanhthuan.bottomnavigation.LoginActivity;
import com.dtt.thanhthuan.bottomnavigation.R;
import com.dtt.thanhthuan.bottomnavigation.RegisterActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class FragmentB extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText add_tenSp, add_giaSp, add_hinhSp, add_motaSp;
    Button btn_addProduct;
    Spinner add_spinner;

    private String URL = "http://192.168.7.152/sqlfood/insertProduct.php";

    private String tensp, giasp, hinhsp, chude, mota;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        // ánh xạ
        add_tenSp =  view.findViewById(R.id.add_tenSp);
        add_giaSp =  view.findViewById(R.id.add_giaSp);
        add_hinhSp =  view.findViewById(R.id.add_hinhSp);
        add_motaSp =  view.findViewById(R.id.add_motaSp);
        btn_addProduct =  view.findViewById(R.id.btn_addProduct);
        add_spinner = view.findViewById(R.id.add_spinner);
        tensp = giasp = hinhsp = chude = mota = "";

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.chude, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        add_spinner.setAdapter(adapter);
        add_spinner.setOnItemSelectedListener(this);

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tensp = add_tenSp.getText().toString().trim();
                giasp = add_giaSp.getText().toString().trim();
                hinhsp = add_hinhSp.getText().toString().trim();
                chude = add_spinner.getSelectedItem().toString().trim();
                mota = add_motaSp.getText().toString().trim();

                if (!tensp.equals("") && !giasp.equals("") && !hinhsp.equals("") && !chude.equals("") && !mota.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Success")) {
                                Toast.makeText(getActivity(), "Add Product Successs", Toast.LENGTH_SHORT).show();

                            } else if (response.equals("Failure")) {
                                Toast.makeText(getActivity(), "Add Product Failure", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("TenSanPham", tensp);
                            data.put("GiaSanPham", giasp);
                            data.put("HinhSanPham", hinhsp);
                            data.put("MaChuDe", chude);
                            data.put("mota", mota);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
                }

            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}