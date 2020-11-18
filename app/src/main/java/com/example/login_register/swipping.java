package com.example.login_register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class swipping extends AppCompatActivity {

    private static  final String TAG = "MainActivity";
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    FloatingActionButton out;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipping);

        CardStackView cardStackView = findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override

            //checking drag
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG,"Ondragging: d=" + direction.name() +" ration=" + ratio);
            }

            @Override
            //checking swiped
            public void onCardSwiped(Direction direction) {
                Log.d(TAG,"OnCarSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if(direction == Direction.Right){
                    Toast.makeText(swipping.this,"Direction Right",Toast.LENGTH_SHORT).show();
                }
                if(direction == Direction.Top){
                    Toast.makeText(swipping.this,"Direction Top",Toast.LENGTH_SHORT).show();

                }
                if(direction == Direction.Left){
                    Toast.makeText(swipping.this,"Direction Left",Toast.LENGTH_SHORT).show();
                }
                if(direction == Direction.Bottom){
                    Toast.makeText(swipping.this,"Direction Bottom",Toast.LENGTH_SHORT).show();
                }


//                if(manager.getTopPosition() == adapter.getItemCount() - 4 )
//                {
//                    paginate();
//                }
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

            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());

        



        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());

    }
    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel(R.drawable.person1,"Person1","24","Jember"));
        items.add(new ItemModel(R.drawable.person2,"Person2","25","Thailand"));
        items.add(new ItemModel(R.drawable.person3,"Person3","26","Lao"));
        items.add(new ItemModel(R.drawable.person4,"Person4","27","YSA"));

        return items;
    }

}