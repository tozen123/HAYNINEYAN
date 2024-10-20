package com.doublehammerstudio.haynineyan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        RelativeLayout splashLayout = findViewById(R.id.main);

        ViewCompat.setOnApplyWindowInsetsListener(splashLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        new Handler().postDelayed(() -> {
            splashLayout.startAnimation(fadeOut);

            new Handler().postDelayed(() -> {
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                String username = sharedPreferences.getString(KEY_USERNAME, null);

                Intent intent;
                if (username == null) {
                    intent = new Intent(SplashScreenActivity.this, UserNameActivity.class);
                } else {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }

                startActivity(intent);
                finish();
            }, 1500);
        }, 1500);
    }
}
