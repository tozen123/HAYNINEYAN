package com.doublehammerstudio.haynineyan;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    SharedPreferences sharedPreferences;
    String currentUsername;
    TextView nameHolder;

    private static final String PREFS_NAME_QUIZ = "quiz_progress";
    private RecyclerView quizProgressRecyclerView;
    private QuizProgressAdapter quizProgressAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nameHolder = findViewById(R.id.nameHolder);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentUsername = sharedPreferences.getString(KEY_USERNAME, "");

        nameHolder.setText(currentUsername);


        Button btnRenameUsername = findViewById(R.id.btn_rename_username);
        btnRenameUsername.setOnClickListener(view -> showRenameDialog());
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                soundPlayer.playWoodButtonSound();

                Intent intent = new Intent(MenuActivity.this, TopicChooseActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        quizProgressRecyclerView = findViewById(R.id.quizProgressRecyclerView);
        quizProgressRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load quiz progress data from SharedPreferences
        List<String> quizProgressList = loadQuizProgress();
        quizProgressAdapter = new QuizProgressAdapter(quizProgressList);
        quizProgressRecyclerView.setAdapter(quizProgressAdapter);
    }
    private List<String> loadQuizProgress() {
        List<String> quizProgressList = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME_QUIZ, MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String progressData = entry.getValue().toString();
            quizProgressList.add(progressData);
        }
        return quizProgressList;
    }
    private void showRenameDialog() {
        Dialog renameDialog = new Dialog(this);
        renameDialog.setContentView(R.layout.dialog_rename_username);

        EditText etNewUsername = renameDialog.findViewById(R.id.et_new_username);
        Button btnSaveUsername = renameDialog.findViewById(R.id.btn_save_username);


        etNewUsername.setText(currentUsername);



        btnSaveUsername.setOnClickListener(view -> {
            String newUsername = etNewUsername.getText().toString().trim();

            if (newUsername.isEmpty()) {
                Toast.makeText(MenuActivity.this, "Please enter a new username", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_USERNAME, newUsername);
                editor.apply();
                nameHolder.setText(newUsername);

                Toast.makeText(MenuActivity.this, "Username updated successfully", Toast.LENGTH_SHORT).show();
                renameDialog.dismiss();
            }
        });

        renameDialog.show();
    }
}
