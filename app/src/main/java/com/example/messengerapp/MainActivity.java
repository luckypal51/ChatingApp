package com.example.messengerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView;
ImageButton search;
profile_fragment profileFragment;
Chats_Fragments chatsFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        profileFragment = new profile_fragment();
        chatsFragments = new Chats_Fragments();
        bottomNavigationView = findViewById(R.id.bottom_nav);
        search = findViewById(R.id.imageButton);
        //bottom sheet navigatuiopn creation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.chats){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,chatsFragments).commit();
                }
                if (item.getItemId()==R.id.profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,profileFragment).commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.chats);
          search.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(MainActivity.this, SearchActivity.class);
                  startActivity(i);
              }
          });
    }
}