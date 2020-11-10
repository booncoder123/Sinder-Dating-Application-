package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;

    FirebaseAuth fAuth;
    DatabaseReference myRef;

    ProgressBar progressBar;
//    FirebaseFirestore fStore;
    String userID;


    //method for sending data to register at firebase
    private  void RegisterNow(final String name,String email,String password ,String phone){
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(register.this,"User Created",Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid(); // getting id
                    myRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(userID);

                    Map<String,Object> user = new HashMap<>();
                    user.put("fName",name);
                    user.put("email",email);
                    user.put("phone",phone);
                    user.put("imageURL","default");


                    myRef.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("value","OnSuccess: user Profile is created for "+ userID);
                            Intent i = new Intent(register.this,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("value","OnFailure:  "+ e.toString());
                        }
                    });


                }
                else{
                    Toast.makeText(register.this,"Error " + task.getException().getMessage() ,Toast.LENGTH_SHORT).show();


                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mFullName = findViewById(R.id.name);
        mEmail    =    findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mPhone = findViewById(R.id.Phone);
        mRegisterBtn = findViewById(R.id.login_button);
        mLoginBtn = findViewById(R.id.gologin);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();

         //check if it alreadys id there
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class)); //send the user to MainActivity
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String email = mEmail.getText().toString().trim(); // .trim() getting out space
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                String phone = mPhone.getText().toString();

                if(TextUtils.isEmpty(email)){ // .TextUtils. for using isEmpty()
                    mEmail.setError("Email is Required.");
                    return;

                }
                if(TextUtils.isEmpty(password)){ // .TextUtils. for using isEmpty()
                    mPassword.setError("Password is Required.");
                    return;

                }
                if(password.length() < 6){ // .TextUtils. for using isEmpty()
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;

                }
                progressBar.setVisibility(View.VISIBLE);
                RegisterNow(fullName,email,password,phone);



            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login_new.class));
            }
        });{

        }

    }
}