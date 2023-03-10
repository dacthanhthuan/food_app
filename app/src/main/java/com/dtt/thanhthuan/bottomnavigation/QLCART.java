package com.dtt.thanhthuan.bottomnavigation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class QLCART extends SQLiteOpenHelper {
    public static String TenCSDL = "QLCART123";
    // Phiên bản
    public static int Version = 1;

    public QLCART(@Nullable Context context) {
        super(context, TenCSDL, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo bảng trong CSDL
        String sql = "CREATE TABLE CART (MaSP INTEGER PRIMARY KEY, TenSP TEXT, HinhSP TEXT, GiaSp INTEGER, SoLuong INTEGER);";
        sqLiteDatabase.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
