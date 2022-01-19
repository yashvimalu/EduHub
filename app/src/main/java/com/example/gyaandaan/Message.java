package com.example.gyaandaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Message extends AppCompatActivity {

    TextView username;
    String sender,receiver;
    RecyclerView msg_rv;
    ImageButton send;
    EditText text_send;
    MessageAdapter messageAdapter;
    List<ChatModal> mChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        msg_rv = findViewById(R.id.msg_rv);
        msg_rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        msg_rv.setLayoutManager(linearLayoutManager);

        username = findViewById(R.id.username);
        send = findViewById(R.id.send_btn);
        text_send = findViewById(R.id.text_send);

        Bundle extras=getIntent().getExtras();
        sender=extras.getString("sender");
        receiver=extras.getString("receiver");

        username.setText(receiver);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                text_send.setText("");
                sendMessage(sender,receiver,msg);
            }
        });
        readMessage(sender,receiver);
    }

    private void sendMessage(String sender, String receiver,String message){

        if(!message.equals("")) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sender", sender);
            hashMap.put("receiver", receiver);
            hashMap.put("message", message);
            reference.child("Chats").push().setValue(hashMap);
        }
    }

    private void readMessage(String sender, String receiver) {
        Log.d("TAG", "** readMessage called");
    mChat = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatModal chat = dataSnapshot.getValue(ChatModal.class);
                    if(chat.getReceiver().toLowerCase().equals(receiver.toLowerCase()) && chat.getSender().toLowerCase().equals(sender.toLowerCase()) ||
                        chat.getReceiver().toLowerCase().equals(sender.toLowerCase())  && chat.getSender().toLowerCase().equals(receiver.toLowerCase()))
                    {
                        Log.d("TAG", "**Chat found");
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(Message.this,mChat,sender,receiver);
                    msg_rv.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}