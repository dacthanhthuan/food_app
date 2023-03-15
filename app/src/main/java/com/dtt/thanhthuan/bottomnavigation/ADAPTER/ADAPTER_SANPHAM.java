package com.dtt.thanhthuan.bottomnavigation.ADAPTER;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtt.thanhthuan.bottomnavigation.R;
import com.dtt.thanhthuan.bottomnavigation.SERVER;
import com.dtt.thanhthuan.bottomnavigation.model.SANPHAM;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ADAPTER_SANPHAM extends RecyclerView.Adapter<ADAPTER_SANPHAM.ADAPTERSANPHAMVIEWHOLDER> implements Filterable {
    Context context;
    ArrayList<SANPHAM> data;
    ArrayList<SANPHAM> dataOrigin; // tạm
    private isClickListener iClickListener;


    public interface isClickListener {
        void onClikShowItem(SANPHAM sp);

    }

    public ADAPTER_SANPHAM(Context context, ArrayList<SANPHAM> data, isClickListener iClickListener) {
        this.context = context;
        this.data = data;
        this.dataOrigin = data;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public ADAPTERSANPHAMVIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false);
        return new ADAPTERSANPHAMVIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADAPTERSANPHAMVIEWHOLDER holder, int position) {
        SANPHAM sp = data.get(position);
        NumberFormat formatPrice = new DecimalFormat("#,###");
        // gắn dữ liệu cho TÊN và GIÁ
        holder.tvtenSp.setText(sp.tensanpham);
        holder.tvgiaSp.setText(formatPrice.format(sp.getGiasanpham()) + " Đ");
        // Gắn dữ liệu hình SẢN PHẨM
        Picasso.get().load(SERVER.imgsanpham + sp.hinhsanpham).into(holder.imghinhSp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onClikShowItem(sp);
            }
        });
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_recycleview));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //Serch
    @Override
    public Filter getFilter() {
        return new ItemFilter();
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String chuoitim = charSequence.toString().toLowerCase().trim();
            FilterResults filterResults = new FilterResults();
            if (!TextUtils.isEmpty(chuoitim)) {
                ArrayList<SANPHAM> tam = new ArrayList<>();
                for (SANPHAM sp : dataOrigin) {
                    if (sp.tensanpham.toLowerCase().toString().contains(chuoitim))
                        tam.add(sp);
                }
                filterResults.values = tam;
                filterResults.count = tam.size();

            } else {
                filterResults.values = dataOrigin;
                filterResults.count = dataOrigin.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // Nếu nhập vào != null và giá trị lớn hơn không thì thực hiện
            if (filterResults != null && filterResults.count > 0) {
                data = (ArrayList<SANPHAM>) filterResults.values;
                notifyDataSetChanged();
            }
        }
    }

    public class ADAPTERSANPHAMVIEWHOLDER extends RecyclerView.ViewHolder {
        ImageView imghinhSp;
        TextView tvtenSp, tvgiaSp;

        public ADAPTERSANPHAMVIEWHOLDER(@NonNull View itemView) {
            super(itemView);
            imghinhSp = itemView.findViewById(R.id.imghinhSp);
            tvtenSp = itemView.findViewById(R.id.tvtenSp);
            tvgiaSp = itemView.findViewById(R.id.tvgiaSp);
        }
    }
}