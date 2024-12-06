package com.example.messengerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {
CountryCodePicker pickere;
Button GenOtp;
EditText phonenumber;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pickere = findViewById(R.id.CountryCode);
        GenOtp = findViewById(R.id.OTP);
        phonenumber = findViewById(R.id.Phonenumber);
        progressBar = findViewById(R.id.Login_phone_progressbar);
        pickere.registerCarrierNumberEditText(phonenumber);
        GenOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

               if(!pickere.isValidFullNumber()){
                   phonenumber.setError("Enter valid phone number");
                   return;
               }
               Intent intent = new Intent(LoginActivity.this, Login_OTP.class);
               intent.putExtra("phonenumber",pickere.getFullNumberWithPlus());
               startActivity(intent);
               finish();
            }
        });
    }
}