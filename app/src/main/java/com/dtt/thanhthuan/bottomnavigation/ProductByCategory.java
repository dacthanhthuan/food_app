package com.dtt.thanhthuan.bottomnavigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dtt.thanhthuan.bottomnavigation.ADAPTER.ADAPTER_SANPHAM;
import com.dtt.thanhthuan.bottomnavigation.model.SANPHAM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductByCategory extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;
    EditText ProductByCategori_edtSearch;
    ImageView btnBack, shop_cart_category;
    RecyclerView rvProductByCategori;
    ArrayList<SANPHAM> datasp;
    ADAPTER_SANPHAM adapter_sanpham;
    ImageView speakButton_category;

    Intent intent;
    String machude;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);
        // ánh xạ
        btnBack = findViewById(R.id.btnBack);
        speakButton_category = findViewById(R.id.speakButton_category);
        ProductByCategori_edtSearch = findViewById(R.id.ProductByCategori_edtSearch);
        rvProductByCategori = findViewById(R.id.rvProductByCategori);
        shop_cart_category = findViewById(R.id.shop_cart_category);

        dbHelper = new DBHelper(this);
        datasp = new ArrayList<>(); // Khởi tạo ArrayList Sản phẩm

        // nhận dữ liệu từ HOME
        intent = getIntent();
        machude = intent.getStringExtra("machude");

        // đẩy lên recycleview
        adapter_sanpham = new ADAPTER_SANPHAM(this, datasp, new ADAPTER_SANPHAM.isClickListener() {
            @Override
            public void onClikShowItem(SANPHAM sp) {
                onClickShowData(sp);
            }
        });
        rvProductByCategori.setAdapter(adapter_sanpham);
        rvProductByCategori.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        // Load sản phẩm trên trang sản phẩm theo chủ đề
        loadSP();

        // Search trên trang sản phẩm theo chủ đề
        ProductByCategori_edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // lấy dữ liệu ở đây
                String chuoitim = charSequence.toString();
                adapter_sanpham.getFilter().filter(chuoitim);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Trở về trang chủ
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductByCategory.this, BottomNavigation.class);
                startActivity(intent);
            }
        });

        //Search giọng nói
        speakButton_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognitionActivity();
            }
        });

        //
        shop_cart_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductByCategory.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    void loadSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsondArray) {
                for (int i = 0; i < jsondArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsondArray.getJSONObject(i);
                        String macd = jsonObject.getString("machude");

                        if (machude.equals(macd)) {
                            datasp.add(new SANPHAM(jsonObject.getString("masp"), macd,
                                    jsonObject.getString("tensp"),
                                    jsonObject.getString("hinhsp"),
                                    jsonObject.getInt("giasp"),
                                    jsonObject.getString("mota")
                            ));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(ProductByCategory.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                adapter_sanpham.notifyDataSetChanged();
            }
        };
        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductByCategory.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        };
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER.sanphamtheochudepath, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);

    }

    // Truyền dữ liệu qua chi tiết
    private void onClickShowData(SANPHAM sp) {
        //Code chuyển dữ liệu
        //Sử dụng Bundle + Intent để put
//        Intent intent = new Intent(ProductByCategory.this, DetailProductActivity.class);
//        intent.putExtra("name", sp.getTensanpham());
//        intent.putExtra("mota", sp.getMota());
//        intent.putExtra("price", sp.getGiasanpham() + "");
//        intent.putExtra("image", sp.getHinhsanpham());
//        startActivity(intent);

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_detail_product);
        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribue = window.getAttributes();
        windowAttribue.gravity = Gravity.TOP;
        window.setAttributes(windowAttribue);
        dialog.setCancelable(true);

        ImageView detail_img = dialog.findViewById(R.id.detail_img);
        TextView detail_name = dialog.findViewById(R.id.detail_name);
        TextView detail_price = dialog.findViewById(R.id.detail_price);
        TextView detail_description = dialog.findViewById(R.id.detail_description);
        Button detail_addtocart = dialog.findViewById(R.id.detail_addtocart);

        NumberFormat formatPrice = new DecimalFormat("#,###");
        // nhận dữ liệu

        detail_name.setText(sp.getTensanpham());
        detail_description.setText(sp.getMota());
        detail_price.setText(formatPrice.format(sp.getGiasanpham()) + " Đ");
        Glide.with(this).load(SERVER.imgsanpham + sp.getHinhsanpham()).into(detail_img);
        detail_img.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_chude));
        detail_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** ------- NOTE ---*/
//                SANPHAM sanpham = new SANPHAM(sp.getTensanpham(), sp.getHinhsanpham(), sp.getGiasanpham());
                Intent intent1 = new Intent(getApplicationContext(), CartActivity.class);
//                intent1.putExtra("id", sanpham.getMasanpham());
//                intent1.putExtra("name", sanpham.getTensanpham());
//                intent1.putExtra("image", sanpham.getHinhsanpham());
//                intent1.putExtra("price", sanpham.getGiasanpham() + "");
//                intent1.putExtra("quantity", sanpham.getSoluong() + "");
                //gọi hàm insert ở đây luôn
                dbHelper.insertProduct(sp.getMasanpham(), sp.getTensanpham(), sp.getHinhsanpham(), sp.getGiasanpham(), 1);

                startActivity(intent1);
            }
        });
        dialog.show();

    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && !matches.isEmpty()) {
                String text = matches.get(0);
                ProductByCategori_edtSearch.setText(text);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
