package com.example.messengerapp.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FirebaseUtil {
    public static String curentuserId(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static DocumentReference currentuserDatail(){
        return FirebaseFirestore.getInstance().collection("users").document(curentuserId());
    }
    public static boolean isboolean(){
        if (curentuserId()!=null){
            return true;
        }
        else {
            return false;
        }
    }
    public static CollectionReference alluserCollectionRefference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static DocumentReference chatRoomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }
    public static String chatroomId(String user1,String user2){
        if (user1.hashCode()<user2.hashCode()){
            return user1+"_"+user2;
        }
        else {
            return user2+"_"+user1;
        }
    }
    public static CollectionReference getchatroomMessageRefference(String chatroomId){
        return chatRoomReference(chatroomId).collection("chats");
    }
    public static CollectionReference allchatcollectionrefference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }
    public  static  DocumentReference getotheruserfromchatroom(List<String> usersId){
        if (usersId.get(0).equals(FirebaseUtil.curentuserId())){
            return alluserCollectionRefference().document(usersId.get(1));
        }
        else {
            return alluserCollectionRefference().document(usersId.get(0));
        }
    }

}
