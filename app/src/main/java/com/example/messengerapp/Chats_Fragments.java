package com.example.messengerapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messengerapp.Utils.FirebaseUtil;
import com.example.messengerapp.model.Userchatroom;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class Chats_Fragments extends Fragment {

RecyclerView recyclerView;
recentrecyclerViewadapter adapter;
    public Chats_Fragments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_chats__fragments, container, false);
       // we can access the all layout item by using view
        recyclerView = view.findViewById(R.id.recycler_view_fragments);
        setuprecyclerview();
    return view;
    }
    private void setuprecyclerview() {

    }

}
