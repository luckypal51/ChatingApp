package com.example.messengerapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.messengerapp.model.UserDetails;

public class AndroidUtils {
    public static  void showToast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static  void setUserAsIntent(Intent i, UserDetails model){
        i.putExtra("user_name",model.getUsername());
        i.putExtra("phone_number",model.getPhone());
        i.putExtra("userId",model.getUserid());
    }
    public static UserDetails setuserDetail(Intent i){
        UserDetails other = new UserDetails();
        other.setUsername(i.getStringExtra("user_name"));
        other.setPhone(i.getStringExtra("phone_number"));
        other.setUserid(i.getStringExtra("userId"));
        return other;
    }
}
