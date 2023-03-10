package com.dtt.thanhthuan.bottomnavigation.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtt.thanhthuan.bottomnavigation.CartActivity;
import com.dtt.thanhthuan.bottomnavigation.DBHelper;
import com.dtt.thanhthuan.bottomnavigation.R;
import com.dtt.thanhthuan.bottomnavigation.SERVER;
import com.dtt.thanhthuan.bottomnavigation.model.SANPHAM;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ADAPTER_CART extends RecyclerView.Adapter<ADAPTER_CART.ADAPTERCARTVIEWHOLDER> {
    Context context;
    ArrayList<SANPHAM> data;
    // Định dạng tiền VNĐ
    NumberFormat formatPrice = new DecimalFormat("#,###");

    public ADAPTER_CART(Context context, ArrayList<SANPHAM> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ADAPTERCARTVIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
        return new ADAPTERCARTVIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADAPTERCARTVIEWHOLDER holder, int position) {
        SANPHAM sp = data.get(position);
        // gắn dữ liệu cho TÊN và GIÁ
        holder.cart_name.setText(sp.tensanpham);
        holder.cart_price.setText(formatPrice.format(sp.giasanpham) + " Đ");
        holder.cart_quantity.setText(sp.getSoluong() + "");
        // Gắn dữ liệu HÌNH SẢN PHẨM
        Picasso.get().load(SERVER.imgsanpham + sp.hinhsanpham).into(holder.cart_img);
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale));

        // Xóa sản phẩm
        holder.cart_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the position of the item to be removed
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.deleteProductById(sp.getMasanpham());
                data.remove(sp);
                notifyDataSetChanged();
                updateTotalDelete();
            }
        });

        holder.cart_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setSoluong(sp.getSoluong() + 1);
                holder.cart_quantity.setText(String.valueOf(sp.getSoluong()));
                updateTotalQuantity();
            }
        });

        holder.cart_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getSoluong() > 1) {
                    sp.setSoluong(sp.getSoluong() - 1);
                    holder.cart_quantity.setText(String.valueOf(sp.getSoluong()));
                    updateTotalQuantity();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void updateTotalDelete() {
        double total = 0;
        for (SANPHAM sp : data) {
            total += sp.getGiasanpham() * sp.getSoluong();
        }
        ((CartActivity) context).updateTotal(total);
    }

    private void updateTotalQuantity() {
        double total = 0;
        for (SANPHAM sp : data) {
            total += sp.getGiasanpham() * sp.getSoluong();
        }
        ((CartActivity) context).updateTotal(total);
    }

    public class ADAPTERCARTVIEWHOLDER extends RecyclerView.ViewHolder {
        // Khai báo
        ImageView cart_img;
        TextView cart_name, cart_price;
        int numberOrder = 1;
        Button cart_delete;
        TextView cart_quantity, cart_min, cart_max;


        public ADAPTERCARTVIEWHOLDER(@NonNull View itemView) {
            super(itemView);
            // ánh xạ
            cart_img = itemView.findViewById(R.id.cart_img);
            cart_name = itemView.findViewById(R.id.cart_name);
            cart_price = itemView.findViewById(R.id.cart_price);
            cart_min = itemView.findViewById(R.id.cart_min);
            cart_quantity = itemView.findViewById(R.id.cart_quantity);
            cart_max = itemView.findViewById(R.id.cart_max);
            cart_delete = itemView.findViewById(R.id.cart_delete);


        }
    }
}
