package com.dtt.thanhthuan.bottomnavigation.fcm;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class PushNotification extends Application{

    public static final String CHANNEL_ID = "CHANNEL_1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChanel();
    }

    //tạo một kênh thông báo
    private void createNotificationChanel() {
        //kiểm tra phiên bản máy
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //TẠO KÊNH THÔNG BÁO
            //gõ tên j vô cũng đc
            CharSequence name = "Chanel_1";
            //thông tin của kênh
            String description = "Thông tin chanel 1";
            //đối tượng quan trọng của kênh thông báo
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //KÊNH THÔNG BÁO GỒM 4 THÔNG TIN: ID , NAME , MỨC DỘ QUAN TRỌNG THÔNG TIN CHI TIẾT CỦA KÊNH
            //tạo ra một kênh thông báo
            //khởi tạo một chanel
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            //sét thông tin mô tả của kênh
            channel.setDescription(description);

            //tạo quản lý kênh
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
