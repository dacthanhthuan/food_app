package com.dtt.thanhthuan.bottomnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dtt.thanhthuan.bottomnavigation.model.SANPHAM;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DetailProductActivity extends AppCompatActivity {
    ImageView detail_img;
    TextView detail_name, detail_price, detail_description;
    Button detail_addtocart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        // ánh xạ
        detail_img = findViewById(R.id.detail_img);
        detail_name = findViewById(R.id.detail_name);
        detail_price = findViewById(R.id.detail_price);
        detail_description = findViewById(R.id.detail_description);
        detail_addtocart = findViewById(R.id.detail_addtocart);

        // Định dạng tiền VNĐ
        NumberFormat formatPrice = new DecimalFormat("#,###");

        // nhận dữ liệu
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String priceString = intent.getStringExtra("price");
        double price = Double.parseDouble(priceString);
        String img = intent.getStringExtra("image");
        String description = intent.getStringExtra("mota");

        detail_name.setText(name);
        detail_description.setText(description);
        detail_price.setText(formatPrice.format(price) + " Đ");
        Glide.with(this).load(SERVER.imgsanpham + img).into(detail_img);

        detail_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SANPHAM sp = new SANPHAM(name, img, Integer.parseInt(priceString));
                Intent intent1 = new Intent(DetailProductActivity.this, CartActivity.class);
                intent1.putExtra("id", sp.getMasanpham());
                intent1.putExtra("name", sp.getTensanpham());
                intent1.putExtra("image", sp.getHinhsanpham());
                intent1.putExtra("price", sp.getGiasanpham() + "");
                intent1.putExtra("quantity", sp.getSoluong() + "");
                startActivity(intent1);
            }
        });
    }

}
