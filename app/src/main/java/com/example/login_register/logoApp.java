package com.example.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class logoApp extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    TextView logo,slogan;
    ImageView image;
    private static int SPLASH_SCREEN = 5000;
    public static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animantion);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        Timer timer;

        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(topAnim);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(logoApp.this,login_new_version1.class);
                startActivity(intent);
                finish();
            }
        },4000);






    }
    public static Context getContext() {
        return mContext;
    }
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}