package com.example.login_register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class FindActivity2 extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView FindFriendRecycleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find2);

        FindFriendRecycleList = (RecyclerView) findViewById(R.id.find_friends_recycle_list);
        FindFriendRecycleList.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("Find Friends");
    }
}