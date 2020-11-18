package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.example.login_register.Model.TabsAccessorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
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
    FloatingActionButton fab_add, setting_button, find_friend_button,exit_buton;
    Animation fabOpen,fabClose,fabClock,fabantiClock;
//    Button mLoginBtn;

//    private  Toolbar mToolbar;

    private ViewPager2 myViewPager;
    private TabLayout mytableLayout;
    private TabsAccessorAdapter mytabsAccessorAdapter;
    private FirebaseAuth mAuth;
    Boolean isopen = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        fab_add = findViewById(R.id.floatingActionButton8);
        setting_button = findViewById(R.id.floatingActionButton10);
        find_friend_button = findViewById(R.id.add_but);
        exit_buton = findViewById(R.id.floatingActionButton_exit);



        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fabClock = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        fabantiClock = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anti);


//        mLoginBtn = findViewById(R.id.button);

        myViewPager = (ViewPager2) findViewById(R.id.tabpager);
        myViewPager.setAdapter(new TabsAccessorAdapter(this));
//        mLoginBtn = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isopen){
                    exit_buton.startAnimation(fabClose);
                    setting_button.startAnimation(fabClose);
                    find_friend_button.startAnimation(fabClose);
                    fab_add.startAnimation(fabClock);

                    setting_button.setClickable(false);
                    find_friend_button.setClickable(false);
                    exit_buton.setClickable(false);
                    isopen = false;
                    exit_buton.setVisibility(View.VISIBLE);
                    setting_button.setVisibility(View.VISIBLE);
                    find_friend_button.setVisibility(View.VISIBLE);


                    find_friend_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SendUsertoFindFriendAct();
                        }
                    });

                    setting_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SendUserToSettingActivity();
                        }
                    });
                    exit_buton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SendUserToLoginActivity();
                            FirebaseAuth.getInstance().signOut();

                        }
                    });
                }
                else {
                    exit_buton.startAnimation(fabOpen);
                    setting_button.startAnimation(fabOpen);
                    find_friend_button.startAnimation(fabOpen);
                    fab_add.startAnimation(fabantiClock);

                    setting_button.setClickable(true);
                    find_friend_button.setClickable(true);
                    exit_buton.setClickable(true);

                    isopen = true;

                }
            }
        });



        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, myViewPager, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            switch (position){
                case 0:{
                    tab.setText("Search");
                    tab.setIcon(R.drawable.mate);
                    break;

                }
                case 1:{
                    tab.setText("chat");
                    tab.setIcon(R.drawable.chatt);
                    break;

                }
                case 2:{
                    tab.setText("friends");
                    tab.setIcon(R.drawable.friends_member);
                    break;

                }
            }
            }
        });
        tabLayoutMediator.attach();

//        mLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), login_new.class));
//                finish();
//            }
//        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseUser == null){
            SendUserToLoginActivity();
        }
        else{
//            VerifyUserExistance();

        }
    }

    private void VerifyUserExistance() {
        String currentUserID = mAuth.getCurrentUser().getUid();
        myRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("sex").exists()){

                }
                else {
                    SendUserToSettingActivity();

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }




    private void SendUsertoFindFriendAct() {
        Intent FindFriendIntent = new Intent(MainActivity.this,FindActivity2.class);
        startActivity(FindFriendIntent);

    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this,login_new_version1.class);
        startActivity(loginIntent);
    }
    private void SendUserToSettingActivity() {
        Intent SettingActivity = new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(SettingActivity);
    }

    private void SendUserToFindActivity() {
        Intent FindActivity = new Intent(MainActivity.this,FindActivity2.class);
        startActivity(FindActivity);
    }




}

