package com.dtt.thanhthuan.bottomnavigation.fcm;

import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dtt.thanhthuan.bottomnavigation.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

public class MessageFirebase extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        //KIỂM TRA THÔNG BÁO CÓ GỬI CHO NHIỀU NGƯỜI KHÔNG
        if (message.getData().size() > 0){
            //LẤY DỮ LIỆU THÔNG BÁO ĐƯA VÀO DATAMESSAGE
            Map<String, String> DataMesage = message.getData();
            String titleNotifi = DataMesage.get("titleMessage");
            String contentNotifi = DataMesage.get("contentMessage");
            sendNotification(titleNotifi,contentNotifi);
        }else {
            //GỬI THÔNG BÁO CHO CÁ NHÂN
            RemoteMessage.Notification notification = message.getNotification();
            if (notification != null){
                String titleNotifi = notification.getTitle();
                String contentNotifi = notification.getBody();
                sendNotification(titleNotifi,contentNotifi);
            }
        }
    }

    private void sendNotification(String title, String content){
        //tạo một hình ảnh: Bitmap --> android nâng cao
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //TẠO MỘT ĐỐI TƯỢNG THÔNG BÁO
        Notification notification = new NotificationCompat.Builder(this, PushNotification.CHANNEL_ID)
                //SET TIÊU ĐỀ
                .setContentTitle(title)
                //SET NỘI DUNG
                .setContentText(content)
                //SET ICON NHỎ
                .setSmallIcon(R.drawable.notification)
                //SÉT HÌNH LỚN
                .setLargeIcon(bitmap)
                .setColor(getResources().getColor(R.color.purple_500, null))
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        //chuyền id: gõ số j vào cũng đc
        notificationManagerCompat.notify((int) new Date().getTime(),notification);
    }
}
