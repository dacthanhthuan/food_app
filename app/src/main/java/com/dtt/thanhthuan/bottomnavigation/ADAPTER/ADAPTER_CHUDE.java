package com.dtt.thanhthuan.bottomnavigation.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtt.thanhthuan.bottomnavigation.ProductByCategory;
import com.dtt.thanhthuan.bottomnavigation.R;
import com.dtt.thanhthuan.bottomnavigation.SERVER;
import com.dtt.thanhthuan.bottomnavigation.model.CHUDE;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ADAPTER_CHUDE extends RecyclerView.Adapter<ADAPTER_CHUDE.ADAPTERCHUDEVIEWHOLDER> {
    ArrayList<CHUDE> data;
    Context context;

    public ADAPTER_CHUDE(ArrayList<CHUDE> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ADAPTERCHUDEVIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chude, parent, false);
        return new ADAPTERCHUDEVIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADAPTERCHUDEVIEWHOLDER holder, int position) {
        CHUDE cd = data.get(position);
        // chua gan du lieu cho HINH
        holder.tvChude.setText(cd.getTenChude());
        // Gắn dữ liệu hình chủ đề
        Picasso.get().load(SERVER.imgpath + cd.hinhChude).into(holder.imgChude);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ProductByCategory.class);
                        intent.putExtra("machude", cd.maChude);
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ADAPTERCHUDEVIEWHOLDER extends RecyclerView.ViewHolder {
        TextView tvChude;
        ImageView imgChude;
        public ADAPTERCHUDEVIEWHOLDER(@NonNull View itemView) {
            super(itemView);
            tvChude = itemView.findViewById(R.id.tvChude);
            imgChude = itemView.findViewById(R.id.imgChude);
        }
    }
}
