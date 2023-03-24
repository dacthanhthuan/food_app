package com.dtt.thanhthuan.bottomnavigation;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dtt.thanhthuan.bottomnavigation.ADAPTER.ADAPTER_CART;
import com.dtt.thanhthuan.bottomnavigation.model.SANPHAM;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public TextView cart_total;
    private Button checkout;
    private RecyclerView rvCart;
    private ADAPTER_CART adapter_cart;
    private Context context;
    private ArrayList<SANPHAM> dataSP;

    SQLiteDatabase db;
    DBHelper dbHelper;

    private String iD, name, image, price;
    private int quantity; // new variable to hold quantity value

    // Định dạng tiền VNĐ
    NumberFormat formatPrice = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cart_total = findViewById(R.id.cart_total);
        checkout = findViewById(R.id.checkout);
        rvCart = findViewById(R.id.rvCart);

        context = this;

        // nhận dữ liệu
        Intent intent = getIntent();
        iD = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        image = intent.getStringExtra("image");
        price = intent.getStringExtra("price");
        quantity = intent.getIntExtra("quantity", 1);

        dataSP = new ArrayList<>();
        dbHelper = new DBHelper(this);
        dbHelper.updateProductQuantityById(iD, quantity);
        dataSP = dbHelper.layALLSP();

        adapter_cart = new ADAPTER_CART(this, dataSP);
        rvCart.setAdapter(adapter_cart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // SET TỔNG TIỀN SẢN PHẨM
        double totalPrice = 0;
        for (SANPHAM sp : dataSP) {
            totalPrice += sp.getGiasanpham() * sp.getSoluong();
        }
        cart_total.setText(formatPrice.format(totalPrice) + " Đ");

        // chuyển sang màn hình CHECKOUT
        double finalTotalPrice = totalPrice;
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CheckOutActivity.class);
                intent.putExtra("tt", finalTotalPrice);
                startActivity(intent);

            }
        });
    }

    //  Phương thức cập nhật lại số tiền sau khi XÓA SẢN PHẨM, TĂNG OR GIẢM SỐ LƯỢNG
    public void updateTotal(double total) {
        cart_total.setText(formatPrice.format(total) + " Đ");
    }


}