package com.dtt.thanhthuan.bottomnavigation.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dtt.thanhthuan.bottomnavigation.ADAPTER.ADAPTER_CHUDE;
import com.dtt.thanhthuan.bottomnavigation.ADAPTER.ADAPTER_SANPHAM;
import com.dtt.thanhthuan.bottomnavigation.CartActivity;
import com.dtt.thanhthuan.bottomnavigation.R;
import com.dtt.thanhthuan.bottomnavigation.SERVER;
import com.dtt.thanhthuan.bottomnavigation.model.CHUDE;
import com.dtt.thanhthuan.bottomnavigation.model.SANPHAM;
import com.dtt.thanhthuan.bottomnavigation.slider.SLIDE;
import com.dtt.thanhthuan.bottomnavigation.slider.SLIDE_ADAPTER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class FragmentA extends Fragment {

    private static final int REQUEST_CODE = 1234;

    ImageView shop_cart1;

    ADAPTER_CHUDE adapter_chude;
    ArrayList<CHUDE> datachude = new ArrayList<>();
    RecyclerView rvChude;
    private CircleIndicator3 indicator3;

    Context context;

    ADAPTER_SANPHAM adapter_sanpham;
    ArrayList<SANPHAM> datasanpham = new ArrayList<>();
    RecyclerView rvSanpham;
    EditText home_edtSearch;

    private ViewPager2 viewpager;
    private List<SLIDE> hinhanh;

    private View mView;

    private Handler handler = new Handler();
    // Luồng sẽ chạy hàm bên dưới
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Kiểm tera hình ảnh đang chạy phải hình cuối cùng không.
            if (viewpager.getCurrentItem() == hinhanh.size() - 1) {
                // Quay về lại hình đầu tiên.
                viewpager.setCurrentItem(0);
            } else {
                // Tiến lên 1 hình nữa
                viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
            }
        }
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView speakButton = view.findViewById(R.id.speakButton);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });

        shop_cart1 = view.findViewById(R.id.shop_cart1);

        rvChude = view.findViewById(R.id.rvChude);
        rvSanpham = view.findViewById(R.id.rvSanpham);
        home_edtSearch = view.findViewById(R.id.home_edtSearch);


        LoadChuDe();
        LoadSanPham();

        // chuyển màn hình đến giỏ hàng
        shop_cart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CartActivity.class);
                    startActivity(intent);
            }
        });

        // tìm kiếm
        home_edtSearch.addTextChangedListener(new TextWatcher() {
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
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        // ánh xạ
        viewpager = view.findViewById(R.id.slides_FHome);
        indicator3 = view.findViewById(R.id.circle_FHome);

        hinhanh = new ArrayList<>();
        hinhanh.add(new SLIDE(SERVER.slidepath + "slides1.jpeg", "Link"));
        hinhanh.add(new SLIDE(SERVER.slidepath + "slides2.jpeg", "Link"));
        hinhanh.add(new SLIDE(SERVER.slidepath + "slides3.jpeg", "Link"));
        hinhanh.add(new SLIDE(SERVER.slidepath + "slides4.jpeg", "Link"));
        hinhanh.add(new SLIDE(SERVER.slidepath + "slides5.jpeg", "Link"));
        hinhanh.add(new SLIDE(SERVER.slidepath + "slides6.jpeg", "Link"));


        // Phần khởi tạo
        SLIDE_ADAPTER slideAdapter = new SLIDE_ADAPTER(hinhanh, getContext());
        viewpager.setAdapter(slideAdapter);
        indicator3.setViewPager(viewpager);

        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

        return view;
    }

    void LoadChuDe() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsondArray) {
                for (int i = 0; i < jsondArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsondArray.getJSONObject(i);
                        datachude.add(new CHUDE(jsonObject.getString("macd"),
                                jsonObject.getString("tencd"),
                                jsonObject.getString("hinhcd")));
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                adapter_chude.notifyDataSetChanged();
            }
        };
        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER.chudepath, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);

        adapter_chude = new ADAPTER_CHUDE(datachude, getContext());
        rvChude.setAdapter(adapter_chude);
        rvChude.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    void LoadSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        datasanpham.add(new SANPHAM(jsonObject.getString("masp"),
                                jsonObject.getString("macd"),
                                jsonObject.getString("tensp"),
                                jsonObject.getString("hinhsp"),
                                jsonObject.getInt("giasp"),
                                jsonObject.getString("mota")));
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Lỗi ", Toast.LENGTH_LONG).show();
                    }
                }
                adapter_sanpham.notifyDataSetChanged();
            }
        };
        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_LONG).show();
            }
        };
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER.sanphampath, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);

        adapter_sanpham = new ADAPTER_SANPHAM(getContext(), datasanpham, new
                ADAPTER_SANPHAM.isClickListener() {
                    @Override
                    public void onClikShowItem(SANPHAM sp) {
                        onClickShowData(sp);
                    }
                });
        rvSanpham.setAdapter(adapter_sanpham);
        rvSanpham.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void onClickShowData(SANPHAM sp) {
        //Code chuyển dữ liệu
        //Sử dụng Bundle + Intent để put

        final Dialog dialog = new Dialog(getActivity());
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
        detail_img.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale));
        detail_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SANPHAM sanpham = new SANPHAM(sp.getTensanpham(), sp.getHinhsanpham(), sp.getGiasanpham());
                Intent intent1 = new Intent(getActivity(), CartActivity.class);
                intent1.putExtra("id", sanpham.getMasanpham());
                intent1.putExtra("name", sanpham.getTensanpham());
                intent1.putExtra("image", sanpham.getHinhsanpham());
                intent1.putExtra("price", sanpham.getGiasanpham() + "");
                intent1.putExtra("quantity", sanpham.getSoluong() + "");
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
                home_edtSearch.setText(text);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}