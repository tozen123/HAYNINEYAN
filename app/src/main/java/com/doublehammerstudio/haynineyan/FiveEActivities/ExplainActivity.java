package com.doublehammerstudio.haynineyan.FiveEActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doublehammerstudio.haynineyan.R;

public class ExplainActivity extends AppCompatActivity {
    private ImageView headerImage;
    private Button backButton;

    private LinearLayout non_mendelian_layout, sex_related_layout, dna_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explain);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            backButton = findViewById(R.id.backButton);

            headerImage = findViewById(R.id.headerImage);

            Intent intent = getIntent();
            String value = intent.getStringExtra("topic");

            non_mendelian_layout = findViewById(R.id.non_mendelian_layout);
            non_mendelian_layout.setVisibility(View.GONE);
            sex_related_layout = findViewById(R.id.sex_related_layout);
            sex_related_layout.setVisibility(View.GONE);
            dna_layout = findViewById(R.id.dna_layout);
            dna_layout.setVisibility(View.GONE);
            if (value != null) {
                switch (value) {
                    case "Non Mendelian":
                        non_mendelian_layout.setVisibility(View.VISIBLE);
                        break;
                    case "Sex Related Inheritance":
                        sex_related_layout.setVisibility(View.VISIBLE);

                        break;
                    case "DNA":
                        dna_layout.setVisibility(View.VISIBLE);

                        break;
                    default:
                        break;
                }
            }

            new Handler().postDelayed(() -> {

                Animation fallAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);

                headerImage.setVisibility(View.VISIBLE);
                headerImage.startAnimation(fallAnimation1);
            }, 200);

            Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    backButton.startAnimation(bounceAnimation);
                    finish();
                }
            });
            return insets;
        });
    }
}