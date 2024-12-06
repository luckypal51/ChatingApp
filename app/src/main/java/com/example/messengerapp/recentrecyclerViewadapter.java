package com.example.messengerapp;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Utils.FirebaseUtil;
import com.example.messengerapp.model.UserDetails;
import com.example.messengerapp.model.Userchatroom;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.ArrayList;

public class recentrecyclerViewadapter extends RecyclerView.Adapter<recentrecyclerViewadapter.Viewholderrecent>{
    Context context;
    ArrayList<Userchatroom> userchatroomArrayList;

    public recentrecyclerViewadapter(Context context, ArrayList<Userchatroom> userchatroomArrayList) {
        this.context = context;
        this.userchatroomArrayList = userchatroomArrayList;
    }

    @NonNull
    @Override
    public Viewholderrecent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholderrecent holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Viewholderrecent extends RecyclerView.ViewHolder {

        public Viewholderrecent(@NonNull View itemView) {
            super(itemView);
        }
    }
}