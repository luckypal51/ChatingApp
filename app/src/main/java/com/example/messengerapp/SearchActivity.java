package com.example.messengerapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Utils.FirebaseUtil;
import com.example.messengerapp.model.UserDetails;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class SearchActivity extends AppCompatActivity {
EditText searchinput;
ImageButton back,search;
RecyclerView recyclerView;
Adaptersearchview adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        searchinput = findViewById(R.id.search_bar_editext);
        back = findViewById(R.id.back);
        search = findViewById(R.id.search_btn);
        recyclerView = findViewById(R.id.Recyclerview_search);
        searchinput.requestFocus();//it will focuss on the editext and the keyboard automatically  open
        back.setOnClickListener(v -> onBackPressed());
        search.setOnClickListener(v -> {
            String searchterm = searchinput.getText().toString();
            if (searchterm.isEmpty()||searchterm.length()<3){
                Toast.makeText(this, "enter the username", Toast.LENGTH_SHORT).show();
                return;
            }
            setupSearchRecyclerView(searchterm);
        });
    }

    private void setupSearchRecyclerView(String searchterm) {
        Query query = FirebaseUtil.alluserCollectionRefference().whereGreaterThanOrEqualTo("username",searchterm);
        FirestoreRecyclerOptions<UserDetails> option = new FirestoreRecyclerOptions.Builder<UserDetails>().setQuery(query,UserDetails.class).build();
        adapter = new Adaptersearchview(option,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter!=null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.startListening();
        }
    }
}