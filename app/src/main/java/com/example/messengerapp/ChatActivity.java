package com.example.messengerapp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Utils.AndroidUtils;
import com.example.messengerapp.Utils.FirebaseUtil;
import com.example.messengerapp.model.UserDetails;
import com.example.messengerapp.model.UserMessageModel;
import com.example.messengerapp.model.Userchatroom;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {
UserDetails othereuser;
TextView username;
EditText Message_input;
ImageButton send_btn,back;
RecyclerView recyclerView;
String ChatroomId;
Userchatroom userchatroom;
chatRecyclerviewadapter adapter;
private RelativeLayout mainLayout;
    private RelativeLayout sendButtonLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatsactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        othereuser = AndroidUtils.setuserDetail(getIntent());
        username = findViewById(R.id.username);
        Message_input = findViewById(R.id.text_edit_message);
        send_btn = findViewById(R.id.message_send_btn);
        back = findViewById(R.id.back_chat);
       recyclerView = findViewById(R.id.Recyclerview_chat);

        ChatroomId = FirebaseUtil.chatroomId(FirebaseUtil.curentuserId(),othereuser.getUserid());
        username.setText(othereuser.getUsername());
        back.setOnClickListener((v)->{
            onBackPressed();
        });
        getorCreatechatRoom();
        setupRecyclerview();
        send_btn.setOnClickListener(v -> {
            String messsage =Message_input.getText().toString();
            if (messsage.isEmpty()){
                return;
            }
            sendMessageToUser(messsage);

        });
    }

    private void setupRecyclerview() {
        Query query = FirebaseUtil.getchatroomMessageRefference(ChatroomId).orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<UserMessageModel> option = new FirestoreRecyclerOptions.Builder<UserMessageModel>().setQuery(query,UserMessageModel.class).build();
        adapter = new chatRecyclerviewadapter(option,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    private void sendMessageToUser(String messsage) {
        userchatroom.setLastMessageSenderId(FirebaseUtil.curentuserId());
        userchatroom.setLastMessageTimestamp(Timestamp.now());
        userchatroom.setLastMssage(messsage);
        FirebaseUtil.chatRoomReference(ChatroomId).set(userchatroom);
        UserMessageModel userMessageModel = new UserMessageModel(messsage,FirebaseUtil.curentuserId(),Timestamp.now());
        FirebaseUtil.getchatroomMessageRefference(ChatroomId).add(userMessageModel).addOnCompleteListener(task -> {
      if (task.isSuccessful()){
          Message_input.setText("");
      }
        });
    }

    private void getorCreatechatRoom() {
        FirebaseUtil.chatRoomReference(ChatroomId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                userchatroom =task.getResult().toObject(Userchatroom.class);
                if (userchatroom==null){
                    //if frist time chating
                    userchatroom = new Userchatroom(ChatroomId, Arrays.asList(FirebaseUtil.curentuserId(),othereuser.getUserid()), Timestamp.now(),"");
                }
                FirebaseUtil.chatRoomReference(ChatroomId).set(userchatroom);
            }
        });
    }
}