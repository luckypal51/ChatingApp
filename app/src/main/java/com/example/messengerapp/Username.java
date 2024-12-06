package com.example.messengerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.messengerapp.Utils.FirebaseUtil;
import com.example.messengerapp.model.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class Username extends AppCompatActivity {
EditText username;
Button save;
ProgressBar progressBar;
String phone;
    UserDetails userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_username);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username = findViewById(R.id.username);
        save = findViewById(R.id.username_button);
        progressBar = findViewById(R.id.username_progressbar);
        phone = getIntent().getStringExtra("phone");
        getusername();
        save.setOnClickListener(v -> {
            setUsername();
        });
    }
     void setUsername(){
       String Username = username.getText().toString();
       if (Username.isEmpty()||Username.length()<3){
        username.setError("Username atleast 4 character");
        return;
       }
       setInprogress(true);
       if (userDetails!=null){
           userDetails.setUsername(Username);
       }
       else {
           userDetails = new UserDetails(Username,phone, Timestamp.now(),FirebaseUtil.curentuserId());

       }
       FirebaseUtil.currentuserDatail().set(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               setInprogress(false);
               if(task.isSuccessful()){
                Intent i = new Intent(Username.this, MainActivity.class);
                // claering the login activiities the opening the main activity
                   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(i);
               }
           }
       });
     }

    private void getusername() {
        setInprogress(true);
        FirebaseUtil.currentuserDatail().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInprogress(false);
                if (task.isSuccessful()){
                    userDetails=  task.getResult().toObject(UserDetails.class);
                    if (userDetails!=null){
                        username.setText(userDetails.getUsername());
                    }
                }
            }
        });
    }

    void setInprogress(boolean inprogress){
        if (inprogress){
            progressBar.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
        }
    }
}