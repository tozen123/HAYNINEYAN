package com.doublehammerstudio.haynineyan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doublehammerstudio.haynineyan.FiveEActivities.ElaborateActivity;
import com.doublehammerstudio.haynineyan.FiveEActivities.EngageActivity;
import com.doublehammerstudio.haynineyan.FiveEActivities.EvaluateActivity;
import com.doublehammerstudio.haynineyan.FiveEActivities.ExplainActivity;
import com.doublehammerstudio.haynineyan.FiveEActivities.ExploreActivity;
import com.doublehammerstudio.haynineyan.FiveEActivities.SetEvaluateDifficultyActivity;

public class FiveETabsActivity extends AppCompatActivity {

    private ImageView headerImage, ropeBG;

    private ImageButton engageButton, exploreButton, explainButton, elaborateButton, evaluateButton;
    private Animation bounceAnimation;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_five_etabs);

        headerImage = findViewById(R.id.headerImage);
        backButton = findViewById(R.id.backButton);
        ropeBG = findViewById(R.id.rope_bg);

        bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            Intent intent = getIntent();
            String value = intent.getStringExtra("topic");

            if (value != null) {
                switch (value) {
                    case "Non Mendelian":
                        headerImage.setImageResource(R.drawable.non_mendelian_text_png);
                        headerImage.setPadding(10,10,10,10);

                        break;
                    case "Sex Related Inheritance":
                        headerImage.setImageResource(R.drawable.sex_related_inheritance_text_png);
                        headerImage.setPadding(10,10,10,10);


                        break;
                    case "DNA":
                        headerImage.setImageResource(R.drawable.dna_text);
                        headerImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        headerImage.setPadding(10,10,10,10);
                        break;
                    default:
                        headerImage.setImageResource(R.drawable.hay9yan_png);
                        break;
                }
            } else {
                Toast.makeText(FiveETabsActivity.this, "Error: No Topic", Toast.LENGTH_LONG).show();
                finish();
            }


            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backButton.startAnimation(bounceAnimation);
                    Intent intent = new Intent(FiveETabsActivity.this, TopicChooseActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });
            engageButton = findViewById(R.id.engageButton);
            exploreButton = findViewById(R.id.exploreButton);
            explainButton = findViewById(R.id.explainButton);
            elaborateButton = findViewById(R.id.elaborateButton);
            evaluateButton = findViewById(R.id.evaluateButton);





            animateButtons();

            engageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());
                    soundPlayer.playWoodButtonSound();

                    engageButton.startAnimation(bounceAnimation);
                    Intent intent = new Intent(FiveETabsActivity.this, EngageActivity.class);
                    intent.putExtra("topic", value);
                    startActivity(intent);
                }
            });

            exploreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                    soundPlayer.playWoodButtonSound();
                    exploreButton.startAnimation(bounceAnimation);
                    Intent intent = new Intent(FiveETabsActivity.this, ExploreActivity.class);
                    intent.putExtra("topic", value);
                    startActivity(intent);
                }
            });
            explainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                    soundPlayer.playWoodButtonSound();
                    explainButton.startAnimation(bounceAnimation);
                    Intent intent = new Intent(FiveETabsActivity.this, ExplainActivity.class);
                    intent.putExtra("topic", value);
                    startActivity(intent);
                }
            });
            elaborateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                    soundPlayer.playWoodButtonSound();
                    elaborateButton.startAnimation(bounceAnimation);
                    Intent intent = new Intent(FiveETabsActivity.this, ElaborateActivity.class);
                    intent.putExtra("topic", value);
                    startActivity(intent);
                }
            });
            evaluateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                    soundPlayer.playWoodButtonSound();
                    evaluateButton.startAnimation(bounceAnimation);
                    Intent intent = new Intent(FiveETabsActivity.this, SetEvaluateDifficultyActivity.class);
                    intent.putExtra("topic", value);
                    startActivity(intent);
                }
            });

            return insets;
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
    private void animateButtons() {
        new Handler().postDelayed(() -> {
            SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

            soundPlayer.playRopeAnimSound();
            Animation fallAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            ropeBG.setVisibility(View.VISIBLE);
            ropeBG.startAnimation(fallAnimation1);

            headerImage.setVisibility(View.VISIBLE);
            headerImage.startAnimation(fallAnimation1);
        }, 300);

        new Handler().postDelayed(() -> {
            Animation fallAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            engageButton.setVisibility(View.VISIBLE);
            engageButton.startAnimation(fallAnimation1);
        }, 400);

        new Handler().postDelayed(() -> {
            Animation fallAnimation2 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            exploreButton.setVisibility(View.VISIBLE);
            exploreButton.startAnimation(fallAnimation2);
        }, 600);

        new Handler().postDelayed(() -> {
            Animation fallAnimation3 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            explainButton.setVisibility(View.VISIBLE);
            explainButton.startAnimation(fallAnimation3);
        }, 800);

        new Handler().postDelayed(() -> {
            Animation fallAnimation4 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            elaborateButton.setVisibility(View.VISIBLE);
            elaborateButton.startAnimation(fallAnimation4);
        }, 1000);

        new Handler().postDelayed(() -> {
            Animation fallAnimation5 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            evaluateButton.setVisibility(View.VISIBLE);
            evaluateButton.startAnimation(fallAnimation5);
        }, 1200);
    }


}
