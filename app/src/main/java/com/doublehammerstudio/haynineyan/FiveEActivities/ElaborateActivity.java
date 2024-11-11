package com.doublehammerstudio.haynineyan.FiveEActivities;

import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doublehammerstudio.haynineyan.GenotypeSpinnerAdapter;
import com.doublehammerstudio.haynineyan.R;
import com.doublehammerstudio.haynineyan.SoundEffectPlayer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ElaborateActivity extends AppCompatActivity {

    private ImageView headerImage;
    private TextView genotypeValue, phenotypeValue, genotypicRatioValue, phenotypicRatioValue;
    private Button backButton;
    private Button backButton1;
    private Animation bounceAnimation;

    private Spinner firstSpinner, categorySpinner, parentOneSpinner, parentTwoSpinner;
    private GridLayout punnettGrid;
    private ImageView cell1Image, cell2Image, cell3Image, cell4Image;
    private TextView cell1Text, cell2Text, cell3Text, cell4Text;

    // Data for spinners
    private String[] dominanceTypes = {"Incomplete Dominance", "Codominance", "Multiple Alleles"};
    private String[] categories = {"Flower", "Dog", "Blood"};

    private HashMap<String, String[]> genotypeMap = new HashMap<>();
    private HashMap<String, int[]> imageMap = new HashMap<>();
    private HashMap<String, Integer> imageMapBlood = new HashMap<>();

    private TextView firstSpinnerLabel, categoryLabel;

    private String[] sex_related_inheritance_types = {"Sex-Linked Genes", "Sex-Limited Traits", "Sex-Influenced Traits"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_elaborate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Initialize views
            headerImage = findViewById(R.id.headerImage);
            firstSpinner = findViewById(R.id.dominanceTypeSpinner);
            categorySpinner = findViewById(R.id.categorySpinner);
            parentOneSpinner = findViewById(R.id.parentOneSpinner);
            parentTwoSpinner = findViewById(R.id.parentTwoSpinner);
            punnettGrid = findViewById(R.id.punnett_square_grid);

            firstSpinnerLabel = findViewById(R.id.dominanceTypeSpinnerLabel);
            categoryLabel = findViewById(R.id.categorySpinnerLabel);

            backButton = findViewById(R.id.backButton);



            genotypeValue = findViewById(R.id.genotype_value);
            phenotypeValue = findViewById(R.id.phenotype_value);
            genotypicRatioValue = findViewById(R.id.genotypic_ratio_value);
            phenotypicRatioValue = findViewById(R.id.phenotypic_ratio_value);

            // Initialize views for the Punnett square
            cell1Image = findViewById(R.id.punnett_cell_1_image);
            cell1Text = findViewById(R.id.punnett_cell_1_text);

            cell2Image = findViewById(R.id.punnett_cell_2_image);
            cell2Text = findViewById(R.id.punnett_cell_2_text);

            cell3Image = findViewById(R.id.punnett_cell_3_image);
            cell3Text = findViewById(R.id.punnett_cell_3_text);

            cell4Image = findViewById(R.id.punnett_cell_4_image);
            cell4Text = findViewById(R.id.punnett_cell_4_text);


            new Handler().postDelayed(() -> {
                Animation fallAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
                headerImage.setVisibility(View.VISIBLE);
                headerImage.startAnimation(fallAnimation1);
            }, 200);


            Intent intent = getIntent();
            String value = intent.getStringExtra("topic");

            firstSpinner.setVisibility(View.GONE);
            categorySpinner.setVisibility(View.GONE);

//            if (value != null) {
//                switch (value) {
//                    case "Non Mendelian":
//                        firstSpinner.setVisibility(View.VISIBLE);
//                        categorySpinner.setVisibility(View.VISIBLE);
//
//                        ArrayAdapter<String> dominanceTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dominanceTypes);
//                        dominanceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        firstSpinner.setAdapter(dominanceTypeAdapter);
//
//                        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
//                        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        categorySpinner.setAdapter(categoryAdapter);
//
//                        // Set up genotype data for categories
//                        genotypeMap.put("Flower", new String[]{"RR", "RW", "WW"}); // Flower options (Red, Pink, White)
//                        genotypeMap.put("Dog", new String[]{"BB", "BW", "WW"}); // Dog options (Black, Gray, White)
//
//                        imageMap.put("Flower", new int[]{R.drawable.red_flower_rr, R.drawable.pink_flower_rw, R.drawable.white_flower_ww});
//                        imageMap.put("Dog", new int[]{R.drawable.black_dog_bb, R.drawable.gray_dog_bw, R.drawable.white_dog_ww});
//
//
//                        imageMap.put("Codominance_Flower", new int[]{R.drawable.codominance_red_and_white_flower});
//                        imageMap.put("Codominance_Dog", new int[]{R.drawable.codominance_black_and_white_dog});
//
//
//                        imageMapBlood.put("Blood_AA", R.drawable.multiple_alleles_blood_type_a);
//                        imageMapBlood.put("Blood_Ai", R.drawable.multiple_alleles_blood_type_a);
//                        imageMapBlood.put("Blood_BB", R.drawable.multiple_alleles_blood_type_b);
//                        imageMapBlood.put("Blood_Bi", R.drawable.multiple_alleles_blood_type_b);
//                        imageMapBlood.put("Blood_AB", R.drawable.multiple_alleles_blood_type_ab);
//                        imageMapBlood.put("Blood_O", R.drawable.multiple_alleles_blood_type_o);
//
//                        // Separate handling for categorySpinner
//                        categorySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
//                                String selectedCategory = categories[position];
//                                updateParentSpinners(selectedCategory);
//                            }
//
//                            @Override
//                            public void onNothingSelected(android.widget.AdapterView<?> parent) {
//                            }
//                        });
//
//                        // Separate handling for dominanceTypeSpinner
//                        firstSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
//                                String selectedDominanceType = dominanceTypes[position];
//                                if (selectedDominanceType.equals("Multiple Alleles")) {
//                                    // Automatically set the category to "Blood"
//                                    categorySpinner.setSelection(findIndexInArray(categories, "Blood"));
//                                    categorySpinner.setEnabled(false);
//                                }
//                                else {
//                                    categorySpinner.setEnabled(true);
//
//                                }
//                                updateParentSpinners(categorySpinner.getSelectedItem().toString());
//                            }
//
//                            @Override
//                            public void onNothingSelected(android.widget.AdapterView<?> parent) {
//                            }
//                        });
//
//
//                        break;
//                    case "Sex Related Inheritance":
//                        firstSpinner.setVisibility(View.VISIBLE);
//                        firstSpinnerLabel.setText("Type of Sex-Related Inheritance");
//
//
//                        categoryLabel.setPadding(0, 18, 0, 18);
//                        categoryLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21); // Set font size to 21sp
//                        categoryLabel.setTextColor(Color.WHITE); // Set font color to black
//
//                        ArrayAdapter<String> sex_related_inheritance_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sex_related_inheritance_types);
//                        sex_related_inheritance_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        firstSpinner.setAdapter(sex_related_inheritance_adapter);
//
//                        firstSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
//                                String selectedDominanceType = sex_related_inheritance_types[position];
//
//
//                                if (selectedDominanceType.equals("Sex-Linked Genes")) {
//                                    categoryLabel.setText("Trait: Color Blindness");
//                                    updateSexRelatedSpinners("Sex-Linked Genes");
//                                } else if (selectedDominanceType.equals("Sex-Limited Traits")) {
//                                    categoryLabel.setText("Trait: Lactation in Cattle");
//                                    updateSexRelatedSpinners("Sex-Limited Traits");
//
//                                } else if (selectedDominanceType.equals("Sex-Influenced Traits")) {
//                                    categoryLabel.setText("Trait: Male Pattern Baldness");
//                                    updateSexRelatedSpinners("Sex-Influenced Traits");
//
//                                } else {
//
//                                    return;
//                                }
//                            }
//
//                            @Override
//                            public void onNothingSelected(android.widget.AdapterView<?> parent) {
//                            }
//                        });
//
//                        break;
//                    case "DNA":
//
//                        createDNAElaborate();
//
//
//                        break;
//                    default:
//                        break;
//                }
//            }


            if (value != null) {
                switch (value) {
                    case "Non Mendelian":
                        firstSpinner.setVisibility(View.VISIBLE);
                        categorySpinner.setVisibility(View.VISIBLE);

                        ArrayAdapter<String> dominanceTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dominanceTypes);
                        dominanceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        firstSpinner.setAdapter(dominanceTypeAdapter);

                        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
                        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        categorySpinner.setAdapter(categoryAdapter);

                        // Set up genotype data for categories
                        genotypeMap.put("Flower", new String[]{"RR", "RW", "WW"}); // Flower options (Red, Pink, White)
                        genotypeMap.put("Dog", new String[]{"BB", "BW", "WW"}); // Dog options (Black, Gray, White)

                        imageMap.put("Flower", new int[]{R.drawable.red_flower_rr, R.drawable.pink_flower_rw, R.drawable.white_flower_ww});
                        imageMap.put("Dog", new int[]{R.drawable.black_dog_bb, R.drawable.gray_dog_bw, R.drawable.white_dog_ww});

                        imageMap.put("Codominance_Flower", new int[]{R.drawable.codominance_red_and_white_flower});
                        imageMap.put("Codominance_Dog", new int[]{R.drawable.codominance_black_and_white_dog});

                        imageMapBlood.put("Blood_AA", R.drawable.multiple_alleles_blood_type_a);
                        imageMapBlood.put("Blood_Ai", R.drawable.multiple_alleles_blood_type_a);
                        imageMapBlood.put("Blood_BB", R.drawable.multiple_alleles_blood_type_b);
                        imageMapBlood.put("Blood_Bi", R.drawable.multiple_alleles_blood_type_b);
                        imageMapBlood.put("Blood_AB", R.drawable.multiple_alleles_blood_type_ab);
                        imageMapBlood.put("Blood_O", R.drawable.multiple_alleles_blood_type_o);

                        // Separate handling for categorySpinner
                        categorySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                                String selectedCategory = categories[position];
                                updateParentSpinners(selectedCategory);
                            }

                            @Override
                            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                            }
                        });

                        firstSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                                String selectedDominanceType = dominanceTypes[position];
                                List<String> categoryList = new ArrayList<>(Arrays.asList(categories)); // Copy categories to a modifiable list

                                if (selectedDominanceType.equals("Multiple Alleles")) {
                                    // Ensure "Blood" is included in the list and set the category to "Blood"
                                    if (!categoryList.contains("Blood")) {
                                        categoryList.add("Blood");
                                    }
                                    ArrayAdapter<String> updatedCategoryAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, categoryList);
                                    updatedCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    categorySpinner.setAdapter(updatedCategoryAdapter);

                                    // Set the category to "Blood" and disable the spinner
                                    categorySpinner.setSelection(findIndexInArray(categoryList.toArray(new String[0]), "Blood"));
                                    categorySpinner.setEnabled(false);
                                } else {
                                    // Remove "Blood" from the list and enable the spinner
                                    categoryList.remove("Blood");
                                    ArrayAdapter<String> updatedCategoryAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, categoryList);
                                    updatedCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    categorySpinner.setAdapter(updatedCategoryAdapter);

                                    // Set the selection back to the first item (or another default) if "Blood" was removed
                                    if (categoryList.size() > 0) {
                                        categorySpinner.setSelection(0);
                                    }
                                    categorySpinner.setEnabled(true);
                                }

                                // Update parent spinners based on the selected category
                                updateParentSpinners(categorySpinner.getSelectedItem().toString());
                            }

                            @Override
                            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                            }
                        });


                        break;

                    case "Sex Related Inheritance":
                        firstSpinner.setVisibility(View.VISIBLE);
                        firstSpinnerLabel.setText("Type of Sex-Related Inheritance");

                        categoryLabel.setPadding(0, 18, 0, 18);
                        categoryLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21); // Set font size to 21sp
                        categoryLabel.setTextColor(Color.WHITE); // Set font color to black

                        ArrayAdapter<String> sex_related_inheritance_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sex_related_inheritance_types);
                        sex_related_inheritance_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        firstSpinner.setAdapter(sex_related_inheritance_adapter);

                        firstSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                                String selectedDominanceType = sex_related_inheritance_types[position];

                                if (selectedDominanceType.equals("Sex-Linked Genes")) {
                                    categoryLabel.setText("Trait: Color Blindness");
                                    updateSexRelatedSpinners("Sex-Linked Genes");
                                } else if (selectedDominanceType.equals("Sex-Limited Traits")) {
                                    categoryLabel.setText("Trait: Lactation in Cattle");
                                    updateSexRelatedSpinners("Sex-Limited Traits");

                                } else if (selectedDominanceType.equals("Sex-Influenced Traits")) {
                                    categoryLabel.setText("Trait: Male Pattern Baldness");
                                    updateSexRelatedSpinners("Sex-Influenced Traits");

                                } else {
                                    return;
                                }
                            }

                            @Override
                            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                            }
                        });

                        break;

                    case "DNA":
                        createDNAElaborate();
                        break;

                    default:
                        break;
                }
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

            if(!value.equals("DNA")){
                Button instructionButton = findViewById(R.id.instructionButton);
                instructionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                        soundPlayer.playWoodButtonSound();

                        showInstructionsDialog(value);
                    }
                });
            }



            return insets;
        });


    }

    private void showInstructionsDialog(String topic) {
        // Create a dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_instructions); // Use custom layout
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); // Transparent background

        // Set up TextView and Button inside the dialog
        TextView instructionsText = dialog.findViewById(R.id.instructionsText);
        Button okButton = dialog.findViewById(R.id.okButton);

        // Define instructions based on topic
        String instructions;
        if ("Non Mendelian".equals(topic)) {
            instructions = "• Choose the genetic pattern you want to explore: Incomplete Dominance, Codominance, or Multiple Alleles.\n\n"
                    + "• Choose whether you want to observe Plants, Animals, or Blood Types.\n\n"
                    + "• Select the specific traits or alleles for Parent 1 and Parent 2 from the given options.\n\n"
                    + "• Once you've made your selections, the app will automatically display the possible offspring and their genotypes and phenotypes in a Punnett square.";
        } else if ("Sex Related Inheritance".equals(topic)) {
            instructions = "• Choose the genetic pattern you want to explore: Sex-Linked Genes, Sex-Limited Traits, or Sex-Influenced Traits.\n\n"
                    + "• Select the specific traits or alleles for Parent 1 and Parent 2 from the given options.\n\n"
                    + "• Once you've made your selections, the app will automatically display the possible offspring and their genotypes and phenotypes in a Punnett square.";
        } else {
            instructions = "No instructions available for this topic.";
        }

        // Set instructions text
        instructionsText.setText(instructions);

        // Set up OK button to dismiss the dialog
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

                soundPlayer.playWoodButtonSound();
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }






















    // Member variables
    private ImageView targetThymine, targetCytosine,  targetCytosine1, targetGuanine, targetAdenine, targetThymine1;
    private ImageView baseAdenine, baseGuanine, baseCytosine, baseAdenine1, baseGuanine1, baseThymine;

    private void createDNAElaborate() {
        setContentView(R.layout.activity_dna_elaborate);

        // Initialize the back button and target views
        backButton1 = findViewById(R.id.backButton1);

        targetThymine = findViewById(R.id.target_thymine);
        targetThymine1 = findViewById(R.id.target_thymine1);
        targetCytosine = findViewById(R.id.target_cytosine);
        targetCytosine1 = findViewById(R.id.target_cytosine1);
        targetGuanine = findViewById(R.id.target_guanine);
        targetAdenine = findViewById(R.id.target_adenine);

        baseAdenine = findViewById(R.id.adenine);
        baseGuanine = findViewById(R.id.guanine);
        baseCytosine = findViewById(R.id.cytosine);
        baseAdenine1 = findViewById(R.id.adenine1);
        baseGuanine1 = findViewById(R.id.guanine1);
        baseThymine = findViewById(R.id.thymine);


        bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        backButton1.setOnClickListener(view -> {
            SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());
            soundPlayer.playWoodButtonSound();
            backButton1.startAnimation(bounceAnimation);
            finish();
        });

        setupDragAndDrop();
    }

    private void setupDragAndDrop() {
        findViewById(R.id.adenineGenerator).setOnLongClickListener(view -> {
            generateDraggableView(R.drawable.adenine_red, "adenine");
            return true;
        });

        findViewById(R.id.thymineGenerator).setOnLongClickListener(view -> {
            generateDraggableView(R.drawable.thymine_yellow, "thymine");
            return true;
        });

        findViewById(R.id.guanineGenerator).setOnLongClickListener(view -> {
            generateDraggableView(R.drawable.guanine_blue, "guanine");
            return true;
        });

        findViewById(R.id.cytosineGenerator).setOnLongClickListener(view -> {
            generateDraggableView(R.drawable.cytosine_green, "cytosine");
            return true;
        });

        findViewById(R.id.adenine).setOnDragListener(dragListener);
        findViewById(R.id.guanine).setOnDragListener(dragListener);
        findViewById(R.id.cytosine).setOnDragListener(dragListener);
        findViewById(R.id.adenine1).setOnDragListener(dragListener);
        findViewById(R.id.guanine1).setOnDragListener(dragListener);
        findViewById(R.id.thymine).setOnDragListener(dragListener);


    }

    private void generateDraggableView(int drawableId, String baseType) {
        LinearLayout container = findViewById(R.id.generatedContrainer);

        if (container != null) {
            container.removeAllViews();

            ImageView newView = new ImageView(this);
            newView.setImageResource(drawableId);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    60
            );
            params.setMargins(4, 4, 4, 4);
            params.gravity = Gravity.CENTER;
            newView.setLayoutParams(params);

            newView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            newView.setTag("draggableView");
            newView.setTag(R.id.base_type, baseType);

            newView.setOnLongClickListener(longClickListener);

            container.addView(newView);
        } else {
            Log.e("ElaborateActivity", "Container not found. Make sure you have a container with the specified ID.");
        }
    }

    private final View.OnDragListener dragListener = (thisView, dragEvent) -> {
        int action = dragEvent.getAction();
        View draggedView = (View) dragEvent.getLocalState();

        if (action == DragEvent.ACTION_DROP) {
            if (draggedView != null && "draggableView".equals(draggedView.getTag())) {
                String baseType = (String) draggedView.getTag(R.id.base_type);

                switch (baseType) {

                    case "thymine":
                        if (thisView.getId() == R.id.adenine1) {
                            targetThymine1.setVisibility(View.VISIBLE);
                            setLayoutParamsAndDisable(baseAdenine1, targetThymine1);
                            Toast.makeText(thisView.getContext(), "Matched ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(thisView.getContext(), "Incorrect Match", Toast.LENGTH_SHORT).show();
                        }

                        if (thisView.getId() == R.id.adenine) {
                            targetThymine.setVisibility(View.VISIBLE);
                            setLayoutParamsAndDisable(baseAdenine, targetThymine);
                            Toast.makeText(thisView.getContext(), "Matched ", Toast.LENGTH_SHORT).show();
                        }  else {
                            Toast.makeText(thisView.getContext(), "Incorrect Match", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case "cytosine":
                        if (thisView.getId() == R.id.guanine) {
                            targetCytosine.setVisibility(View.VISIBLE);
                            setLayoutParamsAndDisable(baseGuanine, targetCytosine);
                            Toast.makeText(thisView.getContext(), "Matched", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(thisView.getContext(), "Incorrect Match", Toast.LENGTH_SHORT).show();
                        }

                        if (thisView.getId() == R.id.guanine1) {
                            targetCytosine1.setVisibility(View.VISIBLE);
                            setLayoutParamsAndDisable(baseGuanine1, targetCytosine1);
                            Toast.makeText(thisView.getContext(), "Matched", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(thisView.getContext(), "Incorrect Match", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "guanine":
                        if (thisView.getId() == R.id.cytosine) {
                            targetGuanine.setVisibility(View.VISIBLE);
                            setLayoutParamsAndDisable(baseCytosine, targetGuanine);
                            Toast.makeText(thisView.getContext(), "Matched", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(thisView.getContext(), "Incorrect Match", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "adenine":
                        if (thisView.getId() == R.id.thymine) {
                            targetAdenine.setVisibility(View.VISIBLE);
                            setLayoutParamsAndDisable(baseThymine, targetAdenine);
                            Toast.makeText(thisView.getContext(), "Matched", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(thisView.getContext(), "Incorrect Match", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Toast.makeText(thisView.getContext(), "Unknown base type!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
        return true;
    };


    private final View.OnLongClickListener longClickListener = view -> {
        ClipData clipData = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDragAndDrop(clipData, shadowBuilder, view, 0);
        return true;
    };




    private void setLayoutParamsAndDisable(View draggedView, View targetView) {
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playMutateSound();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
        params.setMargins(80, 0, -10, 0);
        params.weight = 1;
        draggedView.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 60);
        params.setMargins(-10, 0, 80, 0);
        params.weight = 1;
        targetView.setLayoutParams(params);

        targetView.setOnDragListener(null);
        draggedView.setOnLongClickListener(null);

        LinearLayout container = findViewById(R.id.generatedContrainer);
        if (container != null) {
            container.removeAllViews();
        }
    }





//
//            if (draggedView.getId() == R.id.cytosineGenerator) {
//        targetCytosine.setVisibility(View.VISIBLE);
//        setLayoutParamsAndDisable(targetCytosine, thisView);
//    }
//
//            if (draggedView.getId() == R.id.adenineGenerator) {
//        targetThymine1.setVisibility(View.VISIBLE);
//        setLayoutParamsAndDisable(targetThymine1, thisView);
//    }
//
//            if (draggedView.getId() == R.id.guanineGenerator) {
//        targetCytosine1.setVisibility(View.VISIBLE);
//        setLayoutParamsAndDisable(targetCytosine1, thisView);
//    }
//
//            if (draggedView.getId() == R.id.thymineGenerator) {
//        targetAdenine.setVisibility(View.VISIBLE);
//        setLayoutParamsAndDisable(targetAdenine, thisView);
//    }










    private void updateSexRelatedSpinners(String type) {
        if (type.equals("Sex-Linked Genes")) {
            String[] parentOneOptions = {"Normal female (XX)", "Carrier female (XCX)", "Color-blind female (XCXC)"};

            ArrayAdapter<String> parentOne = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parentOneOptions);
            parentOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            parentOneSpinner.setAdapter(parentOne);


            String[] parentTwoOptions = {"Normal male (XY)", "Color-blind male (XCY)"};

            ArrayAdapter<String> parentTwo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parentTwoOptions);
            parentTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            parentTwoSpinner.setAdapter(parentTwo);

        } else if (type.equals("Sex-Limited Traits")) {
            String[] parentOneOptions = {"Lactating Female (XLXL)", "Lactating Female (XLXl)", "Non-lactating Female (XlXl)"};

            ArrayAdapter<String> parentOne = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parentOneOptions);
            parentOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            parentOneSpinner.setAdapter(parentOne);


            String[] parentTwoOptions = {"Non-lactating Male (XLYL)", "Non-lactating Male (XLYl)", "Non-lactating Male (XlYl)"};

            ArrayAdapter<String> parentTwo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parentTwoOptions);
            parentTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            parentTwoSpinner.setAdapter(parentTwo);

        } else if (type.equals("Sex-Influenced Traits")) {

            String[] parentOneOptions = {
                    "Bald female (XBXB)",
                    "Non-bald female (XBXb)",
                    "Non-bald female (XbXb)"
            };

            ArrayAdapter<String> parentOne = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parentOneOptions);
            parentOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            parentOneSpinner.setAdapter(parentOne);

            String[] parentTwoOptions = {
                    "Bald male (XBYB)",
                    "Bald male (XBYb)",
                    "Non-bald male (XbYb)"
            };

            ArrayAdapter<String> parentTwo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, parentTwoOptions);
            parentTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            parentTwoSpinner.setAdapter(parentTwo);

        }

        android.widget.AdapterView.OnItemSelectedListener itemSelectedListener2 = new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                calculatePunnettSquareSexRelatedInheritance(parentOneSpinner.getSelectedItem().toString(), parentTwoSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        };

        parentOneSpinner.setOnItemSelectedListener(itemSelectedListener2);
        parentTwoSpinner.setOnItemSelectedListener(itemSelectedListener2);
    }


    private void calculatePunnettSquareSexRelatedInheritance(String parent1, String parent2) {
        String traitType = categoryLabel.getText().toString();

        if (traitType.equals("Trait: Color Blindness")) {
            String[] parent1Alleles = getSexLinkedAlleles(parent1);
            String[] parent2Alleles = getSexLinkedAlleles(parent2);

            if (parent1Alleles != null && parent2Alleles != null) {
                // Create offspring genotypes

                String offspring1;
                String offspring2;
                String offspring3;
                String offspring4;

                if (Arrays.toString(parent1Alleles).equals("[XC, XC]") &&
                        Arrays.toString(parent2Alleles).equals("[X, Y]")) {
                    offspring1 = parent1Alleles[0] + parent2Alleles[0]; // XC from mom, X from dad
                    offspring2 = parent1Alleles[0] + parent2Alleles[1]; // XC from mom, Y from dad
                    offspring3 = parent1Alleles[1] + parent2Alleles[0]; // XC from mom, X from dad
                    offspring4 = parent1Alleles[1] + parent2Alleles[1];
                } else {
                    offspring1 = sortAlleles(parent1Alleles[0] + parent2Alleles[0]);
                    offspring2 = sortAlleles(parent1Alleles[0] + parent2Alleles[1]);
                    offspring3 = sortAlleles(parent1Alleles[1] + parent2Alleles[0]);
                    offspring4 = sortAlleles(parent1Alleles[1] + parent2Alleles[1]);
                }

                if (offspring1.isEmpty()) {
                    Toast.makeText(ElaborateActivity.this, "Error occurred with the Algorithm", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (offspring2.isEmpty()) {
                    Toast.makeText(ElaborateActivity.this, "Error occurred with the Algorithm", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (offspring3.isEmpty()) {
                    Toast.makeText(ElaborateActivity.this, "Error occurred with the Algorithm", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (offspring4.isEmpty()) {
                    Toast.makeText(ElaborateActivity.this, "Error occurred with the Algorithm", Toast.LENGTH_SHORT).show();

                    return;
                }


                // Update UI with parent alleles
                String p1_allele1 = parent1Alleles[0]; // X or XC
                String p1_allele2 = parent1Alleles[1]; // X or Y
                String p2_allele1 = parent2Alleles[0]; // X or XC
                String p2_allele2 = parent2Alleles[1]; // Y

                TextView parent1Allele1 = findViewById(R.id.parent1_allele1);
                TextView parent1Allele2 = findViewById(R.id.parent1_allele2);
                TextView parent2Allele1 = findViewById(R.id.parent2_allele1);
                TextView parent2Allele2 = findViewById(R.id.parent2_allele2);

                parent1Allele1.setText(String.valueOf(p1_allele1));
                parent1Allele2.setText(String.valueOf(p1_allele2));
                parent2Allele1.setText(String.valueOf(p2_allele1));
                parent2Allele2.setText(String.valueOf(p2_allele2));

                // Offspring combinations (should be four genotypes)
                String[] offspring = {offspring1, offspring2, offspring3, offspring4};

                // Update the results for Punnett square
                updateResultsForSexRelatedInheritance(offspring);


//
//                setSexRelatedPunnettSquareImages(offspring[0], cell1Image, cell1Text);
//                setSexRelatedPunnettSquareImages(offspring[1], cell2Image, cell2Text);
//                setSexRelatedPunnettSquareImages(offspring[2], cell3Image, cell3Text);
//                setSexRelatedPunnettSquareImages(offspring[3], cell4Image, cell4Text);

                new Handler().postDelayed(() -> {
                    setSexRelatedPunnettSquareImages(offspring[0], cell1Image, cell1Text);
                }, 200);
                new Handler().postDelayed(() -> {
                    setSexRelatedPunnettSquareImages(offspring[1], cell2Image, cell2Text);
                }, 600);
                new Handler().postDelayed(() -> {
                    setSexRelatedPunnettSquareImages(offspring[2], cell3Image, cell3Text);
                }, 800);
                new Handler().postDelayed(() -> {
                    setSexRelatedPunnettSquareImages(offspring[3], cell4Image, cell4Text);
                }, 1200);
            }

        } else if (traitType.equals("Trait: Lactation in Cattle")) {
            String[] parent1Alleles = getLactationAlleles(parent1);
            String[] parent2Alleles = getLactationAlleles(parent2);

            if (parent1Alleles != null && parent2Alleles != null) {
                // Create offspring genotypes
                String offspring1 = parent1Alleles[0] + parent2Alleles[0];
                String offspring2 = parent1Alleles[0] + parent2Alleles[1];
                String offspring3 = parent1Alleles[1] + parent2Alleles[0];
                String offspring4 = parent1Alleles[1] + parent2Alleles[1];


                if (offspring1.isEmpty()) {
                    Toast.makeText(ElaborateActivity.this, "Error occurred with the Algorithm", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (offspring2.isEmpty()) {
                    Toast.makeText(ElaborateActivity.this, "Error occurred with the Algorithm", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (offspring3.isEmpty()) {
                    Toast.makeText(ElaborateActivity.this, "Error occurred with the Algorithm", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (offspring4.isEmpty()) {
                    Toast.makeText(ElaborateActivity.this, "Error occurred with the Algorithm", Toast.LENGTH_SHORT).show();

                    return;
                }


                // Update UI with parent alleles
                String p1_allele1 = parent1Alleles[0]; // X or XC
                String p1_allele2 = parent1Alleles[1]; // X or Y
                String p2_allele1 = parent2Alleles[0]; // X or XC
                String p2_allele2 = parent2Alleles[1]; // Y

                TextView parent1Allele1 = findViewById(R.id.parent1_allele1);
                TextView parent1Allele2 = findViewById(R.id.parent1_allele2);
                TextView parent2Allele1 = findViewById(R.id.parent2_allele1);
                TextView parent2Allele2 = findViewById(R.id.parent2_allele2);

                parent1Allele1.setText(String.valueOf(p1_allele1));
                parent1Allele2.setText(String.valueOf(p1_allele2));
                parent2Allele1.setText(String.valueOf(p2_allele1));
                parent2Allele2.setText(String.valueOf(p2_allele2));


                String[] offspring = {sortAlleles(offspring1), sortAlleles(offspring2), sortAlleles(offspring3), sortAlleles(offspring4)};

                // Set images based on offspring genotypes
//                setLactationPunnettSquareImages(offspring[0], cell1Image, cell1Text);
//                setLactationPunnettSquareImages(offspring[1], cell2Image, cell2Text);
//                setLactationPunnettSquareImages(offspring[2], cell3Image, cell3Text);
//                setLactationPunnettSquareImages(offspring[3], cell4Image, cell4Text);
                new Handler().postDelayed(() -> {
                    setLactationPunnettSquareImages(offspring[0], cell1Image, cell1Text);
                }, 200);
                new Handler().postDelayed(() -> {
                    setLactationPunnettSquareImages(offspring[1], cell2Image, cell2Text);
                }, 600);
                new Handler().postDelayed(() -> {
                    setLactationPunnettSquareImages(offspring[2], cell3Image, cell3Text);
                }, 800);
                new Handler().postDelayed(() -> {
                    setLactationPunnettSquareImages(offspring[3], cell4Image, cell4Text);
                }, 1200);
                updateResultsForLactation(offspring);
            }

        } else if (traitType.equals("Trait: Male Pattern Baldness")) {

            String[] parent1Alleles = getMalePatternBaldnessAlleles(parent1);
            String[] parent2Alleles = getMalePatternBaldnessAlleles(parent2);

            if (parent1Alleles != null && parent2Alleles != null) {
                // Create offspring genotypes
                String offspring1 = parent1Alleles[0] + parent2Alleles[0];  // First allele from both parents
                String offspring2 = parent1Alleles[0] + parent2Alleles[1];  // First allele from parent 1, second allele from parent 2
                String offspring3 = parent1Alleles[1] + parent2Alleles[0];  // Second allele from parent 1, first allele from parent 2
                String offspring4 = parent1Alleles[1] + parent2Alleles[1];

                if (offspring1.isEmpty() || offspring2.isEmpty() || offspring3.isEmpty() || offspring4.isEmpty()) {
                    Toast.makeText(ElaborateActivity.this, "Error occurred with the Algorithm", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Update UI with parent alleles
                String p1_allele1 = parent1Alleles[0]; // X or XC
                String p1_allele2 = parent1Alleles[1]; // X or Y
                String p2_allele1 = parent2Alleles[0]; // X or XC
                String p2_allele2 = parent2Alleles[1]; // Y

                TextView parent1Allele1 = findViewById(R.id.parent1_allele1);
                TextView parent1Allele2 = findViewById(R.id.parent1_allele2);
                TextView parent2Allele1 = findViewById(R.id.parent2_allele1);
                TextView parent2Allele2 = findViewById(R.id.parent2_allele2);

                parent1Allele1.setText(String.valueOf(p1_allele1));
                parent1Allele2.setText(String.valueOf(p1_allele2));
                parent2Allele1.setText(String.valueOf(p2_allele1));
                parent2Allele2.setText(String.valueOf(p2_allele2));


                // Set images based on offspring genotypes
//                setBaldnessPunnettSquareImages(offspring1, cell1Image, cell1Text);
//                setBaldnessPunnettSquareImages(offspring2, cell2Image, cell2Text);
//                setBaldnessPunnettSquareImages(offspring3, cell3Image, cell3Text);
//                setBaldnessPunnettSquareImages(offspring4, cell4Image, cell4Text);
                new Handler().postDelayed(() -> {
                    setBaldnessPunnettSquareImages(offspring1, cell1Image, cell1Text);
                }, 200);
                new Handler().postDelayed(() -> {
                    setBaldnessPunnettSquareImages(offspring2, cell2Image, cell2Text);
                }, 600);
                new Handler().postDelayed(() -> {
                    setBaldnessPunnettSquareImages(offspring3, cell3Image, cell3Text);
                }, 800);
                new Handler().postDelayed(() -> {
                    setBaldnessPunnettSquareImages(offspring4, cell4Image, cell4Text);
                }, 1200);
                // Update the results dynamically
                updateResultsForMalePatternBaldness(new String[]{offspring1, offspring2, offspring3, offspring4});
            }

        }

    }

    private String sortBaldGenotype(String genotype) {
        char[] alleles = genotype.toCharArray();
        Arrays.sort(alleles);
        return new String(alleles);
    }
    private void updateResultsForMalePatternBaldness(String[] offspring) {
        int countXBXB = 0, countXBXb = 0, countXbXb = 0, countXBYB = 0, countXBYb = 0, countXbYb = 0;

        for (String genotype : offspring) {
            switch (genotype) {
                case "XBXB":
                    countXBXB++;
                    break;
                case "XBXb":
                case "XbXB":  // Handle both orders
                    countXBXb++;
                    break;
                case "XbXb":
                    countXbXb++;
                    break;
                case "XBYB":
                    countXBYB++;
                    break;
                case "XBYb":
                case "YbXB":  // Handle both orders
                    countXBYb++;
                    break;
                case "XbYb":
                    countXbYb++;
                    break;
            }
        }

        // Calculate total offspring
        int total = countXBXB + countXBXb + countXbXb + countXBYB + countXBYb + countXbYb;

        // Generate Genotypic Ratio
        StringBuilder genotypicRatioText = new StringBuilder();
        boolean isFirstGenotype = true;
        if (countXBXB > 0) {
            genotypicRatioText.append(countXBXB).append(" XBXB (").append((countXBXB * 100 / total)).append("% XBXB)");
            isFirstGenotype = false;
        }
        if (countXBXb > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXBXb).append(" XBXb (").append((countXBXb * 100 / total)).append("% XBXb)");
            isFirstGenotype = false;
        }
        if (countXbXb > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXbXb).append(" XbXb (").append((countXbXb * 100 / total)).append("% XbXb)");
            isFirstGenotype = false;
        }
        if (countXBYB > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXBYB).append(" XBYB (").append((countXBYB * 100 / total)).append("% XBYB)");
            isFirstGenotype = false;
        }
        if (countXBYb > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXBYb).append(" XBYb (").append((countXBYb * 100 / total)).append("% XBYb)");
            isFirstGenotype = false;
        }
        if (countXbYb > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXbYb).append(" XbYb (").append((countXbYb * 100 / total)).append("% XbYb)");
        }

        // Generate Phenotypic Ratio
        StringBuilder phenotypicRatioText = new StringBuilder();
        boolean isFirstPhenotype = true;
        if (countXBXB + countXBXb > 0) {
            phenotypicRatioText.append(countXBXB + countXBXb).append(" Bald Females (").append(((countXBXB + countXBXb) * 100 / total)).append("% Bald Females)");
            isFirstPhenotype = false;
        }
        if (countXbXb > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countXbXb).append(" Non-bald Females (").append((countXbXb * 100 / total)).append("% Non-bald Females)");
            isFirstPhenotype = false;
        }
        if (countXBYB + countXBYb > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countXBYB + countXBYb).append(" Bald Males (").append(((countXBYB + countXBYb) * 100 / total)).append("% Bald Males)");
            isFirstPhenotype = false;
        }
        if (countXbYb > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countXbYb).append(" Non-bald Males (").append((countXbYb * 100 / total)).append("% Non-bald Males)");
        }

        // Generate Genotype Text
        StringBuilder genotypeText = new StringBuilder();
        isFirstGenotype = true;
        if (countXBXB > 0) {
            genotypeText.append("XBXB");
            isFirstGenotype = false;
        }
        if (countXBXb > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("XBXb");
            isFirstGenotype = false;
        }
        if (countXbXb > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("XbXb");
            isFirstGenotype = false;
        }
        if (countXBYB > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("XBYB");
            isFirstGenotype = false;
        }
        if (countXBYb > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("XBYb");
            isFirstGenotype = false;
        }
        if (countXbYb > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("XbYb");
        }

        // Generate Phenotype Text
        StringBuilder phenotypeText = new StringBuilder();
        isFirstPhenotype = true;
        if (countXBXB + countXBXb > 0) {
            phenotypeText.append("Bald Female");
            isFirstPhenotype = false;
        }
        if (countXbXb > 0) {
            if (!isFirstPhenotype) phenotypeText.append(", ");
            phenotypeText.append("Non-bald Female");
            isFirstPhenotype = false;
        }
        if (countXBYB + countXBYb > 0) {
            if (!isFirstPhenotype) phenotypeText.append(", ");
            phenotypeText.append("Bald Male");
            isFirstPhenotype = false;
        }
        if (countXbYb > 0) {
            if (!isFirstPhenotype) phenotypeText.append(", ");
            phenotypeText.append("Non-bald Male");
        }

        // Update the TextViews for Genotypic and Phenotypic ratios
        genotypeValue.setText(Html.fromHtml(String.format("%s", genotypicRatioText.toString())));
        phenotypeValue.setText(Html.fromHtml(String.format("%s", phenotypicRatioText.toString())));

        // Update Genotype and Phenotype TextViews
        genotypicRatioValue.setText(Html.fromHtml(String.format("%s", genotypeText.toString())));
        phenotypicRatioValue.setText(Html.fromHtml(String.format("%s", phenotypeText.toString())));
    }

//    private void updateResultsForMalePatternBaldness(String[] offspring) {
//        int countXBXB = 0, countXBXb = 0, countXbXb = 0, countXBYB = 0, countXBYb = 0, countXbYb = 0;
//
//        for (String genotype : offspring) {
//            switch (genotype) {
//                case "XBXB":
//                    countXBXB++;
//                    break;
//                case "XBXb":
//                case "XbXB":  // Handle both orders
//                    countXBXb++;
//                    break;
//                case "XbXb":
//                    countXbXb++;
//                    break;
//                case "XBYB":
//                    countXBYB++;
//                    break;
//                case "XBYb":
//                case "YbXB":  // Handle both orders
//                    countXBYb++;
//                    break;
//                case "XbYb":
//                    countXbYb++;
//                    break;
//            }
//        }
//
//        // Calculate total offspring
//        int total = countXBXB + countXBXb + countXbXb + countXBYB + countXBYb + countXbYb;
//
//        // Generate Genotypic Ratio
//        StringBuilder genotypicRatioText = new StringBuilder();
//        if (countXBXB > 0) genotypicRatioText.append(countXBXB).append(" XBXB (").append((countXBXB * 100 / total)).append("% XBXB)");
//        if (countXBXb > 0) genotypicRatioText.append(", ").append(countXBXb).append(" XBXb (").append((countXBXb * 100 / total)).append("% XBXb)");
//        if (countXbXb > 0) genotypicRatioText.append(", ").append(countXbXb).append(" XbXb (").append((countXbXb * 100 / total)).append("% XbXb)");
//        if (countXBYB > 0) genotypicRatioText.append(", ").append(countXBYB).append(" XBYB (").append((countXBYB * 100 / total)).append("% XBYB)");
//        if (countXBYb > 0) genotypicRatioText.append(", ").append(countXBYb).append(" XBYb (").append((countXBYb * 100 / total)).append("% XBYb)");
//        if (countXbYb > 0) genotypicRatioText.append(", ").append(countXbYb).append(" XbYb (").append((countXbYb * 100 / total)).append("% XbYb)");
//
//        // Generate Phenotypic Ratio
//        StringBuilder phenotypicRatioText = new StringBuilder();
//        if (countXBXB + countXBXb > 0) phenotypicRatioText.append(countXBXB + countXBXb).append(" Bald Females (").append(((countXBXB + countXBXb) * 100 / total)).append("% Bald Females)");
//        if (countXbXb > 0) phenotypicRatioText.append(", ").append(countXbXb).append(" Non-bald Females (").append((countXbXb * 100 / total)).append("% Non-bald Females)");
//        if (countXBYB + countXBYb > 0) phenotypicRatioText.append(", ").append(countXBYB + countXBYb).append(" Bald Males (").append(((countXBYB + countXBYb) * 100 / total)).append("% Bald Males)");
//        if (countXbYb > 0) phenotypicRatioText.append(", ").append(countXbYb).append(" Non-bald Males (").append((countXbYb * 100 / total)).append("% Non-bald Males)");
//
//        // Generate Genotype/s Text
//        StringBuilder genotypeText = new StringBuilder();
//        if (countXBXB > 0) genotypeText.append("XBXB");
//        if (countXBXb > 0) genotypeText.append(", XBXb");
//        if (countXbXb > 0) genotypeText.append(", XbXb");
//        if (countXBYB > 0) genotypeText.append(", XBYB");
//        if (countXBYb > 0) genotypeText.append(", XBYb");
//        if (countXbYb > 0) genotypeText.append(", XbYb");
//
//        // Generate Phenotype/s Text
//        StringBuilder phenotypeText = new StringBuilder();
//        if (countXBXB + countXBXb > 0) phenotypeText.append("Bald Female");
//        if (countXbXb > 0) phenotypeText.append(", Non-bald Female");
//        if (countXBYB + countXBYb > 0) phenotypeText.append(", Bald Male");
//        if (countXbYb > 0) phenotypeText.append(", Non-bald Male");
//
//        // Update the TextViews for Genotypic and Phenotypic ratios
//        genotypeValue.setText(Html.fromHtml(String.format("%s", genotypicRatioText.toString())));
//        phenotypeValue.setText(Html.fromHtml(String.format("%s", phenotypicRatioText.toString())));
//
//        // Update Genotype and Phenotype TextViews
//        genotypicRatioValue.setText(Html.fromHtml(String.format("%s", genotypeText.toString())));
//        phenotypicRatioValue.setText(Html.fromHtml(String.format("%s", phenotypeText.toString())));
//    }


    private void setBaldnessPunnettSquareImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype);  // Display the genotype in the cell

        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playMutateSound();

        // Use a switch to map the genotype to the correct image resource
        switch (genotype) {
            case "XBXB":
                imageView.setImageResource(R.drawable.sex_related_xbxb);  // Image for Bald Female (XBXB)
                break;
            case "XBXb":
            case "XbXB":  // Handle both allele orders
                imageView.setImageResource(R.drawable.sex_related_xbxb_);  // Image for Non-Bald Female (XBXb)
                break;
            case "XbXb":
                imageView.setImageResource(R.drawable.sex_related_xb_xb_);  // Image for Non-Bald Female (XbXb)
                break;
            case "XBYB":
                imageView.setImageResource(R.drawable.sex_related_xbyb);  // Image for Bald Male (XBYB)
                break;
            case "XBYb":
            case "YbXB":  // Handle both allele orders
                imageView.setImageResource(R.drawable.sex_related_xbyb_);  // Image for Non-Bald Male (XBYb)
                break;
            case "XbYb":
                imageView.setImageResource(R.drawable.sex_related_xb_yb_);  // Image for Non-Bald Male (XbYb)
                break;
            case "XbYB":
                imageView.setImageResource(R.drawable.sex_related_xb_yb);  // Image for Non-Bald Male (XbYb)
                break;
            default:
                imageView.setImageResource(android.R.color.transparent);  // If no match, use a transparent placeholder
                break;
        }
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeAnimation);
    }

    private String[] getMalePatternBaldnessAlleles(String parent) {
        switch (parent) {
            case "Bald female (XBXB)":
                return new String[]{"XB", "XB"};
            case "Non-bald female (XBXb)":
                return new String[]{"XB", "Xb"};
            case "Non-bald female (XbXb)":
                return new String[]{"Xb", "Xb"};
            case "Bald male (XBYB)":
                return new String[]{"XB", "YB"};
            case "Bald male (XBYb)":
                return new String[]{"XB", "Yb"};
            case "Non-bald male (XbYb)":
                return new String[]{"Xb", "Yb"};
            default:
                return null;
        }
    }



    private void setLactationPunnettSquareImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype);  // Set genotype label
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playMutateSound();
        switch (genotype) {
            case "XLXL":
                imageView.setImageResource(R.drawable.sex_related_xlxl);  // Lactating female
                break;
            case "XLXl":
            case "XlXL":
                imageView.setImageResource(R.drawable.sex_related_xlxi);  // Carrier female
                break;
            case "XlXl":
                imageView.setImageResource(R.drawable.sex_related_xixi);  // Non-lactating female
                break;
            case "XlYl":
                imageView.setImageResource(R.drawable.sex_related_xiyi);  // Non-lactating male (homozygous recessive)
                break;
            case "XLYL":
                imageView.setImageResource(R.drawable.sex_related_xlyl);  // Non-lactating male
                break;
            case "XLYl":
                imageView.setImageResource(R.drawable.sex_related_xlyi);  // Non-lactating male (carrier)
                break;

            case "XlYL":
                imageView.setImageResource(R.drawable.sex_related_xiyl);  // Non-lactating male (homozygous recessive)
                break;
            default:
                imageView.setImageResource(android.R.color.transparent);  // If no match, use a placeholder
                break;
        }
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeAnimation);
    }



    private String[] getLactationAlleles(String parent) {
        switch (parent) {
            case "Lactating Female (XLXL)":
                return new String[]{"XL", "XL"};
            case "Lactating Female (XLXl)":
                return new String[]{"XL", "Xl"};
            case "Non-lactating Female (XlXl)":
                return new String[]{"Xl", "Xl"};
            case "Non-lactating Male (XLYL)":
                return new String[]{"XL", "YL"};
            case "Non-lactating Male (XLYl)":
                return new String[]{"XL", "Yl"};
            case "Non-lactating Male (XlYl)":
                return new String[]{"Xl", "Yl"};
            default:
                return null;
        }
    }
//    private void updateResultsForLactation(String[] offspring) {
//        int countXLXL = 0, countXLXl = 0, countXlXl = 0, countXLYL = 0, countXLYl = 0, countXlYl = 0;
//
//        // Count each genotype in the offspring array
//        for (String genotype : offspring) {
//            switch (genotype) {
//                case "XLXL":
//                    countXLXL++;
//                    break;
//                case "XLXl":
//                    countXLXl++;
//                    break;
//                case "XlXl":
//                    countXlXl++;
//                    break;
//                case "XLYL":
//                    countXLYL++;
//                    break;
//                case "XLYl":
//                    countXLYl++;
//                    break;
//                case "XlYl":
//                    countXlYl++;
//                    break;
//            }
//        }
//
//        int total = countXLXL + countXLXl + countXlXl + countXLYL + countXLYl + countXlYl;
//
//        // Build the genotypic and phenotypic ratio text
//        StringBuilder genotypicRatioText = new StringBuilder();
//        if (countXLXL > 0) genotypicRatioText.append(countXLXL).append(" XLXL (").append((countXLXL * 100 / total)).append("% XLXL)");
//        if (countXLXl > 0) genotypicRatioText.append(", ").append(countXLXl).append(" XLXl (").append((countXLXl * 100 / total)).append("% XLXl)");
//        if (countXlXl > 0) genotypicRatioText.append(", ").append(countXlXl).append(" XlXl (").append((countXlXl * 100 / total)).append("% XlXl)");
//        if (countXLYL > 0) genotypicRatioText.append(", ").append(countXLYL).append(" XLYL (").append((countXLYL * 100 / total)).append("% XLYL)");
//        if (countXLYl > 0) genotypicRatioText.append(", ").append(countXLYl).append(" XLYl (").append((countXLYl * 100 / total)).append("% XLYl)");
//        if (countXlYl > 0) genotypicRatioText.append(", ").append(countXlYl).append(" XlYl (").append((countXlYl * 100 / total)).append("% XlYl)");
//
//        genotypeValue.setText(Html.fromHtml(String.format("%s", genotypicRatioText.toString())));
//
//        StringBuilder phenotypeText = new StringBuilder();
//        if (countXLXL > 0) phenotypeText.append("Lactating Female");
//        if (countXLXl > 0) phenotypeText.append(", Carrier Female");
//        if (countXlXl > 0) phenotypeText.append(", Non-lactating Female");
//        if (countXLYL > 0) phenotypeText.append(", Non-lactating Male");
//        if (countXLYl > 0) phenotypeText.append(", Carrier Male");
//        if (countXlYl > 0) phenotypeText.append(", Non-lactating Male");
//
//        phenotypicRatioValue.setText(Html.fromHtml(String.format("%s", phenotypeText.toString())));
//    }

    private void updateResultsForLactation(String[] offspring) {
        int countXLXL = 0, countXLXl = 0, countXlXl = 0, countXLYL = 0, countXLYl = 0, countXlYl = 0;

        // Count each genotype in the offspring array
        for (String genotype : offspring) {
            switch (genotype) {
                case "XLXL":
                    countXLXL++;
                    break;
                case "XLXl":
                    countXLXl++;
                    break;
                case "XlXl":
                    countXlXl++;
                    break;
                case "XLYL":
                    countXLYL++;
                    break;
                case "XLYl":
                    countXLYl++;
                    break;
                case "XlYl":
                    countXlYl++;
                    break;
            }
        }

        int total = countXLXL + countXLXl + countXlXl + countXLYL + countXLYl + countXlYl;

        // Build the genotypic ratio text with a flag to avoid starting with a comma
        StringBuilder genotypicRatioText = new StringBuilder();
        boolean isFirst = true;
        if (countXLXL > 0) {
            genotypicRatioText.append(countXLXL).append(" XLXL (").append((countXLXL * 100 / total)).append("% XLXL)");
            isFirst = false;
        }
        if (countXLXl > 0) {
            if (!isFirst) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXLXl).append(" XLXl (").append((countXLXl * 100 / total)).append("% XLXl)");
            isFirst = false;
        }
        if (countXlXl > 0) {
            if (!isFirst) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXlXl).append(" XlXl (").append((countXlXl * 100 / total)).append("% XlXl)");
            isFirst = false;
        }
        if (countXLYL > 0) {
            if (!isFirst) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXLYL).append(" XLYL (").append((countXLYL * 100 / total)).append("% XLYL)");
            isFirst = false;
        }
        if (countXLYl > 0) {
            if (!isFirst) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXLYl).append(" XLYl (").append((countXLYl * 100 / total)).append("% XLYl)");
            isFirst = false;
        }
        if (countXlYl > 0) {
            if (!isFirst) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXlYl).append(" XlYl (").append((countXlYl * 100 / total)).append("% XlYl)");
        }

        genotypeValue.setText(Html.fromHtml(String.format("%s", genotypicRatioText.toString())));

        // Build the phenotypic ratio text with a similar flag to avoid starting with a comma
        StringBuilder phenotypeText = new StringBuilder();
        isFirst = true;
        if (countXLXL > 0) {
            phenotypeText.append("Lactating Female");
            isFirst = false;
        }
        if (countXLXl > 0) {
            if (!isFirst) phenotypeText.append(", ");
            phenotypeText.append("Carrier Female");
            isFirst = false;
        }
        if (countXlXl > 0) {
            if (!isFirst) phenotypeText.append(", ");
            phenotypeText.append("Non-lactating Female");
            isFirst = false;
        }
        if (countXLYL > 0) {
            if (!isFirst) phenotypeText.append(", ");
            phenotypeText.append("Non-lactating Male");
            isFirst = false;
        }
        if (countXLYl > 0) {
            if (!isFirst) phenotypeText.append(", ");
            phenotypeText.append("Carrier Male");
            isFirst = false;
        }
        if (countXlYl > 0) {
            if (!isFirst) phenotypeText.append(", ");
            phenotypeText.append("Non-lactating Male");
        }

        phenotypicRatioValue.setText(Html.fromHtml(String.format("%s", phenotypeText.toString())));
    }

    private String sortAlleles(String genotype) {
        if (genotype.equals("XCX") || genotype.equals("XXC")) {
            return "XCX";  // Carrier female
        }

        if (genotype.equals("XCY") || genotype.equals("YCX")) {
            return "XCY";  // Color-blind male
        }

        return genotype;
    }

    private String[] getSexLinkedAlleles(String parent) {
        switch (parent) {
            case "Normal female (XX)":
                return new String[]{"X", "X"};  // Normal female
            case "Carrier female (XCX)":
                return new String[]{"XC", "X"};  // Carrier female: one X normal and one X with color-blind trait
            case "Color-blind female (XCXC)":
                return new String[]{"XC", "XC"};  // Color-blind female: both X's carry the color-blind trait
            case "Normal male (XY)":
                return new String[]{"X", "Y"};  // Normal male
            case "Color-blind male (XCY)":
                return new String[]{"XC", "Y"};  // Color-blind male: X carries color-blind trait, Y does not
            default:
                return null;
        }
    }



    private void setSexRelatedPunnettSquareImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype);  // Display the full genotype in the cell
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playMutateSound();
        // Use a switch to map genotype to the correct image resource
        switch (genotype) {
            case "XX":
                imageView.setImageResource(R.drawable.sex_related_xx);
                break;
            case "XCX":
                imageView.setImageResource(R.drawable.sex_related_xcx);
                break;
            case "XCXC":
                imageView.setImageResource(R.drawable.sex_related_xcxc);
                break;
            case "XY":
                imageView.setImageResource(R.drawable.sex_related_xy);
                break;
            case "XCY":
            case "XCCY":
                imageView.setImageResource(R.drawable.sex_related_xcy);
                break;
            default:
                imageView.setImageResource(android.R.color.transparent);  // If no match, use a transparent placeholder
                break;
        }

        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeAnimation);
    }

//    private void updateResultsForSexRelatedInheritance(String[] offspring) {
//        // Count occurrences of each genotype
//        int countXX = 0, countXY = 0, countXCX = 0, countXCXC = 0, countXCY = 0;
//
//        // Count each genotype in the offspring array
//        for (String genotype : offspring) {
//            switch (genotype) {
//                case "XX":
//                    countXX++;
//                    break;
//                case "XCX":
//                case "XcX":  // To handle case-insensitive versions
//                    countXCX++;
//                    break;
//                case "XCXC":
//                    countXCXC++;
//                    break;
//                case "XY":
//                    countXY++;
//                    break;
//                case "XCY":
//                case "XcY":  // To handle case-insensitive versions
//                    countXCY++;
//                    break;
//            }
//        }
//
//        // Calculate the total number of offspring
//        int total = countXX + countXCX + countXCXC + countXY + countXCY;
//
//        // Build the genotypic ratio string dynamically
//        StringBuilder genotypicRatioText = new StringBuilder();
//        if (countXX > 0) genotypicRatioText.append(countXX).append(" XX (").append((countXX * 100 / total)).append("% XX)");
//        if (countXCX > 0) genotypicRatioText.append(", ").append(countXCX).append(" XCX (").append((countXCX * 100 / total)).append("% XCX)");
//        if (countXCXC > 0) genotypicRatioText.append(", ").append(countXCXC).append(" XCXC (").append((countXCXC * 100 / total)).append("% XCXC)");
//        if (countXY > 0) genotypicRatioText.append(", ").append(countXY).append(" XY (").append((countXY * 100 / total)).append("% XY)");
//        if (countXCY > 0) genotypicRatioText.append(", ").append(countXCY).append(" XCY (").append((countXCY * 100 / total)).append("% XCY)");
//
//        // Build the phenotypic ratio string dynamically
//        StringBuilder phenotypicRatioText = new StringBuilder();
//        if (countXX > 0) phenotypicRatioText.append(countXX).append(" Normal Females (").append((countXX * 100 / total)).append("% Normal Females)");
//        if (countXCX > 0) phenotypicRatioText.append(", ").append(countXCX).append(" Carrier Females (").append((countXCX * 100 / total)).append("% Carrier Females)");
//        if (countXCXC > 0) phenotypicRatioText.append(", ").append(countXCXC).append(" Color-blind Females (").append((countXCXC * 100 / total)).append("% Color-blind Females)");
//        if (countXY > 0) phenotypicRatioText.append(", ").append(countXY).append(" Normal Males (").append((countXY * 100 / total)).append("% Normal Males)");
//        if (countXCY > 0) phenotypicRatioText.append(", ").append(countXCY).append(" Color-blind Males (").append((countXCY * 100 / total)).append("% Color-blind Males)");
//
//        // Set the dynamic text for the results
//        genotypeValue.setText(Html.fromHtml(String.format("%s", genotypicRatioText.toString())));
//        phenotypeValue.setText(Html.fromHtml(String.format("%s", phenotypicRatioText.toString())));
//
//        // Set the genotype/s and phenotype/s results dynamically
//        StringBuilder genotypeText = new StringBuilder();
//        if (countXX > 0) genotypeText.append("XX");
//        if (countXCX > 0) genotypeText.append(", XCX");
//        if (countXCXC > 0) genotypeText.append(", XCXC");
//        if (countXY > 0) genotypeText.append(", XY");
//        if (countXCY > 0) genotypeText.append(", XCY");
//
//        StringBuilder phenotypeText = new StringBuilder();
//        if (countXX > 0) phenotypeText.append("Normal Female");
//        if (countXCX > 0) phenotypeText.append(", Carrier Female");
//        if (countXCXC > 0) phenotypeText.append(", Color-blind Female");
//        if (countXY > 0) phenotypeText.append(", Normal Male");
//        if (countXCY > 0) phenotypeText.append(", Color-blind Male");
//
//        // Update the genotype and phenotype TextViews
//        genotypicRatioValue.setText(Html.fromHtml(String.format("%s", genotypeText.toString())));
//        phenotypicRatioValue.setText(Html.fromHtml(String.format("%s", phenotypeText.toString())));
//    }
    private void updateResultsForSexRelatedInheritance(String[] offspring) {
        int countXX = 0, countXY = 0, countXCX = 0, countXCXC = 0, countXCY = 0;

        for (String genotype : offspring) {
            switch (genotype) {
                case "XX":
                    countXX++;
                    break;
                case "XCX":
                case "XcX":
                    countXCX++;
                    break;
                case "XCXC":
                    countXCXC++;
                    break;
                case "XY":
                    countXY++;
                    break;
                case "XCY":
                case "XcY":
                    countXCY++;
                    break;
            }
        }

        int total = countXX + countXCX + countXCXC + countXY + countXCY;

        StringBuilder genotypicRatioText = new StringBuilder();
        boolean isFirstGenotype = true;
        if (countXX > 0) {
            genotypicRatioText.append(countXX).append(" XX (").append((countXX * 100 / total)).append("% XX)");
            isFirstGenotype = false;
        }
        if (countXCX > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXCX).append(" X^CX (").append((countXCX * 100 / total)).append("% X^CX)");
            isFirstGenotype = false;
        }
        if (countXCXC > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXCXC).append(" X^CX^C (").append((countXCXC * 100 / total)).append("% X^CX^C)");
            isFirstGenotype = false;
        }
        if (countXY > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXY).append(" XY (").append((countXY * 100 / total)).append("% XY)");
            isFirstGenotype = false;
        }
        if (countXCY > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countXCY).append(" X^CY (").append((countXCY * 100 / total)).append("% X^CY)");
        }

        StringBuilder phenotypicRatioText = new StringBuilder();
        boolean isFirstPhenotype = true;
        if (countXX > 0) {
            phenotypicRatioText.append(countXX).append(" Normal Females (").append((countXX * 100 / total)).append("% Normal Females)");
            isFirstPhenotype = false;
        }
        if (countXCX > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countXCX).append(" Carrier Females (").append((countXCX * 100 / total)).append("% Carrier Females)");
            isFirstPhenotype = false;
        }
        if (countXCXC > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countXCXC).append(" Color-blind Females (").append((countXCXC * 100 / total)).append("% Color-blind Females)");
            isFirstPhenotype = false;
        }
        if (countXY > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countXY).append(" Normal Males (").append((countXY * 100 / total)).append("% Normal Males)");
            isFirstPhenotype = false;
        }
        if (countXCY > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countXCY).append(" Color-blind Males (").append((countXCY * 100 / total)).append("% Color-blind Males)");
        }

        genotypeValue.setText(Html.fromHtml(String.format("%s", genotypicRatioText.toString())));
        phenotypeValue.setText(Html.fromHtml(String.format("%s", phenotypicRatioText.toString())));

        StringBuilder genotypeText = new StringBuilder();
        boolean isFirstGenotypeText = true;
        if (countXX > 0) {
            genotypeText.append("XX");
            isFirstGenotypeText = false;
        }
        if (countXCX > 0) {
            if (!isFirstGenotypeText) genotypeText.append(", ");
            genotypeText.append("X^CX");
            isFirstGenotypeText = false;
        }
        if (countXCXC > 0) {
            if (!isFirstGenotypeText) genotypeText.append(", ");
            genotypeText.append("X^CX^C");
            isFirstGenotypeText = false;
        }
        if (countXY > 0) {
            if (!isFirstGenotypeText) genotypeText.append(", ");
            genotypeText.append("XY");
            isFirstGenotypeText = false;
        }
        if (countXCY > 0) {
            if (!isFirstGenotypeText) genotypeText.append(", ");
            genotypeText.append("X^CY");
        }

        StringBuilder phenotypeText = new StringBuilder();
        boolean isFirstPhenotypeText = true;
        if (countXX > 0) {
            phenotypeText.append("Normal Female");
            isFirstPhenotypeText = false;
        }
        if (countXCX > 0) {
            if (!isFirstPhenotypeText) phenotypeText.append(", ");
            phenotypeText.append("Carrier Female");
            isFirstPhenotypeText = false;
        }
        if (countXCXC > 0) {
            if (!isFirstPhenotypeText) phenotypeText.append(", ");
            phenotypeText.append("Color-blind Female");
            isFirstPhenotypeText = false;
        }
        if (countXY > 0) {
            if (!isFirstPhenotypeText) phenotypeText.append(", ");
            phenotypeText.append("Normal Male");
            isFirstPhenotypeText = false;
        }
        if (countXCY > 0) {
            if (!isFirstPhenotypeText) phenotypeText.append(", ");
            phenotypeText.append("Color-blind Male");
        }

        genotypicRatioValue.setText(Html.fromHtml(String.format("%s", genotypeText.toString())));
        phenotypicRatioValue.setText(Html.fromHtml(String.format("%s", phenotypeText.toString())));
    }

    private int findIndexInArray(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1; // Not found
    }
    // Update parent genotype spinners based on the selected category
    private void updateParentSpinners(String category) {
        String[] genotypes = null;
        int[] images = null; // Use an array for images, except for blood type (handled separately below)

        // Get the selected dominance type
        String selectedDominanceType = firstSpinner.getSelectedItem().toString();

        if (selectedDominanceType.equals("Multiple Alleles")) {
            // Handling for Blood Type (Multiple Alleles)
            genotypes = new String[]{
                    "Homozygous Type A Blood (IA IA)",
                    "Heterozygous Type A Blood (IA i)",
                    "Homozygous Type B Blood (IB IB)",
                    "Heterozygous Type B Blood (IB i)",
                    "Type AB Blood (IA IB)",
                    "Type O Blood (ii)"
            };

            // Since we only need one image per genotype for blood, we use a custom logic
            GenotypeSpinnerAdapter parentOneAdapter = new GenotypeSpinnerAdapter(this, genotypes, new int[]{
                    R.drawable.multiple_alleles_blood_type_a, // For IAIA
                    R.drawable.multiple_alleles_blood_type_a, // For IAi
                    R.drawable.multiple_alleles_blood_type_b, // For IBIB
                    R.drawable.multiple_alleles_blood_type_b, // For IBi
                    R.drawable.multiple_alleles_blood_type_ab, // For IAIB
                    R.drawable.multiple_alleles_blood_type_o  // For ii
            });

            // Set up both parent spinners for blood type
            parentOneSpinner.setAdapter(parentOneAdapter);
            parentTwoSpinner.setAdapter(parentOneAdapter); // Use the same adapter for both spinners

        } else if (selectedDominanceType.equals("Codominance")) {
            // Handling for Codominance
            if (category.equals("Flower")) {
                genotypes = new String[]{"RR", "RW", "WW"}; // Codominance Flower genotypes
                images = new int[]{R.drawable.red_flower_rr, R.drawable.codominance_red_and_white_flower, R.drawable.white_flower_ww}; // Codominance flower images
            } else if (category.equals("Dog")) {
                genotypes = new String[]{"BB", "BW", "WW"}; // Codominance Dog genotypes
                images = new int[]{R.drawable.black_dog_bb, R.drawable.codominance_black_and_white_dog, R.drawable.white_dog_ww}; // Codominance dog images
            }

            // Set up both parent spinners with the codominance data
            if (genotypes != null && images != null) {
                GenotypeSpinnerAdapter parentOneAdapter = new GenotypeSpinnerAdapter(this, genotypes, images);
                parentOneSpinner.setAdapter(parentOneAdapter);

                GenotypeSpinnerAdapter parentTwoAdapter = new GenotypeSpinnerAdapter(this, genotypes, images);
                parentTwoSpinner.setAdapter(parentTwoAdapter);
            }

        } else {
            // Handling for Incomplete Dominance (existing logic)
            genotypes = genotypeMap.get(category);
            images = imageMap.get(category);

            // Set up both parent spinners for incomplete dominance
            if (genotypes != null && images != null) {
                GenotypeSpinnerAdapter parentOneAdapter = new GenotypeSpinnerAdapter(this, genotypes, images);
                parentOneSpinner.setAdapter(parentOneAdapter);

                GenotypeSpinnerAdapter parentTwoAdapter = new GenotypeSpinnerAdapter(this, genotypes, images);
                parentTwoSpinner.setAdapter(parentTwoAdapter);
            }
        }

        // Set up Punnett square calculation listeners for both spinners (applies to all cases)
        android.widget.AdapterView.OnItemSelectedListener itemSelectedListener = new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                calculatePunnettSquare(parentOneSpinner.getSelectedItem().toString(), parentTwoSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        };

        parentOneSpinner.setOnItemSelectedListener(itemSelectedListener);
        parentTwoSpinner.setOnItemSelectedListener(itemSelectedListener);
    }


    // Helper method to extract alleles for blood types
    private char[] getBloodTypeAlleles(String parent) {
        switch (parent) {
            case "Homozygous Type A Blood (IA IA)":
                return new char[]{'A', 'A'};
            case "Heterozygous Type A Blood (IA i)":
                return new char[]{'A', 'i'};
            case "Homozygous Type B Blood (IB IB)":
                return new char[]{'B', 'B'};
            case "Heterozygous Type B Blood (IB i)":
                return new char[]{'B', 'i'};
            case "Type AB Blood (IA IB)":
                return new char[]{'A', 'B'};
            case "Type O Blood (ii)":
                return new char[]{'i', 'i'};
            default:
                return null;
        }
    }


    // Helper method to sort genotypes alphabetically (e.g., "AB" -> "AB", "BA" -> "AB")
    private String sortGenotype(String genotype) {
        char[] alleles = genotype.toCharArray();
        java.util.Arrays.sort(alleles);
        return new String(alleles);
    }
    private void normalize(String allele, TextView pAllele){
        if(allele.equals("A")){
            pAllele.setText("IA");
        } else if(allele.equals("B")){
            pAllele.setText("IB");
        }else if(allele.equals("Ai")){
            pAllele.setText("IA i");
        }else  if(allele.equals("Bi")){
            pAllele.setText("IB i");

        }else

        {
            pAllele.setText(allele);

        }
    }
    private void calculatePunnettSquare(String parent1, String parent2) {
        clearPunnettGrid();

        String selectedCategory = categorySpinner.getSelectedItem().toString();
        String selectedDominanceType = firstSpinner.getSelectedItem().toString();

        // Handle Multiple Alleles (Blood Type)
        if (selectedDominanceType.equals("Multiple Alleles") && selectedCategory.equals("Blood")) {


            char[] parent1Alleles = getBloodTypeAlleles(parent1);
            char[] parent2Alleles = getBloodTypeAlleles(parent2);

            if (parent1Alleles != null && parent1Alleles.length == 2 && parent2Alleles != null && parent2Alleles.length == 2) {
                String offspring1 = "" + parent1Alleles[0] + parent2Alleles[0];
                String offspring2 = "" + parent1Alleles[0] + parent2Alleles[1];
                String offspring3 = "" + parent1Alleles[1] + parent2Alleles[0];
                String offspring4 = "" + parent1Alleles[1] + parent2Alleles[1];

                String[] offspring = {sortGenotype(offspring1), sortGenotype(offspring2), sortGenotype(offspring3), sortGenotype(offspring4)};
                TextView parent1Allele1 = findViewById(R.id.parent1_allele1);
                TextView parent1Allele2 = findViewById(R.id.parent1_allele2);
                TextView parent2Allele1 = findViewById(R.id.parent2_allele1);
                TextView parent2Allele2 = findViewById(R.id.parent2_allele2);


                normalize(String.valueOf(parent1Alleles[0]), parent1Allele1);
                normalize(String.valueOf(parent1Alleles[1]), parent1Allele2);
                normalize(String.valueOf(parent2Alleles[0]), parent2Allele1);
                normalize(String.valueOf(parent2Alleles[1]), parent2Allele2);
//                parent1Allele1.setText(String.valueOf(parent1Alleles[0]));
//                parent1Allele2.setText(String.valueOf(parent1Alleles[1]));
//                parent2Allele1.setText(String.valueOf(parent2Alleles[0]));
//                parent2Allele2.setText(String.valueOf(parent2Alleles[1]));












                updateResultsForBlood(offspring);
            } else {
                // Handle error if alleles are missing or incorrect
                Toast.makeText(ElaborateActivity.this, "Error: Parent alleles are missing or invalid", Toast.LENGTH_SHORT).show();
            }

            return;
        }

        // Handle other cases (Codominance, Incomplete Dominance)
        if (parent1.length() == 2 && parent2.length() == 2) {
            char p1_allele1 = parent1.charAt(0);
            char p1_allele2 = parent1.charAt(1);
            char p2_allele1 = parent2.charAt(0);
            char p2_allele2 = parent2.charAt(1);

            // Set the parent alleles in the UI
            TextView parent1Allele1 = findViewById(R.id.parent1_allele1);
            TextView parent1Allele2 = findViewById(R.id.parent1_allele2);
            TextView parent2Allele1 = findViewById(R.id.parent2_allele1);
            TextView parent2Allele2 = findViewById(R.id.parent2_allele2);

            parent1Allele1.setText(String.valueOf(p1_allele1));
            parent1Allele2.setText(String.valueOf(p1_allele2));
            parent2Allele1.setText(String.valueOf(p2_allele1));
            parent2Allele2.setText(String.valueOf(p2_allele2));

            // Calculate offspring genotypes
            String offspring1 = "" + p1_allele1 + p2_allele1;
            String offspring2 = "" + p1_allele1 + p2_allele2;
            String offspring3 = "" + p1_allele2 + p2_allele1;
            String offspring4 = "" + p1_allele2 + p2_allele2;

            // Create an array of offspring genotypes
            String[] offspring = {offspring1, offspring2, offspring3, offspring4};

            // Call updateResults to set the genotype, phenotype, and ratios
            updateResults(offspring);

            // Handle specific cases based on the selected dominance type and category
            if (selectedDominanceType.equals("Codominance")) {
                // Handle codominance cases for flowers and dogs
                if (selectedCategory.equals("Flower")) {
//                    setCodominanceFlowerImages(offspring1, cell1Image, cell1Text);
//                    setCodominanceFlowerImages(offspring2, cell2Image, cell2Text);
//                    setCodominanceFlowerImages(offspring3, cell3Image, cell3Text);
//                    setCodominanceFlowerImages(offspring4, cell4Image, cell4Text);

                    new Handler().postDelayed(() -> {
                        setCodominanceFlowerImages(offspring1, cell1Image, cell1Text);
                    }, 200);
                    new Handler().postDelayed(() -> {
                        setCodominanceFlowerImages(offspring2, cell2Image, cell2Text);
                    }, 600);
                    new Handler().postDelayed(() -> {
                        setCodominanceFlowerImages(offspring3, cell3Image, cell3Text);
                    }, 800);
                    new Handler().postDelayed(() -> {
                        setCodominanceFlowerImages(offspring4, cell4Image, cell4Text);
                    }, 1200);
                } else if (selectedCategory.equals("Dog")) {
//                    setCodominanceDogImages(offspring1, cell1Image, cell1Text);
//                    setCodominanceDogImages(offspring2, cell2Image, cell2Text);
//                    setCodominanceDogImages(offspring3, cell3Image, cell3Text);
//                    setCodominanceDogImages(offspring4, cell4Image, cell4Text);

                    new Handler().postDelayed(() -> {
                        setCodominanceDogImages(offspring1, cell1Image, cell1Text);
                    }, 200);
                    new Handler().postDelayed(() -> {
                        setCodominanceDogImages(offspring2, cell2Image, cell2Text);
                    }, 600);
                    new Handler().postDelayed(() -> {
                        setCodominanceDogImages(offspring3, cell3Image, cell3Text);
                    }, 800);
                    new Handler().postDelayed(() -> {
                        setCodominanceDogImages(offspring4, cell4Image, cell4Text);
                    }, 1200);
                }
            } else {
                // Handle Incomplete Dominance as usual
                if (selectedCategory.equals("Flower")) {
//                    setFlowerPunnettSquareImages(offspring1, cell1Image, cell1Text);
//                    setFlowerPunnettSquareImages(offspring2, cell2Image, cell2Text);
//                    setFlowerPunnettSquareImages(offspring3, cell3Image, cell3Text);
//                    setFlowerPunnettSquareImages(offspring4, cell4Image, cell4Text);
                    new Handler().postDelayed(() -> {
                        setFlowerPunnettSquareImages(offspring1, cell1Image, cell1Text);
                    }, 200);
                    new Handler().postDelayed(() -> {
                        setFlowerPunnettSquareImages(offspring2, cell2Image, cell2Text);
                    }, 600);
                    new Handler().postDelayed(() -> {
                        setFlowerPunnettSquareImages(offspring3, cell3Image, cell3Text);
                    }, 800);
                    new Handler().postDelayed(() -> {
                        setFlowerPunnettSquareImages(offspring4, cell4Image, cell4Text);
                    }, 1200);
                } else if (selectedCategory.equals("Dog")) {
//                    setDogPunnettSquareImages(offspring1, cell1Image, cell1Text);
//                    setDogPunnettSquareImages(offspring2, cell2Image, cell2Text);
//                    setDogPunnettSquareImages(offspring3, cell3Image, cell3Text);
//                    setDogPunnettSquareImages(offspring4, cell4Image, cell4Text);
                    new Handler().postDelayed(() -> {
                        setDogPunnettSquareImages(offspring1, cell1Image, cell1Text);
                    }, 200);
                    new Handler().postDelayed(() -> {
                        setDogPunnettSquareImages(offspring2, cell2Image, cell2Text);
                    }, 600);
                    new Handler().postDelayed(() -> {
                        setDogPunnettSquareImages(offspring3, cell3Image, cell3Text);
                    }, 800);
                    new Handler().postDelayed(() -> {
                        setDogPunnettSquareImages(offspring4, cell4Image, cell4Text);
                    }, 1200);
                }
            }
        }
    }



    private void setFlowerPunnettSquareImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype);
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playMutateSound();
        switch (genotype) {
            case "RR":
                imageView.setImageResource(R.drawable.red_flower_rr);
                break;
            case "RW":
            case "WR":
                textView.setText("RW");

                imageView.setImageResource(R.drawable.pink_flower_rw);
                break;
            case "WW":
                imageView.setImageResource(R.drawable.white_flower_ww);
                break;
            default:
                imageView.setImageResource(android.R.color.transparent);
                break;
        }
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeAnimation);
    }


    private void setDogPunnettSquareImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype); // Set genotype letters
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playMutateSound();
        switch (genotype) {
            case "BB":
                imageView.setImageResource(R.drawable.black_dog_bb);
                break;
            case "BW":
            case "WB":
                textView.setText("BW");
                imageView.setImageResource(R.drawable.gray_dog_bw);
                break;
            case "WW":
                imageView.setImageResource(R.drawable.white_dog_ww);
                break;
            default:
                imageView.setImageResource(android.R.color.transparent);
                break;
        }
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeAnimation);
    }


    private void clearPunnettGrid() {
        cell1Image.setImageResource(android.R.color.transparent);
        cell1Text.setText("");

        cell2Image.setImageResource(android.R.color.transparent);
        cell2Text.setText("");

        cell3Image.setImageResource(android.R.color.transparent);
        cell3Text.setText("");

        cell4Image.setImageResource(android.R.color.transparent);
        cell4Text.setText("");
    }

//    private void updateResults(String[] offspring) {
//        int countRR = 0, countRW = 0, countWW = 0; // Flower genotype counts
//        int countBB = 0, countBW = 0, countWW_dog = 0; // Dog genotype counts
//
//        String selectedCategory = categorySpinner.getSelectedItem().toString();
//
//        // Count the occurrences of each genotype based on the category
//        if (selectedCategory.equals("Flower")) {
//            for (String genotype : offspring) {
//                switch (genotype) {
//                    case "RR":
//                        countRR++;
//                        break;
//                    case "RW":
//                    case "WR": // RW and WR are the same
//                        countRW++;
//                        break;
//                    case "WW":
//                        countWW++;
//                        break;
//                }
//            }
//
//            // Build the genotype and phenotype text based on non-zero counts for Flower
//            StringBuilder genotypeText = new StringBuilder();
//            StringBuilder phenotypeText = new StringBuilder();
//
//            if (countRR > 0) {
//                genotypeText.append(countRR).append(" RR");
//                phenotypeText.append(countRR).append(" Red");
//            }
//            if (countRW > 0) {
//                if (genotypeText.length() > 0) genotypeText.append(", ");
//                if (phenotypeText.length() > 0) phenotypeText.append(", ");
//                genotypeText.append(countRW).append(" RW");
//                phenotypeText.append(countRW).append(" Pink");
//            }
//            if (countWW > 0) {
//                if (genotypeText.length() > 0) genotypeText.append(", ");
//                if (phenotypeText.length() > 0) phenotypeText.append(", ");
//                genotypeText.append(countWW).append(" WW");
//                phenotypeText.append(countWW).append(" White");
//            }
//
//            // Set Genotype and Phenotype results for Flower
//            genotypeValue.setText(genotypeText.toString());
//            phenotypeValue.setText(phenotypeText.toString());
//
//            // Build the Genotypic Ratio for Flower (only show non-zero values)
//            StringBuilder genotypicRatioText = new StringBuilder();
//            int total = countRR + countRW + countWW;
//
//            if (countRR > 0) genotypicRatioText.append((countRR * 100 / total)).append("% RR");
//            if (countRW > 0) genotypicRatioText.append(", ").append((countRW * 100 / total)).append("% RW");
//            if (countWW > 0) genotypicRatioText.append(", ").append((countWW * 100 / total)).append("% WW");
//
//            genotypicRatioValue.setText(genotypicRatioText.toString());
//
//            // Set Phenotypic Ratio for Flower
//            phenotypicRatioValue.setText(total + " Total");
//
//        } else if (selectedCategory.equals("Dog")) {
//            for (String genotype : offspring) {
//                switch (genotype) {
//                    case "BB":
//                        countBB++;
//                        break;
//                    case "BW":
//                    case "WB": // BW and WB are the same
//                        countBW++;
//                        break;
//                    case "WW":
//                        countWW_dog++;
//                        break;
//                }
//            }
//
//
//            StringBuilder genotypeText = new StringBuilder();
//            StringBuilder phenotypeText = new StringBuilder();
//
//            if (countBB > 0) {
//                genotypeText.append(countBB).append(" BB");
//                phenotypeText.append(countBB).append(" Black");
//            }
//            if (countBW > 0) {
//                if (genotypeText.length() > 0) genotypeText.append(", ");
//                if (phenotypeText.length() > 0) phenotypeText.append(", ");
//                genotypeText.append(countBW).append(" BW");
//                phenotypeText.append(countBW).append(" Gray");
//            }
//            if (countWW_dog > 0) {
//                if (genotypeText.length() > 0) genotypeText.append(", ");
//                if (phenotypeText.length() > 0) phenotypeText.append(", ");
//                genotypeText.append(countWW_dog).append(" WW");
//                phenotypeText.append(countWW_dog).append(" White");
//            }
//
//            genotypeValue.setText(genotypeText.toString());
//            phenotypeValue.setText(phenotypeText.toString());
//
//            StringBuilder genotypicRatioText = new StringBuilder();
//            int total = countBB + countBW + countWW_dog;
//
//            if (countBB > 0) genotypicRatioText.append((countBB * 100 / total)).append("% BB");
//            if (countBW > 0) genotypicRatioText.append(", ").append((countBW * 100 / total)).append("% BW");
//            if (countWW_dog > 0) genotypicRatioText.append(", ").append((countWW_dog * 100 / total)).append("% WW");
//
//            genotypicRatioValue.setText(genotypicRatioText.toString());
//            phenotypicRatioValue.setText(total + " Total");
//
//
//        }
//    }
private void updateResults(String[] offspring) {
    int countRR = 0, countRW = 0, countWW = 0; // Flower genotype counts
    int countBB = 0, countBW = 0, countWW_dog = 0; // Dog genotype counts

    String selectedCategory = categorySpinner.getSelectedItem().toString();

    // Count the occurrences of each genotype based on the category
    if (selectedCategory.equals("Flower")) {
        for (String genotype : offspring) {
            switch (genotype) {
                case "RR":
                    countRR++;
                    break;
                case "RW":
                case "WR": // RW and WR are the same
                    countRW++;
                    break;
                case "WW":
                    countWW++;
                    break;
            }
        }

        // Build the genotype and phenotype text based on non-zero counts for Flower
        StringBuilder genotypeText = new StringBuilder();
        StringBuilder phenotypeText = new StringBuilder();

        if (countRR > 0) {
            genotypeText.append(countRR).append(" RR");
            phenotypeText.append(countRR).append(" Red");
        }
        if (countRW > 0) {
            if (genotypeText.length() > 0) genotypeText.append(", ");
            if (phenotypeText.length() > 0) phenotypeText.append(", ");
            genotypeText.append(countRW).append(" RW");
            phenotypeText.append(countRW).append(" Pink");
        }
        if (countWW > 0) {
            if (genotypeText.length() > 0) genotypeText.append(", ");
            if (phenotypeText.length() > 0) phenotypeText.append(", ");
            genotypeText.append(countWW).append(" WW");
            phenotypeText.append(countWW).append(" White");
        }

        // Set Genotype and Phenotype results for Flower
        genotypeValue.setText(genotypeText.toString());
        phenotypeValue.setText(phenotypeText.toString());

        // Build the Genotypic Ratio for Flower (only show non-zero values)
        StringBuilder genotypicRatioText = new StringBuilder();
        int total = countRR + countRW + countWW;

        boolean isFirstGenotype = true;
        if (countRR > 0) {
            genotypicRatioText.append((countRR * 100 / total)).append("% RR");
            isFirstGenotype = false;
        }
        if (countRW > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append((countRW * 100 / total)).append("% RW");
            isFirstGenotype = false;
        }
        if (countWW > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append((countWW * 100 / total)).append("% WW");
        }

        genotypicRatioValue.setText(genotypicRatioText.toString());

        // Set Phenotypic Ratio for Flower
        phenotypicRatioValue.setText(total + " Total");

    } else if (selectedCategory.equals("Dog")) {
        for (String genotype : offspring) {
            switch (genotype) {
                case "BB":
                    countBB++;
                    break;
                case "BW":
                case "WB": // BW and WB are the same
                    countBW++;
                    break;
                case "WW":
                    countWW_dog++;
                    break;
            }
        }

        // Build the genotype and phenotype text based on non-zero counts for Dog
        StringBuilder genotypeText = new StringBuilder();
        StringBuilder phenotypeText = new StringBuilder();

        if (countBB > 0) {
            genotypeText.append(countBB).append(" BB");
            phenotypeText.append(countBB).append(" Black");
        }
        if (countBW > 0) {
            if (genotypeText.length() > 0) genotypeText.append(", ");
            if (phenotypeText.length() > 0) phenotypeText.append(", ");
            genotypeText.append(countBW).append(" BW");
            phenotypeText.append(countBW).append(" Gray");
        }
        if (countWW_dog > 0) {
            if (genotypeText.length() > 0) genotypeText.append(", ");
            if (phenotypeText.length() > 0) phenotypeText.append(", ");
            genotypeText.append(countWW_dog).append(" WW");
            phenotypeText.append(countWW_dog).append(" White");
        }

        genotypeValue.setText(genotypeText.toString());
        phenotypeValue.setText(phenotypeText.toString());

        StringBuilder genotypicRatioText = new StringBuilder();
        int total = countBB + countBW + countWW_dog;

        boolean isFirstGenotype = true;
        if (countBB > 0) {
            genotypicRatioText.append((countBB * 100 / total)).append("% BB");
            isFirstGenotype = false;
        }
        if (countBW > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append((countBW * 100 / total)).append("% BW");
            isFirstGenotype = false;
        }
        if (countWW_dog > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append((countWW_dog * 100 / total)).append("% WW");
        }

        genotypicRatioValue.setText(genotypicRatioText.toString());
        phenotypicRatioValue.setText(total + " Total");
    }
}


    private void setCodominanceFlowerImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype);
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playMutateSound();
        switch (genotype) {
            case "RR":
                imageView.setImageResource(R.drawable.red_flower_rr);
                break;
            case "WW":
                imageView.setImageResource(R.drawable.white_flower_ww);
                break;
            case "RW":
            case "WR":
                textView.setText("RW");
                imageView.setImageResource(R.drawable.codominance_red_and_white_flower);
                break;
            default:
                imageView.setImageResource(android.R.color.transparent);
                break;
        }
        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeAnimation);
    }

    private void setCodominanceDogImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype);
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playMutateSound();
        switch (genotype) {
            case "BB":
                imageView.setImageResource(R.drawable.black_dog_bb);
                break;
            case "WW":
                imageView.setImageResource(R.drawable.white_dog_ww);
                break;
            case "BW":
            case "WB":
                textView.setText("BW");
                imageView.setImageResource(R.drawable.codominance_black_and_white_dog);
                break;
            default:
                imageView.setImageResource(android.R.color.transparent);
                break;
        }

        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeAnimation);
    }









    private void setBloodTypePunnettSquareImages(String genotype, ImageView imageView, TextView textView) {
        SoundEffectPlayer soundPlayer = SoundEffectPlayer.getInstance(getApplicationContext());

        soundPlayer.playMutateSound();
        if(genotype.equals("AA")){
            textView.setText("IA IA");
        } else if(genotype.equals("BB")){
            textView.setText("IB IB");
        }else if(genotype.equals("Ai")){
            textView.setText("IA i");
        }else  if(genotype.equals("Bi")){
            textView.setText("IB i");

        }else if(genotype.equals("AB")){
            textView.setText("IA IB");

        }else

        {
            textView.setText(genotype);

        }

        Integer imageResource = imageMapBlood.get("Blood_" + genotype);
        if (imageResource != null) {
            imageView.setImageResource(imageResource);
        } else if(genotype.equals("ii")) {
            imageView.setImageResource(imageMapBlood.get("Blood_O"));

        }
            else {
            imageView.setImageResource(android.R.color.transparent);

            }

        Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeAnimation);
    }

//    // Display offspring genotypes in the Punnett square
//    setBloodTypePunnettSquareImages(offspring[0], cell1Image, cell1Text);
//    setBloodTypePunnettSquareImages(offspring[1], cell2Image, cell2Text);
//    setBloodTypePunnettSquareImages(offspring[2], cell3Image, cell3Text);
//    setBloodTypePunnettSquareImages(offspring[3], cell4Image, cell4Text);
    private void updateResultsForBlood(String[] offspring) {
        clearPunnettGrid();
        // Count occurrences of each genotype
        int countAA = 0, countAi = 0, countBB = 0, countBi = 0, countAB = 0, countO = 0;


//        //    // Display offspring genotypes in the Punnett square
//    setBloodTypePunnettSquareImages(offspring[0], cell1Image, cell1Text);
//    setBloodTypePunnettSquareImages(offspring[1], cell2Image, cell2Text);
//    setBloodTypePunnettSquareImages(offspring[2], cell3Image, cell3Text);
//    setBloodTypePunnettSquareImages(offspring[3], cell4Image, cell4Text);

        // Display offspring genotypes in the Punnett square with delays
        //setBloodTypePunnettSquareImages(offspring[0], cell1Image, cell1Text);
        new Handler().postDelayed(() -> setBloodTypePunnettSquareImages(offspring[0], cell1Image, cell1Text), 200);
        new Handler().postDelayed(() -> setBloodTypePunnettSquareImages(offspring[1], cell2Image, cell2Text), 600);
        new Handler().postDelayed(() -> setBloodTypePunnettSquareImages(offspring[2], cell3Image, cell3Text), 800);
        new Handler().postDelayed(() -> setBloodTypePunnettSquareImages(offspring[3], cell4Image, cell4Text), 1200);

        // Count each genotype in the offspring array
        for (String genotype : offspring) {
            String sortedGenotype = sortGenotype(genotype); // Normalize genotype

            switch (sortedGenotype) {
                case "AA":
                case "IAIA":
                    countAA++;
                    break;
                case "Ai":
                case "IAi":
                case "iIA":
                    countAi++;
                    break;
                case "BB":
                case "IBIB":
                    countBB++;
                    break;
                case "Bi":
                case "IBi":
                case "iIB":
                    countBi++;
                    break;
                case "AB":
                case "IAIB":
                case "IBIA":
                    countAB++;
                    break;
                case "ii":
                    countO++;
                    break;
            }
        }

        // Calculate the total number of offspring
        int total = countAA + countAi + countBB + countBi + countAB + countO;

        // Build the genotypic ratio string dynamically
        StringBuilder genotypicRatioText = new StringBuilder();
        boolean isFirstGenotype = true;
        if (countAA > 0) {
            genotypicRatioText.append(countAA).append(" IAIA (").append((countAA * 100 / total)).append("% IAIA)");
            isFirstGenotype = false;
        }
        if (countAi > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countAi).append(" IAi (").append((countAi * 100 / total)).append("% IAi)");
            isFirstGenotype = false;
        }
        if (countBB > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countBB).append(" IBIB (").append((countBB * 100 / total)).append("% IBIB)");
            isFirstGenotype = false;
        }
        if (countBi > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countBi).append(" IBi (").append((countBi * 100 / total)).append("% IBi)");
            isFirstGenotype = false;
        }
        if (countAB > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countAB).append(" IAIB (").append((countAB * 100 / total)).append("% IAIB)");
            isFirstGenotype = false;
        }
        if (countO > 0) {
            if (!isFirstGenotype) genotypicRatioText.append(", ");
            genotypicRatioText.append(countO).append(" ii (").append((countO * 100 / total)).append("% ii)");
        }

        // Build the phenotypic ratio string dynamically
        StringBuilder phenotypicRatioText = new StringBuilder();
        boolean isFirstPhenotype = true;
        if (countAA + countAi > 0) {
            phenotypicRatioText.append(countAA + countAi).append(" Type A (").append(((countAA + countAi) * 100 / total)).append("% Type A)");
            isFirstPhenotype = false;
        }
        if (countBB + countBi > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countBB + countBi).append(" Type B (").append(((countBB + countBi) * 100 / total)).append("% Type B)");
            isFirstPhenotype = false;
        }
        if (countAB > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countAB).append(" Type AB (").append((countAB * 100 / total)).append("% Type AB)");
            isFirstPhenotype = false;
        }
        if (countO > 0) {
            if (!isFirstPhenotype) phenotypicRatioText.append(", ");
            phenotypicRatioText.append(countO).append(" Type O (").append((countO * 100 / total)).append("% Type O)");
        }

        // Set the dynamic text for the Punnett square results
        genotypeValue.setText(Html.fromHtml(String.format("%s", genotypicRatioText.toString())));
        phenotypeValue.setText(Html.fromHtml(String.format("%s", phenotypicRatioText.toString())));

        // Set the genotype results dynamically
        StringBuilder genotypeText = new StringBuilder();
        isFirstGenotype = true;
        if (countAA > 0) {
            genotypeText.append("IAIA");
            isFirstGenotype = false;
        }
        if (countAi > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("IAi");
            isFirstGenotype = false;
        }
        if (countBB > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("IBIB");
            isFirstGenotype = false;
        }
        if (countBi > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("IBi");
            isFirstGenotype = false;
        }
        if (countAB > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("IAIB");
            isFirstGenotype = false;
        }
        if (countO > 0) {
            if (!isFirstGenotype) genotypeText.append(", ");
            genotypeText.append("ii");
        }

        // Set the phenotype results dynamically
        StringBuilder phenotypeText = new StringBuilder();
        isFirstPhenotype = true;
        if (countAA + countAi > 0) {
            phenotypeText.append("Type A");
            isFirstPhenotype = false;
        }
        if (countBB + countBi > 0) {
            if (!isFirstPhenotype) phenotypeText.append(", ");
            phenotypeText.append("Type B");
            isFirstPhenotype = false;
        }
        if (countAB > 0) {
            if (!isFirstPhenotype) phenotypeText.append(", ");
            phenotypeText.append("Type AB");
            isFirstPhenotype = false;
        }
        if (countO > 0) {
            if (!isFirstPhenotype) phenotypeText.append(", ");
            phenotypeText.append("Type O");
        }

        // Update the genotype and phenotype TextViews dynamically
        genotypicRatioValue.setText(Html.fromHtml(String.format("%s", genotypeText.toString())));
        phenotypicRatioValue.setText(Html.fromHtml(String.format("%s", phenotypeText.toString())));
    }
}
