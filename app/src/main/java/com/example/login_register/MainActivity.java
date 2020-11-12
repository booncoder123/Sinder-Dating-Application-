package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;


import com.example.login_register.Model.TabsAccessorAdapter;
import com.example.login_register.Model.Users;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Firebase
    FirebaseUser firebaseUser;
    DatabaseReference myRef;
//    Button mLoginBtn;

//    private  Toolbar mToolbar;

    private ViewPager2 myViewPager;
    private TabLayout mytableLayout;
    private TabsAccessorAdapter mytabsAccessorAdapter;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

//        mLoginBtn = findViewById(R.id.button);

        myViewPager = (ViewPager2) findViewById(R.id.tabpager);
        myViewPager.setAdapter(new TabsAccessorAdapter(this));
//        mLoginBtn = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, myViewPager, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            switch (position){
                case 0:{
                    tab.setText("Pending");
                    tab.setIcon(R.drawable.ic_pending);
                    break;

                }
                case 1:{
                    tab.setText("Confirm");
                    tab.setIcon(R.drawable.ic_confirm);
                    break;

                }
                case 2:{
                    tab.setText("Sending");
                    tab.setIcon(R.drawable.ic_del);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            mAuth.signOut();
            SendUserToLoginActivity();

        }
        if (item.getItemId() == R.id.menu_find_friend) {
            SendUsertoFindFriendAct();
        }
        if (item.getItemId() == R.id.menu_settings) {
            SendUserToSettingActivity();

        }
        return true;

    }

    private void SendUsertoFindFriendAct() {
        Intent FindFriendIntent = new Intent(MainActivity.this,FindActivity2.class);
        startActivity(FindFriendIntent);

    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this,login_new.class);
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

