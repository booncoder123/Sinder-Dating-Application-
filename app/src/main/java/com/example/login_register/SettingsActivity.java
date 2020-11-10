package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private Button updateAccount_button;
    private EditText userName,Email;
    private CircleImageView userProfileImage;
    private RadioButton male;
    private RadioButton female;
    FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        InitializeFields();
        updateAccount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(male.isChecked() || female.isChecked()){
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if((snapshot.child("sex").exists())){
                                SendUserToMainActivity();

                            }
                            else{
                                if(male.isChecked()) {
                            HashMap<String,Object> map = new HashMap<String,Object>();
                            map.put("sex","Male");
                            myRef.child("MyUsers").child(firebaseUser.getUid()).updateChildren(map);
                            male.setChecked(true);
                                    SendUserToMainActivity();

                                }
                                else{
                                    HashMap<String,Object> map = new HashMap<String,Object>();
                                    map.put("sex","Female");
                                    myRef.child("MyUsers").child(firebaseUser.getUid()).updateChildren(map);
                                    SendUserToMainActivity();

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
        
    }

    private void InitializeFields() {
        updateAccount_button = (Button) findViewById(R.id.pd_button);
        userProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        male = (RadioButton) findViewById(R.id.radioButton);
        female = (RadioButton) findViewById(R.id.radioButton);
        updateAccount_button = (Button) findViewById(R.id.pd_button);
        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();




    }

    private void show_user_respond() {

    }
    private void SendUserToMainActivity() {
        Intent MainIntent = new Intent(SettingsActivity.this,MainActivity.class);
        startActivity(MainIntent);
    }
}