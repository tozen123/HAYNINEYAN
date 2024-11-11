package com.doublehammerstudio.haynineyan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import android.Manifest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

            Button menuButton = findViewById(R.id.menuButton);

            Button pdfButton = findViewById(R.id.pdfButton);
            pdfButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        // For Android 9 and below, request storage permission
                        if (ContextCompat.checkSelfPermission(TopicChooseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(TopicChooseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            showPdfDialog();
                        }
                    } else {
                        showPdfDialog();
                    }
                }
            });
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                    soundPlayer.playWoodButtonSound();

                    Intent intent = new Intent(TopicChooseActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            });
            Button backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                    soundPlayer.playWoodButtonSound();

                    Intent intent = new Intent(TopicChooseActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            });

            splashImage.startAnimation(fadeAnimation);
            new Handler().postDelayed(() -> {
                splashImage.startAnimation(scaleIdleAnimation);
            }, 1000);


            ImageSlider imageSlider = findViewById(R.id.slider);
            List<SlideModel> slideModels  = new ArrayList<>();
            slideModels.add(new SlideModel(R.drawable.non_mendelian_tab_png, ScaleTypes.CENTER_INSIDE ));
            slideModels.add(new SlideModel(R.drawable.sex_related_inheritance_tab_png, ScaleTypes.CENTER_INSIDE ));
            slideModels.add(new SlideModel(R.drawable.dna_tab, ScaleTypes.CENTER_INSIDE));
            imageSlider.setImageList(slideModels);


            imageSlider.setItemClickListener(i -> {
                SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                soundPlayer.playWoodButtonSound();
                Intent intent = new Intent(TopicChooseActivity.this, FiveETabsActivity.class);

                switch (i) {
                    case 0:
                        // send string data "Non Mendelian"
                        intent.putExtra("topic", "Non Mendelian");
                        break;
                    case 1:
                        // send string data "Sex Related Inheritance"
                        intent.putExtra("topic", "Sex Related Inheritance");
                        break;
                    case 2:
                        // send string data "DNA"
                        intent.putExtra("topic", "DNA");
                        break;
                    default:
                        Toast.makeText(this, "Unknown item clicked", Toast.LENGTH_SHORT).show();
                        return; // Exit early if unknown
                }

                startActivity(intent);
            });

            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    private void showPdfDialog() {
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playWoodButtonSound();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose what topic you want to download as PDF");

        // Define topics and map each topic to its respective file
        String[] topics = {"Non Mendelian Inheritance", "Sex Related Inheritance", "DNA"};
        String[] fileNames = {"one.pdf", "two.pdf", "three.pdf"};

        builder.setItems(topics, (dialog, which) -> downloadPdf(fileNames[which], topics[which]));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void downloadPdf(String fileName, String topicName) {
        try {
            // Access PDF from raw resources
            Resources res = getResources();
            int resId = res.getIdentifier(fileName.split("\\.")[0], "raw", getPackageName());
            InputStream in = res.openRawResource(resId);

            // Define public Downloads folder path with the topic name as file name
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File filePath = new File(downloadsDir, topicName + ".pdf");
            OutputStream out = new FileOutputStream(filePath);

            // Write file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.flush();
            out.close();
            in.close();

            // Notify user of successful download
            Toast.makeText(this, "Downloaded " + topicName + " PDF to Downloads folder", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Download failed", Toast.LENGTH_SHORT).show();
        }
    }

    // Request permission result handler
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showPdfDialog();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
