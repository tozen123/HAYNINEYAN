package com.doublehammerstudio.haynineyan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView splashImage;
    private Button startButton, aboutUsButton;
    private Animation fallAnimation, wiggleAnimation, bounceAnimation, fadeAnimation, scaleIdleAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashImage = findViewById(R.id.splashImage);
        startButton = findViewById(R.id.startButton);
        aboutUsButton = findViewById(R.id.aboutUsButton);

        fallAnimation = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
        wiggleAnimation = AnimationUtils.loadAnimation(this, R.anim.wiggle);
        bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        scaleIdleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_idle);


        Intent musicServiceIntent = new Intent(this, MusicService.class);
        startService(musicServiceIntent);


        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(this);
        setupButtonListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        splashImage.startAnimation(fadeAnimation);
        new Handler().postDelayed(() -> {
            splashImage.startAnimation(scaleIdleAnimation);
        }, 1000);



        startButton.startAnimation(fadeAnimation);
        aboutUsButton.startAnimation(fadeAnimation);

        startButton.startAnimation(wiggleAnimation);
        aboutUsButton.startAnimation(wiggleAnimation);
    }

    private void setupButtonListeners() {
        startButton.setOnClickListener(v -> {
            SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(this);
            soundPlayer.playWoodButtonSound();

            startButton.clearAnimation();
            startButton.startAnimation(bounceAnimation);




            bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Intent intent = new Intent(MainActivity.this, TopicChooseActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        });

        aboutUsButton.setOnClickListener(v -> {

            SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(this);
            soundPlayer.playWoodButtonSound();

            aboutUsButton.clearAnimation();
            aboutUsButton.startAnimation(bounceAnimation);

            bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        });
    }
}
