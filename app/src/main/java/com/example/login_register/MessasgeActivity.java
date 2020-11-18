package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.login_register.Model.Chat;
import com.example.login_register.Model.Message_adapter;
import com.example.login_register.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessasgeActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;
    RecyclerView recyclerView;

    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;
    ImageButton btn_send;
    EditText text_send;
    ArrayList<Chat> mchat;
    Message_adapter messageAdapter;
    Context conText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        profile_image = findViewById(R.id.profile_image2);
        username = findViewById(R.id.username);
        intent = getIntent();

        // friend id
        String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println(userid);

        recyclerView = findViewById(R.id.chat_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);






        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fuser.getUid(), userid, msg);
                }
                else {
                    Toast.makeText(MessasgeActivity.this,"You cannot send empty text",Toast.LENGTH_SHORT);
                }
                text_send.setText("");
            }
        });
        //bug
        reference = FirebaseDatabase.getInstance().getReference("peopleNode").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                         Users user = snapshot.getValue(Users.class);
                          username.setText(user.getfName());
                            readMesg(fuser.getUid(),userid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private  void sendMessage(String sender,String reciever,String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashhMap = new HashMap<>();
        hashhMap.put("sender",sender);
        hashhMap.put("reciever",reciever);
        hashhMap.put("message",message);
        reference.child("Chats").push().setValue(hashhMap);
    }

    //can add images here
    private void readMesg(String myid,String userid)
    {
        mchat = new ArrayList<Chat>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                for( DataSnapshot snap: snapshot.getChildren()){
                    Chat chat = snap.getValue(Chat.class);
                    System.out.println(chat.getMessage());
                    if(chat.getReciever().equals(myid) && chat.getSender().equals(userid) || chat.getReciever().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }
                    //add image here
                    messageAdapter = new Message_adapter(MessasgeActivity.this,mchat);
                    recyclerView.setAdapter(messageAdapter);


                }
            }


           public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}