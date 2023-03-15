package com.dtt.thanhthuan.bottomnavigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dtt.thanhthuan.bottomnavigation.model.SANPHAM;

import java.util.ArrayList;

public class DBHelper {
    SQLiteDatabase db;

    public DBHelper(Context context) {
        QLCART qlsvdbHelper = new QLCART(context);
        db = qlsvdbHelper.getWritableDatabase();
    }

    public ArrayList<SANPHAM> layALLSP() {
        ArrayList<SANPHAM> list = new ArrayList<>();
        Cursor cursor = db.query("CART", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            SANPHAM sp = new SANPHAM();
            sp.setMasanpham(cursor.getString(0));
            sp.setTensanpham(cursor.getString(1));
            sp.setHinhsanpham(cursor.getString(2));
            sp.setGiasanpham(cursor.getInt(3));
            sp.setSoluong(cursor.getInt(4)); // new line to get the quantity value
            list.add(sp);
        }
        cursor.close();
        return list;
    }

    // Thêm sản phẩm vào giỏ hàng
    // Thêm sản phẩm vào giỏ hàng
    public void insertProduct(String id, String name, String image, int price, int quantity) {
        Cursor cursor = db.query("CART", null, "MaSP=?", new String[]{id}, null, null, null);

        if (cursor.moveToFirst()) {
            // Nếu sản phẩm tồn tại trong giỏ hàng, cập nhật số lượng + 1
            int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("SoLuong"));
            int newQuantity = currentQuantity + quantity;

            ContentValues values = new ContentValues();
            values.put("SoLuong", newQuantity);

            db.update("CART", values, "MaSP=?", new String[]{id});

        } else {
            // Product does not exist in cart, insert new row
            ContentValues values = new ContentValues();
            values.put("MaSP", id);
            values.put("TenSP", name);
            values.put("HinhSP", image);
            values.put("GiaSp", price);
            values.put("SoLuong", quantity);

            db.insert("CART", null, values);
        }

        cursor.close();
    }



    // Xóa sản phẩm theo MaSP
    public void deleteProductById(String id) {
        db.delete("CART", "MaSP=?", new String[]{id});
        db.close();
    }

    public void updateProductQuantityById(String id, int quantity) {
        ContentValues values = new ContentValues();
        values.put("SoLuong", quantity);
        db.update("CART", values, "MaSP=?", new String[]{id});
    }
}
