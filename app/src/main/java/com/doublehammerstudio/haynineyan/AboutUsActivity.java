package com.doublehammerstudio.haynineyan;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutUsActivity extends AppCompatActivity {

    private ImageView headerImage, mainImage;
    private Button backButton;
    private Animation fadeInAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        headerImage = findViewById(R.id.imageView);
        mainImage = findViewById(R.id.mainImage);
        backButton = findViewById(R.id.backButton);

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        headerImage.startAnimation(fadeInAnimation);
        mainImage.startAnimation(fadeInAnimation);
        backButton.startAnimation(fadeInAnimation);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
