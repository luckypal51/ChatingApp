package com.example.messengerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Utils.FirebaseUtil;
import com.example.messengerapp.model.UserMessageModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class chatRecyclerviewadapter extends FirestoreRecyclerAdapter<UserMessageModel, chatRecyclerviewadapter.chat_Viewholder> {
    Context context;
    public chatRecyclerviewadapter(@NonNull FirestoreRecyclerOptions<UserMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public chatRecyclerviewadapter.chat_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recyclerview_row,parent,false);

        return new chat_Viewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull chat_Viewholder holder, int position, @NonNull UserMessageModel model) {
   if (model.getSenderId().equals(FirebaseUtil.curentuserId())){
       holder.left_chat_view.setVisibility(View.GONE);
       holder.right_chat_view.setVisibility(View.VISIBLE);
       holder.right_chat_textview.setText(model.getMessage());
   }else {
       holder.left_chat_view.setVisibility(View.VISIBLE);
       holder.right_chat_view.setVisibility(View.GONE);
       holder.left_chat_textview.setText(model.getMessage());
   }
    }

    public class chat_Viewholder extends RecyclerView.ViewHolder {
        LinearLayout left_chat_view,right_chat_view;
        TextView left_chat_textview,right_chat_textview;

        public chat_Viewholder(@NonNull View itemView) {
            super(itemView);
            left_chat_view = itemView.findViewById(R.id.left_chat_view);
            left_chat_textview = itemView.findViewById(R.id.left_chat_textview);
            right_chat_view = itemView.findViewById(R.id.right_chat_view);
            right_chat_textview = itemView.findViewById(R.id.right_chat_Textview);
        }
    }
}
