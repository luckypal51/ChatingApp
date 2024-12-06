package com.example.messengerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Utils.AndroidUtils;
import com.example.messengerapp.Utils.FirebaseUtil;
import com.example.messengerapp.model.UserDetails;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Adaptersearchview extends FirestoreRecyclerAdapter<UserDetails, Adaptersearchview.serachViewholder> {
    Context context;
    public Adaptersearchview(@NonNull FirestoreRecyclerOptions<UserDetails> options,Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public serachViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerviewforsearch,parent,false);

        return new serachViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull serachViewholder holder, int position, @NonNull UserDetails model) {
        holder.username.setText(model.getUsername());
        holder.phoneno.setText(model.getPhone());
        if (model.getUserid().equals(FirebaseUtil.curentuserId())){
            holder.username.setText(model.getUsername()+"(Me)");
        }
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ChatActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AndroidUtils.setUserAsIntent(i,model);
            context.startActivity(i);

        });
    }

    public class serachViewholder extends RecyclerView.ViewHolder {
         TextView username, phoneno;
         ImageView profile;
        public serachViewholder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_text);
            phoneno = itemView.findViewById(R.id.username_phoneno);
            profile = itemView.findViewById(R.id.profile_picture_view);
        }
    }
}
