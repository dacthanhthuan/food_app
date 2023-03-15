package com.dtt.thanhthuan.bottomnavigation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtR_Email, edtR_Password, edtR_FullName, edtR_Address, edtR_Phone;
    private String URL = "http://192.168.100.8/sqlfood/register.php";
    private String email, password, fullName, address, phone;
    Button btnRegister;
    TextView tv_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtR_Email = findViewById(R.id.edtR_Email);
        edtR_Password = findViewById(R.id.edtR_Password);
        edtR_FullName = findViewById(R.id.edtR_FullName);
        edtR_Address = findViewById(R.id.edtR_Address);
        edtR_Phone = findViewById(R.id.edtR_Phone);
        btnRegister = findViewById(R.id.btnRegister);
        tv_status = findViewById(R.id.tv_status);
        email = password = fullName = address = phone = "";

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = edtR_FullName.getText().toString().trim();
                email = edtR_Email.getText().toString().trim();
                password = edtR_Password.getText().toString().trim();
                address = edtR_Address.getText().toString().trim();
                phone = edtR_Phone.getText().toString().trim();

                if (!email.equals("") && !password.equals("") && !fullName.equals("") && !address.equals("") && !phone.equals("")) {
                    if (isValidPassword(password)) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                                if (response.equals("Success")) {
                                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (response.equals("Failure")) {
                                    Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> data = new HashMap<>();
                                data.put("Email", email);
                                data.put("Password", password);
                                data.put("fullName", fullName);
                                data.put("DiaChi", address);
                                data.put("Phone", phone);
                                return data;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
                    } else {
                        tv_status.setText("Mật khẩu phải từ 4 kí tự có kí tuwh thường, hoa, số, và kí tự đặc biệt");
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Các trường không thể trống", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void login(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // định dạng password
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}