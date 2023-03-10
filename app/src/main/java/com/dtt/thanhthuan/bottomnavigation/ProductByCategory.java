package com.dtt.thanhthuan.bottomnavigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dtt.thanhthuan.bottomnavigation.ADAPTER.ADAPTER_SANPHAM;
import com.dtt.thanhthuan.bottomnavigation.model.SANPHAM;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Intent intent = new Intent(ProductByCategory.this, DetailProductActivity.class);
        intent.putExtra("name", sp.getTensanpham());
        intent.putExtra("mota", sp.getMota());
        intent.putExtra("price", sp.getGiasanpham() + "");
        intent.putExtra("image", sp.getHinhsanpham());
        startActivity(intent);

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
