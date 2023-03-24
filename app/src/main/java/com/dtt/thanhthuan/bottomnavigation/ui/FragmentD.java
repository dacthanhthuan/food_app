package com.dtt.thanhthuan.bottomnavigation.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dtt.thanhthuan.bottomnavigation.LoginActivity;
import com.dtt.thanhthuan.bottomnavigation.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;


public class FragmentD extends Fragment {
    TextView profile_Email, profile_Password, profile_fullname, profile_Diachi;
    Button btn_Logout;
    ImageView profile_img;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);

        // ÁNH XẠ
        profile_Email = view.findViewById(R.id.profile_Email);
        profile_Diachi = view.findViewById(R.id.profile_Diachi);
        profile_img = view.findViewById(R.id.profile_img);
        profile_fullname = view.findViewById(R.id.profile_fullname);
        profile_Password = view.findViewById(R.id.profile_Password);
        btn_Logout = view.findViewById(R.id.btn_Logout);

        //Gmail
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);

        // lấy dữ liệu từ tài khoản GMAIL
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct!=null){
            // lấy tên và email của tài khoản GMAIL
            String name = acct.getDisplayName();
            String email = acct.getEmail();
            profile_fullname.setText(name);
            profile_Email.setText(email);

            // lấy hình của tài khoản GMAIL
            Uri photoUrl = acct.getPhotoUrl();
            if (photoUrl != null) {
                Glide.with(getActivity()).load(photoUrl).into(profile_img);
            }
        }

        // lấy dữ liệu tài khoản FACEBOOK
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    (object, response) -> {
                        try {
                            String name = object.getString("name");
                            String url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            // Load FB name
                            profile_fullname.setText(name);
                            // Load FB image
                           Picasso.get().load(url).into(profile_img);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();
        }


        // BUTTON ĐĂNG XUẤT
        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifications();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // phương thức đăng xuất và chuyển màn hình sang LoginActivity
    void signOut() {
        // Đăng xuất GMAIL
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        // Đăng xuất FACEBOOK
        LoginManager.getInstance().logOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));

    }

    // THÔNG BÁO ĐĂNG XUẤT BẰNG AlterDialog
    void notifications() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Bạn có chắc muốn đăng xuất ?");
        builder1.setCancelable(true);


        builder1.setPositiveButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        signOut();
                        Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}