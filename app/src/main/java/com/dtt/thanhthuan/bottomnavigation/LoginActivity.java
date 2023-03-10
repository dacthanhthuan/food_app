package com.dtt.thanhthuan.bottomnavigation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dtt.thanhthuan.bottomnavigation.ui.FragmentD;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    public String email, password;
    private Button btnLogin, btn_Chuyentrang;
    private String URL = "http://192.168.43.230/sqlfood/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = password = "";
        edtEmail = findViewById(R.id.edtL_Email);
        edtPassword = findViewById(R.id.edtL_Password);
        btnLogin = findViewById(R.id.btnLogin);
        btn_Chuyentrang = findViewById(R.id.btn_Chuyentrang);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                if (!email.equals("") && !password.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Success")) {
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, BottomNavigation.class);
                                intent.putExtra("email", email);
                                intent.putExtra("password", password);
                                startActivity(intent);
                                finish();
                            } else if (response.equals("Failure")) {
                                Toast.makeText(LoginActivity.this, "Sai Email hoặc Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("Email", email);
                            data.put("Password", password);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(LoginActivity.this, "Các trường không thể trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Chuyentrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



}