package com.dtt.thanhthuan.bottomnavigation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {
    private TextView Result_Email, Result_Total;
    private EditText edtCart_Address, edtCart_danhgia;
    private Button btn_xacnhan;

    private String URL = "http://192.168.100.3/sqlfood/donhang.php";

    private String danhgia, diachi;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        // ÁNH XẠ
        Result_Email = findViewById(R.id.tvResult_Email);
        Result_Total = findViewById(R.id.tvResult_Total);
        edtCart_Address = findViewById(R.id.edtCart_Address);
        edtCart_danhgia = findViewById(R.id.edtCart_danhgia);
        btn_xacnhan = findViewById(R.id.btn_xacnhan);

        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        // Lấy dữ liệu tổng tiền
        String price = intent.getStringExtra("tt");

        // chưa lấy được Email từ BottomNavigation
        Result_Email.setText("dtt2003@gmail.com");
        // get Tổng tiền
        Result_Total.setText(price);

        danhgia = diachi = "";

        // Đẩy dữ liệu lên đơn hàng sau khi đặt hàng
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danhgia = edtCart_danhgia.getText().toString().trim();
                diachi = edtCart_Address.getText().toString().trim();

                if (!danhgia.equals("") && !diachi.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Success")) {
                                Toast.makeText(CheckOutActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                                // Xóa tất cả sản phẩm sau khi mua hàng trong giỏ hàng
                                dbHelper.deleteAllProducts();

                                // Intent sang trang chủ
                                Intent intent = new Intent(CheckOutActivity.this, BottomNavigation.class);
                                startActivity(intent);

                            } else if (response.equals("Failure")) {
                                Toast.makeText(CheckOutActivity.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
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
                            data.put("Email", Result_Email.getText().toString());
                            data.put("ThanhTien", Result_Total.getText().toString());
                            data.put("DanhGia", danhgia);
                            data.put("TrangThai", diachi);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(CheckOutActivity.this, "Các trường không thể trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}