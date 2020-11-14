package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login_register.Model.MyAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;





public class FindActivity2 extends AppCompatActivity {


    private RecyclerView FindFriendRecycleList;

    FirebaseAuth fAuth;
    DatabaseReference myRef;
    String userID;
    ArrayList<String> Stranger_List = new ArrayList<String>();
    ArrayList<String> Stranger_name ;
    String a;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find2);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fAuth = FirebaseAuth.getInstance();
        myRef  = FirebaseDatabase.getInstance().getReference().child("peopleNode").child(userID).child("Strangers");
        Stranger_name = new ArrayList<String>();
        Stranger_List = new ArrayList<String>();


//push data from Strangers node of this.person.id

        myRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dsp : snapshot.getChildren()) {



                            a = String.valueOf(dsp.getKey());
                            if(a!=null && a!=" ")
                                getDataFromID(a);




                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

        Toast.makeText(FindActivity2.this,a,Toast.LENGTH_SHORT).show();









        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Find Friends");

        super.onStart();



        ArrayList<Integer> images = new ArrayList<Integer>();


        MyAdapter myAdapter = new MyAdapter(FindActivity2.this,Stranger_List,images);
        FindFriendRecycleList = findViewById(R.id.find_friends_recycle_list);
        FindFriendRecycleList.setAdapter(myAdapter);
        FindFriendRecycleList.setLayoutManager(new LinearLayoutManager(FindActivity2.this));

    }

    private void getDataFromID(String a) {

            Toast.makeText(FindActivity2.this,a,Toast.LENGTH_SHORT).show();






    }


    }


