package com.dtt.thanhthuan.bottomnavigation.slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtt.thanhthuan.bottomnavigation.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SLIDE_ADAPTER extends RecyclerView.Adapter<SLIDE_ADAPTER.SLIDE_ADAPTER_VIEWHOLDER>{
    private List<SLIDE> hinhanh;
    private Context context;

    public SLIDE_ADAPTER(List<SLIDE> hinhanh, Context context) {
        this.hinhanh = hinhanh;
        this.context = context;
    }

    @NonNull
    @Override
    public SLIDE_ADAPTER_VIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item, parent, false);

        return new SLIDE_ADAPTER_VIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SLIDE_ADAPTER_VIEWHOLDER holder, int position) {
        SLIDE image = hinhanh.get(position);
        Picasso.get().load(image.getUrl()).into(holder.image_slide);
    }

    @Override
    public int getItemCount() {
        if (hinhanh != null) {
            return hinhanh.size();
        }
        return 0;
    }

    public class  SLIDE_ADAPTER_VIEWHOLDER extends RecyclerView.ViewHolder {
        private ImageView image_slide;
        public SLIDE_ADAPTER_VIEWHOLDER(@NonNull View itemView) {
            super(itemView);
            image_slide = itemView.findViewById(R.id.image_slide);
        }
    }
}

