package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login_register.Model.CardStackAdapter;
import com.example.login_register.Model.GroupsFragment;
import com.example.login_register.Model.ItemModel;
import com.example.login_register.Model.RandomCollection;
import com.example.login_register.Model.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class swipping extends AppCompatActivity {

    private static  final String TAG = "MainActivity";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private double male_point = 0.5;
    private  double female_point = 0.5;
    FirebaseAuth fAuth;
    DatabaseReference myRef;
    String userID;
    String area_show;
    int i;
    ArrayList<Users> users;
    CardStackView cardStackView;
    Integer a = 1;
    boolean start = true;
    boolean right = true;
    boolean left = true;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_swipping);
        userID = fAuth.getCurrentUser().getUid(); // getting id
        myRef = FirebaseDatabase.getInstance().getReference("sexNode");
        cardStackView = findViewById(R.id.card_stack_view);

        if(start == true)
        {
            start = false;
            refresh();
        }


        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override

            //checking drag
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG,"Ondragging: d=" + direction.name() +" ration=" + ratio);

            }


            //checking swiped
            public void onCardSwiped(Direction direction) {
                Log.d(TAG,"OnCarSwiped: p=" + manager.getTopPosition() + " d=" + direction);

                if(direction == Direction.Right){
                    Toast.makeText(swipping.this,"Like",Toast.LENGTH_SHORT).show();
                    right = true;
                    left = false;
                }
                if(direction == Direction.Top){
                    Toast.makeText(swipping.this,"Direction Top",Toast.LENGTH_SHORT).show();

                }
                if(direction == Direction.Left){
                    Toast.makeText(swipping.this,"Direction Left",Toast.LENGTH_SHORT).show();
                    left = true;
                    right = false;

                }
                if(direction == Direction.Bottom){
                    Toast.makeText(swipping.this,"Direction Bottom",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG,"OnCardRewound: p=" + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG,"OnCardRewound: p=" + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG,"OnCardRewound: p=" + manager.getTopPosition());



            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG,"Disappear: p=" + manager.getTopPosition());
                System.out.println(position);
               String id = users.get(position).getUserid();
               System.out.println(users.get(position).getSex());
               if(right == true && left == false)
               {
                   System.out.println("like");
                   if(users.get(position).getSex() == "male"){
                       male_point += 0.1;
                       female_point -= 0.1;
                       refresh();
                   }
                   else {
                       male_point -= 0.1;
                       female_point += 0.1;
                       refresh();
                   }


               }

               if(right==false && left == true)
               {
                   System.out.println("dislike");
                   if(users.get(position).getSex() == "male"){
                       male_point -= 0.1;
                       female_point += 0.1;
                       refresh();
                   }
                   else {
                       female_point -= 0.1;
                       male_point +=0.1;
                       refresh();
                   }
               }


            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(1);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        refresh();

    }

    private void refresh() {

        System.out.println("a = " + a);
        ArrayList<String> id_keeper = new ArrayList<>();
        if(calculateWhoShow()=="male"){
            myRef = FirebaseDatabase.getInstance().getReference("sexNode").child("male");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap : snapshot.getChildren()){
                        id_keeper.add(snap.getValue().toString());

                    }
                    sendValueToGetObject(id_keeper);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            myRef = FirebaseDatabase.getInstance().getReference("sexNode").child("female");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap : snapshot.getChildren()){
                        id_keeper.add(snap.getValue().toString());
                    }
                    sendValueToGetObject(id_keeper);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }


    }

    private String calculateWhoShow() {
        RandomCollection<String> a = new RandomCollection<>();
        a.add(male_point, "male");
        a.add(female_point, "female");
        System.out.println(male_point+":"+female_point);
        System.out.println("P   "+a.next());
        return  a.next();

    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
       for(int i=0;i<users.size();i++){
           items.add(new ItemModel(R.drawable.person1,users.get(i).getfName(),"None","None"));
       }


        return items;
    }

    private void sendValueToGetObject(ArrayList<String> id_keeper) {
        ArrayList<Users> users_keeper = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("peopleNode");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()){
                    Users person = snap.getValue(Users.class);
                    for(String i : id_keeper){
                            if(i.equals(person.getUserid())){
                                users_keeper.add(person);

                            }
                    }
                }
                showOutObject(users_keeper);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showOutObject(ArrayList<Users> users_keeper) {
        users = users_keeper;
        adapter = new CardStackAdapter(addList(),users);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());



    }

}