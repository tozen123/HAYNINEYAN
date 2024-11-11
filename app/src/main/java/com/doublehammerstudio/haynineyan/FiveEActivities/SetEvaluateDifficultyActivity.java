package com.doublehammerstudio.haynineyan.FiveEActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doublehammerstudio.haynineyan.FiveETabsActivity;
import com.doublehammerstudio.haynineyan.R;
import com.doublehammerstudio.haynineyan.SoundEffectPlayer;
import com.doublehammerstudio.haynineyan.TopicChooseActivity;

public class SetEvaluateDifficultyActivity extends AppCompatActivity {
    private ImageView headerImage, ropeBG;
    private ImageButton easyButton, averageButton, difficultyButton;
    private TextView labelSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_evaluate_difficulty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            headerImage = findViewById(R.id.headerImage);
            ropeBG = findViewById(R.id.rope_bg);

            labelSign = findViewById(R.id.labelSign);

            easyButton = findViewById(R.id.easyButton);
            averageButton = findViewById(R.id.averageButton);
            difficultyButton = findViewById(R.id.difficultButton);

            Intent intent = getIntent();
            String value = intent.getStringExtra("topic");


            animateButtons();

            Button backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());
                    soundPlayer.playWoodButtonSound();
                    Intent intent = new Intent(SetEvaluateDifficultyActivity.this, TopicChooseActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });
            easyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());
                    soundPlayer.playWoodButtonSound();
                    Intent intent = new Intent(SetEvaluateDifficultyActivity.this, EvaluateActivity.class);
                    intent.putExtra("topic", value);
                    intent.putExtra("difficulty", "Easy");
                    startActivity(intent);
                }
            });

            averageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());
                    soundPlayer.playWoodButtonSound();
                    Intent intent = new Intent(SetEvaluateDifficultyActivity.this, EvaluateActivity.class);
                    intent.putExtra("topic", value);
                    intent.putExtra("difficulty", "Average");
                    startActivity(intent);
                }
            });

            difficultyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());
                    soundPlayer.playWoodButtonSound();
                    Intent intent = new Intent(SetEvaluateDifficultyActivity.this, EvaluateActivity.class);
                    intent.putExtra("topic", value);
                    intent.putExtra("difficulty", "Difficult");
                    startActivity(intent);
                }
            });

            return insets;
        });
    }
    private void animateButtons() {
        new Handler().postDelayed(() -> {
            SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());
            soundPlayer.playRopeAnimSound();
            Animation fallAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            ropeBG.setVisibility(View.VISIBLE);
            ropeBG.startAnimation(fallAnimation1);


            labelSign.setVisibility(View.VISIBLE);
            labelSign.startAnimation(fallAnimation1);


            headerImage.setVisibility(View.VISIBLE);
            headerImage.startAnimation(fallAnimation1);
        }, 300);

        new Handler().postDelayed(() -> {
            Animation fallAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            easyButton.setVisibility(View.VISIBLE);
            easyButton.startAnimation(fallAnimation1);
        }, 400);

        new Handler().postDelayed(() -> {
            Animation fallAnimation2 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            averageButton.setVisibility(View.VISIBLE);
            averageButton.startAnimation(fallAnimation2);
        }, 600);

        new Handler().postDelayed(() -> {
            Animation fallAnimation3 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            difficultyButton.setVisibility(View.VISIBLE);
            difficultyButton.startAnimation(fallAnimation3);
        }, 800);


    }
}