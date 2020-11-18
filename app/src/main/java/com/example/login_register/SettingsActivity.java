package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.login_register.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private Button updateAccount_button;

    private CircleImageView userProfileImage;
    FirebaseUser firebaseUser;
    DatabaseReference myRef;
    FirebaseUser fuser;
    FirebaseAuth fAuth;
    EditText Name,Phone,Email;
    String sex;
    private  Uri imageUrl;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        InitializeFields();
        myRef = FirebaseDatabase.getInstance().getReference("peopleNode").child(fuser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                Name.setText(user.getfName());
                Email.setText(user.getEmail());
                Phone.setText(user.getPhone());


                if(user.getImageURL().equals("default")){
                    userProfileImage.setImageResource(R.mipmap.ic_launcher);
                }
                else{
                    Glide.with(SettingsActivity.this).load(user.getImageURL()).into(userProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();


            }
        });




        updateAccount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               myRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
//                       snapshot.child("fName").getRef().setValue(Name.getText().toString());
//                       snapshot.child("phone").getRef().setValue(Phone.getText().toString());

                       myRef.child("fName").setValue(Name.getText().toString());
                       myRef.child("email").setValue(Email.getText().toString());
                       myRef.child("phone").setValue(Phone.getText().toString());

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

            }
        });




        
    }

    private void choosePicture() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data.getData()!=null){
                imageUrl = data.getData();
                userProfileImage.setImageURI(imageUrl);
                    myRef.child("imageURL").setValue(imageUrl.toString());
        }
    }


    private void InitializeFields() {

        myRef = FirebaseDatabase.getInstance().getReference().child("peopleNode");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userProfileImage = findViewById(R.id.profile_image_setting);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        fAuth = FirebaseAuth.getInstance();
        Name = findViewById(R.id.Name_edit);
        Phone = findViewById(R.id.phone_edit);
        Email =findViewById(R.id.email_edit);
        updateAccount_button = findViewById(R.id.button_change_setting);








    }


    private void SendUserToMainActivity() {
        Intent MainIntent = new Intent(SettingsActivity.this,MainActivity.class);
        startActivity(MainIntent);
    }


}