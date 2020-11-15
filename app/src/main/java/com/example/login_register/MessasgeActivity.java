package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessasgeActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        profile_image = findViewById(R.id.profile_image2);
        username = findViewById(R.id.username);
        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println(userid);
        reference = FirebaseDatabase.getInstance().getReference("peopleNode").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a = snapshot.child("fName").getValue().toString();
                username.setText(a);
                System.out.println(userid);
                //can modify picture here
                profile_image.setImageResource(R.drawable.ic_account);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}