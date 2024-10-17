package com.doublehammerstudio.haynineyan.FiveEActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doublehammerstudio.haynineyan.R;

public class EvaluateActivity extends AppCompatActivity {
    private ImageView headerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_evaluate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            headerImage = findViewById(R.id.headerImage);

            Intent intent = getIntent();
            String value = intent.getStringExtra("topic");

            if (value != null) {
                switch (value) {
                    case "Non Mendelian":

                        break;
                    case "Sex Related Inheritance":

                        break;
                    case "DNA":

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


            return insets;
        });
    }
}