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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindActivity2 extends AppCompatActivity {


    private RecyclerView FindFriendRecycleList;
    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find2);
        UserRef = FirebaseDatabase.getInstance().getReference().child("MyUsers");


        FindFriendRecycleList = (RecyclerView) findViewById(R.id.find_friends_recycle_list);
        FindFriendRecycleList.setHasFixedSize(true);
        FindFriendRecycleList.setLayoutManager(new LinearLayoutManager(this));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Find Friends");

        super.onStart();

        FirebaseRecyclerOptions<Contacts> options =
                new FirebaseRecyclerOptions.Builder<Contacts>()
                        .setQuery(UserRef,Contacts.class)
                        .build();
        FirebaseRecyclerAdapter<Contacts,FindFriendViewHolder> adapter =
                new FirebaseRecyclerAdapter<Contacts, FindFriendViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull FindFriendViewHolder holder, int position, @NonNull Contacts model) {
                        holder.userName.setText(""+model.getName());
                        holder.userStatus.setText(model.getStatus());

                    }

                    @NonNull
                    @Override
                    public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout,parent,false);
                        return  new FindFriendViewHolder(v);
                    }
                };
        adapter.startListening();
        FindFriendRecycleList.setAdapter(adapter);
    }

//    @Override
//    protected void onStart() {
//
//
//
//    }
    public  static class FindFriendViewHolder extends RecyclerView.ViewHolder{
        TextView userName,userStatus;
//        CircleImageView profileImage;

        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textviewid);
            userStatus = itemView.findViewById(R.id.textviewname);
//            profileImage = itemView.findViewById(R.id.users_profile_image);

        }
    }


}