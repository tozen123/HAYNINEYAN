package com.doublehammerstudio.haynineyan.FiveEActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doublehammerstudio.haynineyan.GenotypeSpinnerAdapter;
import com.doublehammerstudio.haynineyan.R;

import java.util.HashMap;

public class ElaborateActivity extends AppCompatActivity {

    private ImageView headerImage;
    private TextView genotypeValue, phenotypeValue, genotypicRatioValue, phenotypicRatioValue;

    private Spinner dominanceTypeSpinner, categorySpinner, parentOneSpinner, parentTwoSpinner;
    private GridLayout punnettGrid;
    private ImageView cell1Image, cell2Image, cell3Image, cell4Image;
    private TextView cell1Text, cell2Text, cell3Text, cell4Text;

    // Data for spinners
    private String[] dominanceTypes = {"Incomplete Dominance", "Codominance", "Multiple Alleles"};
    private String[] categories = {"Flower", "Dog", "Blood"};
    private HashMap<String, String[]> genotypeMap = new HashMap<>();
    private HashMap<String, int[]> imageMap = new HashMap<>();

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
            dominanceTypeSpinner = findViewById(R.id.dominanceTypeSpinner);
            categorySpinner = findViewById(R.id.categorySpinner);
            parentOneSpinner = findViewById(R.id.parentOneSpinner);
            parentTwoSpinner = findViewById(R.id.parentTwoSpinner);
            punnettGrid = findViewById(R.id.punnett_square_grid);

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

            // Set up spinner styles to make font black
            ArrayAdapter<String> dominanceTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dominanceTypes);
            dominanceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dominanceTypeSpinner.setAdapter(dominanceTypeAdapter);

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

            // Separate handling for categorySpinner
            categorySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                    String selectedCategory = categories[position];
                    updateParentSpinners(selectedCategory);
                }

                @Override
                public void onNothingSelected(android.widget.AdapterView<?> parent) {}
            });

            // Separate handling for dominanceTypeSpinner
            dominanceTypeSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                    String selectedDominanceType = dominanceTypes[position];
                    if (selectedDominanceType.equals("Multiple Alleles")) {
                        // Automatically set the category to "Blood"
                        categorySpinner.setSelection(findIndexInArray(categories, "Blood"));
                    }

                    updateParentSpinners(categorySpinner.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(android.widget.AdapterView<?> parent) {}
            });

            new Handler().postDelayed(() -> {
                Animation fallAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
                headerImage.setVisibility(View.VISIBLE);
                headerImage.startAnimation(fallAnimation1);
            }, 200);

            return insets;
        });

        // Intent handling
        Intent intent = getIntent();
        String value = intent.getStringExtra("topic");

        if (value != null) {
            switch (value) {
                case "Non Mendelian":
                    // Customize UI or logic for Non-Mendelian if needed
                    break;
                case "Sex Related Inheritance":
                    // Customize UI or logic for Sex-Related Inheritance if needed
                    break;
                case "DNA":
                    // Customize UI or logic for DNA if needed
                    break;
                default:
                    break;
            }
        }
    }
    // Helper method to find the index of a value in an array
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
        int[] images = null;

        // Get the selected dominance type
        String selectedDominanceType = dominanceTypeSpinner.getSelectedItem().toString();

        // Handle Multiple Alleles case
        if (selectedDominanceType.equals("Multiple Alleles")) {
            // Set up genotype and image data for Multiple Alleles (Blood)
            genotypes = new String[]{"A", "A+", "B", "B+", "AB", "OO"};
            images = new int[]{R.drawable.multiple_alleles_blood_type_a, R.drawable.multiple_alleles_blood_type_a,
                    R.drawable.multiple_alleles_blood_type_b, R.drawable.multiple_alleles_blood_type_b,
                    R.drawable.multiple_alleles_blood_type_ab, R.drawable.multiple_alleles_blood_type_o};
        }
        // Handle Codominance case
        else if (selectedDominanceType.equals("Codominance")) {
            if (category.equals("Flower")) {
                genotypes = new String[]{"RR", "RW", "WW"}; // Codominance Flower genotypes
                images = new int[]{R.drawable.red_flower_rr, R.drawable.codominance_red_and_white_flower, R.drawable.white_flower_ww}; // Codominance flower images
            } else if (category.equals("Dog")) {
                genotypes = new String[]{"BB", "BW", "WW"}; // Codominance Dog genotypes
                images = new int[]{R.drawable.black_dog_bb, R.drawable.codominance_black_and_white_dog, R.drawable.white_dog_ww}; // Codominance dog images
            }
        }
        // Handle Incomplete Dominance case (existing logic)
        else {
            genotypes = genotypeMap.get(category);
            images = imageMap.get(category);
        }

        // If genotypes and images are found, update parent spinners
        if (genotypes != null && images != null) {
            GenotypeSpinnerAdapter parentOneAdapter = new GenotypeSpinnerAdapter(this, genotypes, images);
            parentOneSpinner.setAdapter(parentOneAdapter);

            GenotypeSpinnerAdapter parentTwoAdapter = new GenotypeSpinnerAdapter(this, genotypes, images);
            parentTwoSpinner.setAdapter(parentTwoAdapter);

            // Set up Punnett square calculation listeners for both spinners
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

        } else {
            Toast.makeText(this, "No genotypes found for selected category", Toast.LENGTH_SHORT).show();
        }
    }

    private String normalizeBloodGenotype(String genotype) {
        genotype = genotype.toUpperCase().trim(); // Convert to uppercase and remove any extra spaces

        // Normalize different forms of blood genotypes
        switch (genotype) {
            case "A+":
            case "A":
            case "IAIA":
            case "IAI":
            case "AI":
            case "AA":
            case "IA":
                return "A+";

            case "A-":
            case "IAi":
            case "Ai":
            case "IAi-":
            case "AI-":
                return "A-";

            case "B+":
            case "B":
            case "IBIB":
            case "IBI":
            case "BI":
            case "BB":
            case "IB":
                return "B+";

            case "B-":
            case "IBi":
            case "Bi":
            case "IBI-":
            case "BI-":
                return "B-";

            case "AB+":
            case "IAIB":
            case "AB":
            case "AIB":
            case "IAIB+":
                return "AB+";

            case "IAIB-":
                return "AB-";

            case "ii":
            case "OO":
            case "O":
            case "I":
            case "O+":
                return "O+";

            case "ii-":
            case "OO-":
            case "O-":
                return "O-";

            default:
                return genotype; // Return the input if it's already normalized
        }
    }
    private String normalizeOffspringGenotype(String genotype) {
        genotype = genotype.toUpperCase().trim(); // Convert to uppercase and remove any extra spaces

        // Normalize different forms of offspring genotypes
        switch (genotype) {
            // Blood type A combinations
            case "IAIA":
            case "IAI":
            case "AI":
            case "AA":
            case "A":
                return "A";

            case "IAi":
            case "Ai":
            case "A-":
                return "A-";

            // Blood type B combinations
            case "IBIB":
            case "IBI":
            case "BI":
            case "BB":
            case "B":
                return "B";

            case "IBi":
            case "Bi":
            case "B-":
                return "B-";

            // Blood type AB combinations
            case "IAIB":
            case "AB":
            case "AIB":
                return "AB";

            case "IAIB-":
            case "AB-":
                return "AB-";

            // Blood type O combinations
            case "ii":
            case "OO":
            case "O":
                return "O";

            case "O-":
            case "ii-":
            case "OO-":
                return "O-";

            default:
                return genotype; // Return the input if it's already normalized
        }
    }

    private String[] splitBloodGenotype(String genotype) {
        if (genotype.endsWith("+") || genotype.endsWith("-")) {
            return new String[]{genotype.substring(0, genotype.length() - 1), genotype.substring(genotype.length() - 1)};
        }
        return new String[]{genotype, ""}; // No Rh factor provided
    }

    private String combineBloodAndRh(String blood1, String blood2, String rh1, String rh2) {
        String blood = (blood1.equals(blood2)) ? blood1 : "AB"; // Handle AB blood type or mix blood types
        String rh = (rh1.equals("+") || rh2.equals("+")) ? "+" : "-"; // Rh+ is dominant
        return blood + rh;
    }

    // Punnett square calculation and image display
    private void calculatePunnettSquare(String parent1, String parent2) {
        // Clear the grid before displaying new combinations
        clearPunnettGrid();

        // Check which category is selected and load appropriate images and genotypes
        String selectedCategory = categorySpinner.getSelectedItem().toString();
        String selectedDominanceType = dominanceTypeSpinner.getSelectedItem().toString();

        // Handle Multiple Alleles (Blood Type)
        if (selectedDominanceType.equals("Multiple Alleles") && selectedCategory.equals("Blood")) {
            // Split the genotype into the blood type and Rh factor
            String[] p1 = splitBloodGenotype(parent1);
            String[] p2 = splitBloodGenotype(parent2);

            // Extract alleles from each parent genotype
            String p1_blood = p1[0]; // Blood type allele from parent 1
            String p1_rh = p1[1];    // Rh factor from parent 1
            String p2_blood = p2[0]; // Blood type allele from parent 2
            String p2_rh = p2[1];    // Rh factor from parent 2

            // Generate combinations for offspring
            String offspring1 = combineBloodAndRh(p1_blood, p2_blood, p1_rh, p2_rh);
            String offspring2 = combineBloodAndRh(p1_blood, p2_blood, p1_rh, p2_rh);
            String offspring3 = combineBloodAndRh(p1_blood, p2_blood, p1_rh, p2_rh);
            String offspring4 = combineBloodAndRh(p1_blood, p2_blood, p1_rh, p2_rh);

            // Create an array of offspring genotypes
            String[] offspring = {offspring1, offspring2, offspring3, offspring4};

            // Update the parent alleles in the UI
            TextView parent1Allele1 = findViewById(R.id.parent1_allele1);
            TextView parent1Allele2 = findViewById(R.id.parent1_allele2);
            TextView parent2Allele1 = findViewById(R.id.parent2_allele1);
            TextView parent2Allele2 = findViewById(R.id.parent2_allele2);

            parent1Allele1.setText(p1_blood + p1_rh);
            parent1Allele2.setText(p1_blood + p1_rh);
            parent2Allele1.setText(p2_blood + p2_rh);
            parent2Allele2.setText(p2_blood + p2_rh);

            // Call updateResults to set the genotype, phenotype, and ratios
            updateResults(offspring);

            // Update Punnett square with images and genotypes
            setBloodTypeImages(offspring1, cell1Image, cell1Text);
            setBloodTypeImages(offspring2, cell2Image, cell2Text);
            setBloodTypeImages(offspring3, cell3Image, cell3Text);
            setBloodTypeImages(offspring4, cell4Image, cell4Text);

            return; // Exit early since this is a special case
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
                    setCodominanceFlowerImages(offspring1, cell1Image, cell1Text);
                    setCodominanceFlowerImages(offspring2, cell2Image, cell2Text);
                    setCodominanceFlowerImages(offspring3, cell3Image, cell3Text);
                    setCodominanceFlowerImages(offspring4, cell4Image, cell4Text);
                } else if (selectedCategory.equals("Dog")) {
                    setCodominanceDogImages(offspring1, cell1Image, cell1Text);
                    setCodominanceDogImages(offspring2, cell2Image, cell2Text);
                    setCodominanceDogImages(offspring3, cell3Image, cell3Text);
                    setCodominanceDogImages(offspring4, cell4Image, cell4Text);
                }
            } else {
                // Handle Incomplete Dominance as usual
                if (selectedCategory.equals("Flower")) {
                    setFlowerPunnettSquareImages(offspring1, cell1Image, cell1Text);
                    setFlowerPunnettSquareImages(offspring2, cell2Image, cell2Text);
                    setFlowerPunnettSquareImages(offspring3, cell3Image, cell3Text);
                    setFlowerPunnettSquareImages(offspring4, cell4Image, cell4Text);
                } else if (selectedCategory.equals("Dog")) {
                    setDogPunnettSquareImages(offspring1, cell1Image, cell1Text);
                    setDogPunnettSquareImages(offspring2, cell2Image, cell2Text);
                    setDogPunnettSquareImages(offspring3, cell3Image, cell3Text);
                    setDogPunnettSquareImages(offspring4, cell4Image, cell4Text);
                }
            }
        }
    }


    private void setBloodTypeImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype); // Set genotype letters
        Toast.makeText(ElaborateActivity.this, genotype, Toast.LENGTH_SHORT).show();
        switch (genotype) {
            case "A+":
            case "A-":
                imageView.setImageResource(R.drawable.multiple_alleles_blood_type_a);
                textView.setText("A");
                break;

            case "B+":
            case "B-":
                imageView.setImageResource(R.drawable.multiple_alleles_blood_type_b);
                textView.setText("B");
                break;

            case "AB+":
            case "AB-":
                imageView.setImageResource(R.drawable.multiple_alleles_blood_type_ab);
                textView.setText("AB");
                break;

            case "O+":
            case "OO":
            case "OO-":
            case "OO+":
            case "O-":
                imageView.setImageResource(R.drawable.multiple_alleles_blood_type_o);
                textView.setText("O");
                break;

            default:
                imageView.setImageResource(android.R.color.transparent); // Handle missing cases
                textView.setText("");
                break;
        }
    }









    // Method to set flower Punnett square images and text
    private void setFlowerPunnettSquareImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype);

        switch (genotype) {
            case "RR":
                imageView.setImageResource(R.drawable.red_flower_rr);
                break;
            case "RW": // For RW and WR, use the same case
            case "WR":
                imageView.setImageResource(R.drawable.pink_flower_rw);
                break;
            case "WW":
                imageView.setImageResource(R.drawable.white_flower_ww);
                break;
            default:
                imageView.setImageResource(android.R.color.transparent); // Or use a placeholder image
                break;
        }
    }

    // Method to set dog Punnett square images and text
    private void setDogPunnettSquareImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype); // Set genotype letters

        switch (genotype) {
            case "BB":
                imageView.setImageResource(R.drawable.black_dog_bb);
                break;
            case "BW":
            case "WB":
                imageView.setImageResource(R.drawable.gray_dog_bw);
                break;
            case "WW":
                imageView.setImageResource(R.drawable.white_dog_ww);
                break;
            default:
                imageView.setImageResource(android.R.color.transparent); // Or use a placeholder image
                break;
        }
    }

    // Method to clear the grid view before a new combination is done
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

    private void updateResults(String[] offspring) {
        int countRR = 0, countRW = 0, countWW = 0; // Flower genotype counts
        int countBB = 0, countBW = 0, countWW_dog = 0; // Dog genotype counts
        int countA = 0, countA_ = 0, countB = 0, countB_ = 0, countAB = 0, countO = 0; // Blood type counts

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

            if (countRR > 0) genotypicRatioText.append((countRR * 100 / total)).append("% RR");
            if (countRW > 0) genotypicRatioText.append(", ").append((countRW * 100 / total)).append("% RW");
            if (countWW > 0) genotypicRatioText.append(", ").append((countWW * 100 / total)).append("% WW");

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

            if (countBB > 0) genotypicRatioText.append((countBB * 100 / total)).append("% BB");
            if (countBW > 0) genotypicRatioText.append(", ").append((countBW * 100 / total)).append("% BW");
            if (countWW_dog > 0) genotypicRatioText.append(", ").append((countWW_dog * 100 / total)).append("% WW");

            genotypicRatioValue.setText(genotypicRatioText.toString());
            phenotypicRatioValue.setText(total + " Total");

        } else if (selectedCategory.equals("Blood")) { // Multiple Alleles case for Blood types

            for (String genotype : offspring) {
                switch (genotype) {
                    case "A":
                    case "A+":
                        countA++;
                        break;
                    case "A-":
                        countA_++;
                        break;
                    case "B":
                    case "B+":
                        countB++;
                        break;
                    case "B-":
                        countB_++;
                        break;
                    case "AB":
                    case "AB-":
                    case "AB+":
                        countAB++;
                        break;
                    case "O":
                    case "OO":
                    case "OO+":
                    case "O+":
                        countO++;
                        break;
                    case "O-":
                    case "OO-":
                        countO++;
                        break;
                }
            }

            // Build the genotype and phenotype text based on non-zero counts for Blood
            StringBuilder genotypeText = new StringBuilder();
            StringBuilder phenotypeText = new StringBuilder();

            if (countA > 0) {
                genotypeText.append(countA).append(" A+");
                phenotypeText.append(countA).append(" Type A+");
            }
            if (countA_ > 0) {
                if (genotypeText.length() > 0) genotypeText.append(", ");
                if (phenotypeText.length() > 0) phenotypeText.append(", ");
                genotypeText.append(countA_).append(" A-");
                phenotypeText.append(countA_).append(" Type A-");
            }
            if (countB > 0) {
                if (genotypeText.length() > 0) genotypeText.append(", ");
                if (phenotypeText.length() > 0) phenotypeText.append(", ");
                genotypeText.append(countB).append(" B+");
                phenotypeText.append(countB).append(" Type B+");
            }
            if (countB_ > 0) {
                if (genotypeText.length() > 0) genotypeText.append(", ");
                if (phenotypeText.length() > 0) phenotypeText.append(", ");
                genotypeText.append(countB_).append(" B-");
                phenotypeText.append(countB_).append(" Type B-");
            }
            if (countAB > 0) {
                if (genotypeText.length() > 0) genotypeText.append(", ");
                if (phenotypeText.length() > 0) phenotypeText.append(", ");
                genotypeText.append(countAB).append(" AB");
                phenotypeText.append(countAB).append(" Type AB");
            }
            if (countO > 0) {
                if (genotypeText.length() > 0) genotypeText.append(", ");
                if (phenotypeText.length() > 0) phenotypeText.append(", ");
                genotypeText.append(countO).append(" O");
                phenotypeText.append(countO).append(" Type O");
            }

            genotypeValue.setText(genotypeText.toString());
            phenotypeValue.setText(phenotypeText.toString());

            // Build the Genotypic Ratio for Blood
            StringBuilder genotypicRatioText = new StringBuilder();
            int total = countA + countA_ + countB + countB_ + countAB + countO;

            if (countA > 0) genotypicRatioText.append((countA * 100 / total)).append("% A+");
            if (countA_ > 0) genotypicRatioText.append(", ").append((countA_ * 100 / total)).append("% A-");
            if (countB > 0) genotypicRatioText.append(", ").append((countB * 100 / total)).append("% B+");
            if (countB_ > 0) genotypicRatioText.append(", ").append((countB_ * 100 / total)).append("% B-");
            if (countAB > 0) genotypicRatioText.append(", ").append((countAB * 100 / total)).append("% AB");
            if (countO > 0) genotypicRatioText.append(", ").append((countO * 100 / total)).append("% O");

            genotypicRatioValue.setText(genotypicRatioText.toString());
            phenotypicRatioValue.setText(total + " Total");
        }
    }


    // Method to set codominance flower images and text
    private void setCodominanceFlowerImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype); // Set genotype letters

        switch (genotype) {
            case "RR":
                // Homozygous red flower in codominance
                imageView.setImageResource(R.drawable.red_flower_rr);
                break;
            case "WW":
                // Homozygous white flower in codominance
                imageView.setImageResource(R.drawable.white_flower_ww);
                break;
            case "RW": // RW and WR are the same
            case "WR":
                // Codominance case where both red and white are expressed
                imageView.setImageResource(R.drawable.codominance_red_and_white_flower);
                break;
            default:
                imageView.setImageResource(android.R.color.transparent); // Or use a placeholder image
                break;
        }
    }

    // Method to set codominance dog images and text
    private void setCodominanceDogImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype); // Set genotype letters

        switch (genotype) {
            case "BB":
                // Homozygous black dog in codominance
                imageView.setImageResource(R.drawable.black_dog_bb);
                break;
            case "WW":
                // Homozygous white dog in codominance
                imageView.setImageResource(R.drawable.white_dog_ww);
                break;
            case "BW": // BW and WB are the same
            case "WB":
                // Codominance case where both black and white are expressed
                imageView.setImageResource(R.drawable.codominance_black_and_white_dog);
                break;
            default:
                imageView.setImageResource(android.R.color.transparent); // Or use a placeholder image
                break;
        }
    }



}
