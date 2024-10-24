package com.doublehammerstudio.haynineyan.FiveEActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
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
    private HashMap<String, Integer> imageMapBlood = new HashMap<>();

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
        int[] images = null; // Use an array for images, except for blood type (handled separately below)

        // Get the selected dominance type
        String selectedDominanceType = dominanceTypeSpinner.getSelectedItem().toString();

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

    private void calculatePunnettSquare(String parent1, String parent2) {
        clearPunnettGrid();

        String selectedCategory = categorySpinner.getSelectedItem().toString();
        String selectedDominanceType = dominanceTypeSpinner.getSelectedItem().toString();

        // Handle Multiple Alleles (Blood Type)
        if (selectedDominanceType.equals("Multiple Alleles") && selectedCategory.equals("Blood")) {


            char[] parent1Alleles = getBloodTypeAlleles(parent1);
            char[] parent2Alleles = getBloodTypeAlleles(parent2);

            if (parent1Alleles != null && parent1Alleles.length == 2 && parent2Alleles != null && parent2Alleles.length == 2) {

                Toast.makeText(ElaborateActivity.this,
                        "Parent 1 Alleles: " + parent1Alleles[0] + parent1Alleles[1] + ", " +
                                "Parent 2 Alleles: " + parent2Alleles[0] + parent2Alleles[1],
                        Toast.LENGTH_LONG).show();                String offspring1 = "" + parent1Alleles[0] + parent2Alleles[0];
                String offspring2 = "" + parent1Alleles[0] + parent2Alleles[1];
                String offspring3 = "" + parent1Alleles[1] + parent2Alleles[0];
                String offspring4 = "" + parent1Alleles[1] + parent2Alleles[1];

                String[] offspring = {sortGenotype(offspring1), sortGenotype(offspring2), sortGenotype(offspring3), sortGenotype(offspring4)};

                Toast.makeText(ElaborateActivity.this,
                        "DATACalculate" +
                        "offspring[0]: " + offspring[0] + ", " +
                                "offspring[1]: " + offspring[1] + ", " +
                                "offspring[2]: " + offspring[2] + ", " +
                                "offspring[3]: " + offspring[3], Toast.LENGTH_LONG).show();

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

    private void setBloodTypePunnettSquareImages(String genotype, ImageView imageView, TextView textView) {
        textView.setText(genotype);

        // Map the genotype to its corresponding image resource
        Integer imageResource = imageMapBlood.get("Blood_" + genotype); // Prefix genotype with "Blood_"
        if (imageResource != null) {
            imageView.setImageResource(imageResource);
        } else {
            imageView.setImageResource(android.R.color.transparent); // Default if no match is found
        }
    }

    private void updateResultsForBlood(String[] offspring) {
        // Debugging: Print offspring genotypes for verification
        Toast.makeText(ElaborateActivity.this,
                "offspring[0]: " + offspring[0] + ", " +
                        "offspring[1]: " + offspring[1] + ", " +
                        "offspring[2]: " + offspring[2] + ", " +
                        "offspring[3]: " + offspring[3],
                Toast.LENGTH_LONG).show();

        // Count occurrences of each genotype
        int countAA = 0, countAi = 0, countBB = 0, countBi = 0, countAB = 0, countO = 0;

        // Display offspring genotypes in the Punnett square
        setBloodTypePunnettSquareImages(offspring[0], cell1Image, cell1Text);
        setBloodTypePunnettSquareImages(offspring[1], cell2Image, cell2Text);
        setBloodTypePunnettSquareImages(offspring[2], cell3Image, cell3Text);
        setBloodTypePunnettSquareImages(offspring[3], cell4Image, cell4Text);

        // Count each genotype in the offspring array
        for (String genotype : offspring) {
            // Normalize the genotype (e.g., ensure IAi is treated as the same as iIA)
            String sortedGenotype = sortGenotype(genotype);

            switch (sortedGenotype) {
                case "AA":
                case "IAIA":
                    countAA++;
                    break;
                case "Ai":
                case "IAi":
                case "iIA": // Account for both orders
                    countAi++;
                    break;
                case "BB":
                case "IBIB":
                    countBB++;
                    break;
                case "Bi":
                case "IBi":
                case "iIB": // Account for both orders
                    countBi++;
                    break;
                case "AB":
                case "IAIB":
                case "IBIA": // Account for both orders
                    countAB++;
                    break;
                case "ii":
                    countO++;
                    break;
            }
        }

        // Calculate the total number of offspring
        int total = countAA + countAi + countBB + countBi + countAB + countO;

        // Build the genotypic ratio string dynamically based on the counts
        StringBuilder genotypicRatioText = new StringBuilder();
        if (countAA > 0) genotypicRatioText.append(countAA).append(" IAIA (").append((countAA * 100 / total)).append("% IAIA)");
        if (countAi > 0) genotypicRatioText.append(", ").append(countAi).append(" IAi (").append((countAi * 100 / total)).append("% IAi)");
        if (countBB > 0) genotypicRatioText.append(", ").append(countBB).append(" IBIB (").append((countBB * 100 / total)).append("% IBIB)");
        if (countBi > 0) genotypicRatioText.append(", ").append(countBi).append(" IBi (").append((countBi * 100 / total)).append("% IBi)");
        if (countAB > 0) genotypicRatioText.append(", ").append(countAB).append(" IAIB (").append((countAB * 100 / total)).append("% IAIB)");
        if (countO > 0) genotypicRatioText.append(", ").append(countO).append(" ii (").append((countO * 100 / total)).append("% ii)");

        // Build the phenotypic ratio string dynamically
        StringBuilder phenotypicRatioText = new StringBuilder();
        if (countAA + countAi > 0) phenotypicRatioText.append(countAA + countAi).append(" Type A (").append(((countAA + countAi) * 100 / total)).append("% Type A)");
        if (countBB + countBi > 0) phenotypicRatioText.append(", ").append(countBB + countBi).append(" Type B (").append(((countBB + countBi) * 100 / total)).append("% Type B)");
        if (countAB > 0) phenotypicRatioText.append(", ").append(countAB).append(" Type AB (").append((countAB * 100 / total)).append("% Type AB)");
        if (countO > 0) phenotypicRatioText.append(", ").append(countO).append(" Type O (").append((countO * 100 / total)).append("% Type O)");

        // Set the dynamic text for the Punnett square results
        genotypeValue.setText(Html.fromHtml(String.format("%s", genotypicRatioText.toString())));
        phenotypeValue.setText(Html.fromHtml(String.format("%s", phenotypicRatioText.toString())));

        // Set the genotype and phenotype results dynamically
        StringBuilder genotypeText = new StringBuilder();
        if (countAA > 0) genotypeText.append("IAIA");
        if (countAi > 0) genotypeText.append(", IAi");
        if (countBB > 0) genotypeText.append(", IBIB");
        if (countBi > 0) genotypeText.append(", IBi");
        if (countAB > 0) genotypeText.append(", IAIB");
        if (countO > 0) genotypeText.append(", ii");

        StringBuilder phenotypeText = new StringBuilder();
        if (countAA + countAi > 0) phenotypeText.append("Type A");
        if (countBB + countBi > 0) phenotypeText.append(", Type B");
        if (countAB > 0) phenotypeText.append(", Type AB");
        if (countO > 0) phenotypeText.append(", Type O");

        // Update the genotype and phenotype TextViews dynamically
        genotypicRatioValue.setText(Html.fromHtml(String.format("%s", genotypeText.toString())));
        phenotypicRatioValue.setText(Html.fromHtml(String.format("%s", phenotypeText.toString())));
    }


    // Helper method to format the genotype with superscripts
    private String formatGenotype(String genotype) {
        switch (genotype) {
            case "IAIA":
                return "I<sup>A</sup>I<sup>A</sup>";
            case "IAi":
            case "iIA":
                return "I<sup>A</sup>i";
            case "IBIB":
                return "I<sup>B</sup>I<sup>B</sup>";
            case "IBi":
            case "iIB":
                return "I<sup>B</sup>i";
            case "IAIB":
            case "IBIA":
                return "I<sup>A</sup>I<sup>B</sup>";
            case "ii":
                return "ii";
            default:
                return genotype;
        }
    }



}
