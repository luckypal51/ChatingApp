package com.example.messengerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.messengerapp.Utils.AndroidUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_OTP extends AppCompatActivity {

String phone;
long timeout =60L;
String verificationcode;
PhoneAuthProvider.ForceResendingToken resendingToken;
EditText Otp;
TextView resend;
Button genrateOtp;
ProgressBar progressBar;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_otp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        Otp = findViewById(R.id.OTP_editext);
        genrateOtp = findViewById(R.id.enter_otp_btn);
        progressBar = findViewById(R.id.progressbar_loginotp);
        resend = findViewById(R.id.resend_otp);
        phone = getIntent().getExtras().getString("phonenumber");
        Log.d("TAG", "onCreate: "+phone);
        sendOtp(phone,false);
        genrateOtp.setOnClickListener(v -> {
            String otp = Otp.getText().toString();
          PhoneAuthCredential credential=  PhoneAuthProvider.getCredential(verificationcode,otp);
          signIn(credential);
          setInprogress(true);
        });
        resend.setOnClickListener(v -> {
              sendOtp(phone,true);
        });
    }
    void sendOtp(String phone, boolean isresend){
        // Force reCAPTCHA flow
//        FirebaseAuth.getInstance().getFirebaseAuthSettings().forceRecaptchaFlowForTesting(isresend);

        startResendotp();
        setInprogress(true);
        PhoneAuthOptions.Builder builder=PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phone)
                .setTimeout(timeout, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signIn(phoneAuthCredential);
                        setInprogress(false);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        AndroidUtils.showToast(getApplicationContext(),"OTP verification Failed");
                        setInprogress(false);
                        Log.d("TAG", "onVerificationFailed: "+e.getMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationcode =s;
                        resendingToken = forceResendingToken;
                        AndroidUtils.showToast(getApplicationContext(),"OTP sent successfully");
                        setInprogress(false);
                    }
        });
        if (isresend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }
        else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }
    void setInprogress(boolean inprogress){
        if (inprogress){
            progressBar.setVisibility(View.VISIBLE);
            genrateOtp.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            genrateOtp.setVisibility(View.VISIBLE);
        }
    }
    void signIn(PhoneAuthCredential phoneAuthCredential){
       setInprogress(true);
       mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   Intent i = new Intent(Login_OTP.this, Username.class);
                   i.putExtra("phone",phone);
                   startActivity(i);
               }
               else{
                   Toast.makeText(Login_OTP.this, "Login failed ! ", Toast.LENGTH_SHORT).show();
               }
           }
       });
    }
    void startResendotp(){
        resend.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
             timeout --;
             resend.setText("Resend Otp in "+timeout+" seconds");
             if(timeout<=0){
                 timeout =60L;
                 timer.cancel();
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         resend.setEnabled(true);
                     }
                 });
             }
            }
        },0,1000);
    }
    boolean isValidPhoneNumber(String phoneNumber) {
        // Implement your phone number validation logic here
        // For example, using a regular expression:
        Pattern pattern = Pattern.compile("^\\+[0-9]{1,14}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}