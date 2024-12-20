package com.doublehammerstudio.haynineyan.FiveEActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doublehammerstudio.haynineyan.FiveETabsActivity;
import com.doublehammerstudio.haynineyan.R;
import com.doublehammerstudio.haynineyan.SoundEffectPlayer;
import com.doublehammerstudio.haynineyan.TopicChooseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EngageActivity extends AppCompatActivity {

    private ImageView imageHolder1, imageHolder2, imageHolder3, imageHolder4;
    private LinearLayout inputLayout;
    private GridLayout letterOptionsLayout;
    private String correctAnswer;
    private boolean inputBoxesCreated = false;
    private boolean answerCorrect = false;
    private EditText[] letterBoxes;
    private Button backButton;
    private ImageView headerImage;
    private TextView shortInfo;
    private LinearLayout mainLayout;
    private Animation bounceAnimation;

    private List<Button> letterButtons = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_engage);

        headerImage = findViewById(R.id.headerImage);
        mainLayout = findViewById(R.id.mainLayout);

        imageHolder1 = findViewById(R.id.imageHolder1);
        imageHolder2 = findViewById(R.id.imageHolder2);
        imageHolder3 = findViewById(R.id.imageHolder3);
        imageHolder4 = findViewById(R.id.imageHolder4);
        inputLayout = findViewById(R.id.inputLayout);
        backButton = findViewById(R.id.backButton);
        shortInfo = findViewById(R.id.shortInfo);
        letterOptionsLayout = findViewById(R.id.letterOptionsLayout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            Intent intent = getIntent();
            String value = intent.getStringExtra("topic");

            if (value != null) {
                switch (value) {
                    case "Non Mendelian":
                        imageHolder1.setImageResource(R.drawable.non_medelian_inheritance_engage_image_1);
                        imageHolder2.setImageResource(R.drawable.non_medelian_inheritance_engage_image_2);
                        imageHolder3.setImageResource(R.drawable.non_medelian_inheritance_engage_image_3);
                        imageHolder4.setImageResource(R.drawable.non_medelian_inheritance_engage_image_4);
                        shortInfo.setText(R.string.non_mendelian_engage_short_info);
                        correctAnswer = "Blending";
                        break;
                    case "Sex Related Inheritance":
                        imageHolder1.setImageResource(R.drawable.sex_related_inheritance_image_1);
                        imageHolder2.setImageResource(R.drawable.sex_related_inheritance_image_2);
                        imageHolder3.setImageResource(R.drawable.sex_related_inheritance_image_3);
                        imageHolder4.setImageResource(R.drawable.sex_related_inheritance_image_4);
                        shortInfo.setText(R.string.sex_related_inheritance_info);
                        correctAnswer = "Chromosomes";
                        break;
                    case "DNA":
                        imageHolder1.setImageResource(R.drawable.dna_engage_image_1);
                        imageHolder2.setImageResource(R.drawable.dna_engage_image_2);
                        imageHolder3.setImageResource(R.drawable.dna_engage_image_3);
                        imageHolder4.setImageResource(R.drawable.dna_engage_image_4);
                        shortInfo.setText(R.string.dna_info);
                        correctAnswer = "DNA";
                        break;
                    default:
                        break;
                }
            }

            if (!inputBoxesCreated && correctAnswer != null) {
                createInputBoxes(correctAnswer.length());
                createLetterOptions(correctAnswer);
                inputBoxesCreated = true;
            }
            bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());
                    soundPlayer.playWoodButtonSound();
                    backButton.startAnimation(bounceAnimation);
                    finish();
                }
            });



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
            return insets;
        });
    }
    private void createInputBoxes(int wordLength) {
        letterBoxes = new EditText[wordLength];


        for (int i = 0; i < wordLength; i++) {
            final int index = i;

            EditText letterBox = new EditText(this);
            letterBox.setLayoutParams(new LinearLayout.LayoutParams(
                    100,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            letterBox.setEms(1);
            letterBox.setGravity(Gravity.CENTER);
            letterBox.setTextSize(18);
            letterBox.setTextColor(Color.RED);
            letterBox.setMaxLines(1);
            letterBox.setTypeface(letterBox.getTypeface(), Typeface.BOLD);
            letterBox.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
            letterBox.setBackground(getResources().getDrawable(R.drawable.rounded_input_box));
            letterBox.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) letterBox.getLayoutParams();
            params.setMargins(5, 0, 5, 0);

            letterBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (count > 0 && after == 0 && index > 0) {
                        letterBoxes[index - 1].requestFocus();
                        letterBoxes[index - 1].setSelection(letterBoxes[index - 1].getText().length());
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && index < wordLength - 1) {
                        letterBoxes[index + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 1) {
                        letterBox.setText(s.subSequence(0, 1));
                        letterBox.setSelection(1);
                    }

                    if (areAllBoxesFilled()) {
                        checkAnswer();
                    }
                }
            });


            letterBoxes[index] = letterBox;

            inputLayout.addView(letterBox);
        }
    }

    private void createLetterOptions(String answer) {
        // Shuffle the letters of the correct answer
        List<Character> letters = new ArrayList<>();
        for (char c : answer.toCharArray()) {
            letters.add(c);
        }
        Collections.shuffle(letters);

        // Calculate the total number of grid cells (5 columns x 3 rows = 15)
        int totalCells = 12;
        int remainingCells = totalCells - letters.size();

        // Add random letters to fill the remaining grid cells
        for (int i = 0; i < remainingCells; i++) {
            letters.add(getRandomLetter());
        }

        // Shuffle the entire list of letters again to mix the answer and random letters
        Collections.shuffle(letters);

        // Add each letter as a button in the GridLayout
        for (char letter : letters) {
            Button letterButton = new Button(this);
            letterButton.setLayoutParams(new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(140, 140)
            ));
            letterButton.setText(String.valueOf(letter));
            letterButton.setTextSize(21);
            letterButton.setTypeface(Typeface.DEFAULT_BOLD);
            letterButton.setBackground(getResources().getDrawable(R.drawable.rounded_letter_button));
            letterButton.setOnClickListener(view -> onLetterClick(letterButton));

            letterButtons.add(letterButton);
            letterOptionsLayout.addView(letterButton);
        }
    }

    private void resetLetterOptions() {
        // Clear all input boxes
        for (EditText letterBox : letterBoxes) {
            letterBox.setText("");
        }

        // Re-enable all letter buttons
        for (Button letterButton : letterButtons) {
            letterButton.setEnabled(true);
        }
    }
    private char getRandomLetter() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        return alphabet.charAt(random.nextInt(alphabet.length()));
    }


    private String shuffleLetters(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        StringBuilder shuffled = new StringBuilder();
        for (char c : characters) {
            shuffled.append(c);
        }
        return shuffled.toString();
    }

    private void onLetterClick(Button letterButton) {
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(this);
        soundPlayer.playWoodButtonSound();

        String letter = letterButton.getText().toString();

        // Find first empty box and set letter
        for (EditText letterBox : letterBoxes) {
            if (letterBox.getText().toString().isEmpty()) {
                letterBox.setText(letter);
                letterButton.setEnabled(false); // Disable used letter
                break;
            }
        }

        if (areAllBoxesFilled()) {
            checkAnswer();
        }
    }


    private boolean areAllBoxesFilled() {
        for (EditText letterBox : letterBoxes) {
            if (letterBox.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void checkAnswer() {
        StringBuilder userAnswer = new StringBuilder();
        for (EditText letterBox : letterBoxes) {
            userAnswer.append(letterBox.getText().toString().toUpperCase());
        }

        if (userAnswer.toString().equals(correctAnswer.toUpperCase())) {
            SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(this);
            soundPlayer.playCorrectAnswerSound();
            showCorrectAnswerDialog();
            shortInfo.setVisibility(View.VISIBLE);
        } else {
            SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(this);
            soundPlayer.playWrongAnswerSound();
            Toast.makeText(this, "Wrong Answer. Try Again!", Toast.LENGTH_SHORT).show();
            resetLetterOptions(); // Reset input layout and letter options on wrong answer
        }
    }

    private void showCorrectAnswerDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("You entered the correct answer.")
                .setPositiveButton("OK", (dialog, which) -> {
                    disableAllEditTexts();
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    private void disableAllEditTexts() {
        for (EditText letterBox : letterBoxes) {
            letterBox.setTextColor(Color.GREEN);
            letterBox.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
