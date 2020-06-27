package com.example.mygithubapps.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mygithubapps.R;

public class Splash extends AppCompatActivity {

    Animation anim_image,anim_text;
    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        image = findViewById(R.id.imageView_splash);
        text = findViewById(R.id.text_splash);
        anim_image = AnimationUtils.loadAnimation(this,R.anim.image);
        anim_text = AnimationUtils.loadAnimation(this, R.anim.text);

        image.startAnimation(anim_image);
        text.startAnimation(anim_text);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 2000);
    }
}

