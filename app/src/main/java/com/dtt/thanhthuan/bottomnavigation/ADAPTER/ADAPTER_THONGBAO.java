package com.dtt.thanhthuan.bottomnavigation.ADAPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtt.thanhthuan.bottomnavigation.R;
import com.dtt.thanhthuan.bottomnavigation.model.SANPHAM;
import com.dtt.thanhthuan.bottomnavigation.model.THONGBAO;

import java.util.ArrayList;

public class ADAPTER_THONGBAO extends RecyclerView.Adapter<ADAPTER_THONGBAO.THONGBAO_VIEWHOLDER>{

    Context context;
    ArrayList<THONGBAO> data;

    public ADAPTER_THONGBAO(Context context, ArrayList<THONGBAO> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public THONGBAO_VIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thongbao, parent, false);
        return new ADAPTER_THONGBAO.THONGBAO_VIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull THONGBAO_VIEWHOLDER holder, int position) {
        THONGBAO tb = data.get(position);

        holder.imgthongbao.setImageResource(tb.imghinhthongbao);
        holder.tvthongbao.setText(tb.thongbao);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class THONGBAO_VIEWHOLDER extends RecyclerView.ViewHolder {
        ImageView imgthongbao;
        TextView tvthongbao;

        public THONGBAO_VIEWHOLDER(@NonNull View itemView) {
            super(itemView);
            imgthongbao = itemView.findViewById(R.id.imghinhthongbao);
            tvthongbao = itemView.findViewById(R.id.tvthongbao);
        }
    }
}
