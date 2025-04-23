package com.doublehammerstudio.haynineyan.FiveEActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doublehammerstudio.haynineyan.R;
import com.doublehammerstudio.haynineyan.SoundEffectPlayer;

public class ExploreActivity extends AppCompatActivity {
    private ImageView headerImage;
    private ScrollView mainLayout;
    private Button backButton;
    private LinearLayout videoViewLayout;
    private VideoView videoView1;
    private VideoView videoView2;
    private TextView videoTitle1, videoGuideQuestion1, instructions1;
    private TextView videoTitle2, videoGuideQuestion2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            headerImage = findViewById(R.id.headerImage);
            mainLayout = findViewById(R.id.mainLayout);
            backButton = findViewById(R.id.backButton);


            videoTitle1 = findViewById(R.id.videoTitle1);
            videoGuideQuestion1 = findViewById(R.id.videoGuideQuestion1);
            videoView1 = findViewById(R.id.videoView1);

            videoTitle2 = findViewById(R.id.videoTitle2);
            videoView2 = findViewById(R.id.videoView2);
            instructions1 = findViewById(R.id.instructions1);
            videoViewLayout = findViewById(R.id.videoViewLayout);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoTitle1);
            videoView1.setMediaController(mediaController);


            MediaController mediaController2 = new MediaController(this);

            mediaController2.setAnchorView(videoTitle2);
            videoView2.NsetMediaController(mediaController2);

            Intent intent = getIntent();
            String value = intent.getStringExtra("topic");

            if (value != null) {
                switch (value) {
                    case "Non Mendelian":
                        videoTitle1.setText("Incomplete Dominance and Codominance");
                        videoGuideQuestion1.setText(R.string.videoguidequestion1);

                        Uri nonMendelianVideoUri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.non_mendelian_incomplete_dominance_and_codominance_video_1);
                        videoView1.setVideoURI(nonMendelianVideoUri1);

                        videoTitle2.setVisibility(View.VISIBLE);
                        videoView2.setVisibility(View.VISIBLE);
                        videoViewLayout.setVisibility(View.VISIBLE);

                        videoTitle2.setText("Multiple Alleles");

                        Uri nonMendelianVideoUri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.non_mendelian_multiple_alleles_video_2);
                        videoView2.setVideoURI(nonMendelianVideoUri2);

                        instructions1.setText("INFO: These educational videos " +
                                "offer supplementary insights and " +
                                "information to help you learn " +
                                "more about Incomplete Dominance, " +
                                "Codominance, or Multiple " +
                                "Alleles.");
                        break;

                    case "Sex Related Inheritance":
                        videoTitle1.setText("Sex Limited Traits and Sex Influenced Traits");
                        videoGuideQuestion1.setText(R.string.videoguidequestion2);

                        Uri sex1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sex_related_sex_limited_traits_and_sex_influenced_traits_video_2);
                        videoView1.setVideoURI(sex1);

                        videoTitle2.setVisibility(View.VISIBLE);
                        videoView2.setVisibility(View.VISIBLE);
                        videoViewLayout.setVisibility(View.VISIBLE);
                        videoTitle2.setText("Sex-Linked Genes");

                        Uri sex2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sex_related_sex_linked_genes_video_1);
                        videoView2.setVideoURI(sex2);

                        instructions1.setText("INFO: These educational videos " +
                                "offer supplementary insights and " +
                                "information to help you learn " +
                                "more about Sex Limited Traits and Sex Influenced Traits");
                        break;
                    case "DNA":
                        videoTitle2.setVisibility(View.GONE);
                        videoView2.setVisibility(View.GONE);
                        videoViewLayout.setVisibility(View.GONE);

                        videoTitle1.setText("DNA");
                        videoGuideQuestion1.setText(R.string.videoguidequestion3);

                        Uri DNAVideoUri1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dna_video_1);
                        videoView1.setVideoURI(DNAVideoUri1);

                        instructions1.setText("INFO: These educational videos " +
                                "offer supplementary insights and " +
                                "information to help you learn " +
                                "more about DNA");
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
            new Handler().postDelayed(() -> {

                Animation fallAnimation2 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
                mainLayout.setVisibility(View.VISIBLE);
                mainLayout.startAnimation(fallAnimation2);

            }, 400);
            Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());
                    soundPlayer.playWoodButtonSound();
                    backButton.startAnimation(bounceAnimation);
                    finish();
                }
            });
            return insets;
        });
    }
}