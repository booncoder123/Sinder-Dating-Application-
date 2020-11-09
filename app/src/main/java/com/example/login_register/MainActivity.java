package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.login_register.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //Firebase
    FirebaseUser firebaseUser;
    DatabaseReference myRef;
    Button mLoginBtn;

//    public void logout(View view) {
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(), login_new.class));
//        finish();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        mLoginBtn = findViewById(R.id.button);

//
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), login_new.class));
                finish();
            }
        });





//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Users users = snapshot.getValue(Users.class);
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        //send to register act
//        Intent intent = new Intent(this,register.class);
//        startActivity(intent);
    }






}