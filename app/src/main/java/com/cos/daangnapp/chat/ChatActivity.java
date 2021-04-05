package com.cos.daangnapp.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.cos.daangnapp.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChatList;
    private ChatAdapter chatAdapter;
    private DatabaseReference myRef;
    private TextView etText;
    private Button btnSend;
    private static final String TAG = "ChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        int userId = pref.getInt("userId",0);

        // 지금 접속한사람의 네임이 뜬다.
        String username = pref.getString("nickName","");
        Log.d(TAG, "onCreate: 아이디" + username);

        // 게시판작성글 id, 게시판작성자 네임
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int postId = intent.getIntExtra("postId",1);
        Log.d(TAG, "onCreate: 포스트아이디" + postId);
        Log.d(TAG, "onCreate: name" + name);

        // Write a message to the database 데베에 메시지 보내기
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(String.valueOf(postId)).child("chat");

        etText = findViewById(R.id.et_msg);
        btnSend = findViewById(R.id.btn_chat);
        btnSend.setOnClickListener(v -> {
            String msg = etText.getText().toString(); // msg
            if (msg != null){
                ChatData chat = new ChatData();
                chat.setNickname(username);
                chat.setMsg(msg);
                myRef.push().setValue(chat);
                //myRef.setValue("Hello, World!123");
                etText.setText("");
            }
        });

        List<ChatData> chats = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(new ChatActivity(), RecyclerView.VERTICAL,false);
        rvChatList = findViewById(R.id.rv_chat_list);
        rvChatList.setLayoutManager(manager);
        chatAdapter = new ChatAdapter(chats, username);

        rvChatList.setAdapter(chatAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded: 스냅샷 " + snapshot);
                ChatData chat = snapshot.getValue(ChatData.class);
                chatAdapter.addItem(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}