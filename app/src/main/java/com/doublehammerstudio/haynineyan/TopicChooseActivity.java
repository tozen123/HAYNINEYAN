package com.doublehammerstudio.haynineyan;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class TopicChooseActivity extends AppCompatActivity {

    private ImageView splashImage;
    private Animation scaleIdleAnimation, fadeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_topic_choose);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            splashImage = findViewById(R.id.splashImage);
            scaleIdleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_idle);
            fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

            splashImage.startAnimation(fadeAnimation);
            new Handler().postDelayed(() -> {
                splashImage.startAnimation(scaleIdleAnimation);
            }, 1000);


            ImageSlider imageSlider = findViewById(R.id.slider);
            List<SlideModel> slideModels  = new ArrayList<>();
            slideModels.add(new SlideModel(R.drawable.non_mendelian_tab_png, ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.sex_related_inheritance_tab_png, ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.dna_tab, ScaleTypes.CENTER_CROP));
            imageSlider.setImageList(slideModels);


            imageSlider.setItemClickListener(i -> {
                switch (i) {
                    case 0:
                        Toast.makeText(this, "Non-Mendelian Inheritance clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(this, "Sex-Related Inheritance clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(this, "DNA Structure clicked", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(this, "Unknown item clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
            });
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
