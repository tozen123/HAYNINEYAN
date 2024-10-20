package com.doublehammerstudio.haynineyan.FiveEActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doublehammerstudio.haynineyan.Question;
import com.doublehammerstudio.haynineyan.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EvaluateActivity extends AppCompatActivity {
    private ImageView headerImage;
    private TextView questionTextView;
    private LinearLayout choicesLayout, questionHolderBox;
    private TextView tapToNext;
    private int currentQuestionIndex = 0;
    private boolean isDialogShown = false;
    private int correctAnswers = 0;

    private String value, difficulty;
    private TextView remainingTextView;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_evaluate);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            headerImage = findViewById(R.id.headerImage);

            Intent intent = getIntent();
            value = intent.getStringExtra("topic");
             difficulty = intent.getStringExtra("difficulty");

            if (value != null) {
                switch (value) {
                    case "Non Mendelian":
                        switch (difficulty){

                            case "Easy":
                                // Full list of 20 questions
                                List<Question> allQuestions = Arrays.asList(
                                        new Question("What is the outcome of incomplete dominance in red and white four o’clock plants?",
                                                Arrays.asList("Red flowers", "Pink flowers", "White flowers", "Yellow flowers"),
                                                "Pink flowers",
                                                "Explanation: In incomplete dominance, neither allele is fully dominant, resulting in an intermediate phenotype, which in this case is pink."
                                        ),
                                        new Question("What is codominance?",
                                                Arrays.asList("Both alleles blend together.", "One allele is dominant over the other.", "Both alleles are fully expressed.", "Neither allele is expressed."),
                                                "Both alleles are fully expressed.",
                                                "Explanation: In codominance, both alleles in a heterozygous individual are fully expressed, such as in AB blood type."
                                        ),
                                        new Question("Which of the following is an example of codominance?",
                                                Arrays.asList("Roan cattle", "Pink flowers", "Tall and short plants", "Green peas"),
                                                "Roan cattle",
                                                "Explanation: Roan cattle display both red and white fur equally due to codominance."
                                        ),
                                        new Question("What is the phenotypic result of a cross between a red-flowered plant (RR) and a white-flowered plant (WW) showing incomplete dominance?",
                                                Arrays.asList("Red", "White", "Pink", "Red and White"),
                                                "Pink",
                                                "Explanation: In incomplete dominance, a red and white cross results in pink flowers."
                                        ),
                                        new Question("In the ABO blood system, what alleles are codominant?",
                                                Arrays.asList("A and O", "B and O", "A and B", "O and A"),
                                                "A and B",
                                                "Explanation: A and B are codominant alleles in the ABO blood system, meaning both are fully expressed if present."
                                        ),
                                        new Question("What type of inheritance is displayed when neither allele is dominant or recessive, and both are visible?",
                                                Arrays.asList("Codominance", "Incomplete dominance", "Multiple alleles", "Polygenic inheritance"),
                                                "Codominance",
                                                "Explanation: Codominance occurs when both alleles are equally and fully expressed in a heterozygote."
                                        ),
                                        new Question("Which genotype corresponds to type O blood?",
                                                Arrays.asList("IAIA", "IBi", "IAIB", "ii"),
                                                "ii",
                                                "Explanation: Type O blood is the result of two recessive alleles (ii)."
                                        ),
                                        new Question("In codominance, the offspring of a red and white cattle would have:",
                                                Arrays.asList("Red fur", "White fur", "Roan fur", "Black fur"),
                                                "Roan fur",
                                                "Explanation: Roan fur is the result of both red and white alleles being expressed equally."
                                        ),
                                        new Question("What is the expected phenotypic ratio in incomplete dominance of two heterozygous parents?",
                                                Arrays.asList("3:1", "1:2:1", "9:3:3:1", "4:0"),
                                                "1:2:1",
                                                "Explanation: The phenotypic ratio in incomplete dominance is 1:2:1 because heterozygous individuals show a blend of the two traits."
                                        ),
                                        new Question("Which of the following blood types could a person with genotype IAi have?",
                                                Arrays.asList("Type A", "Type B", "Type AB", "Type O"),
                                                "Type A",
                                                "Explanation: The genotype IAi results in type A blood because the A allele is dominant over the O allele."
                                        ),
                                        new Question("What kind of inheritance pattern involves more than two possible alleles for a gene?",
                                                Arrays.asList("Polygenic inheritance", "Multiple alleles", "Incomplete dominance", "Codominance"),
                                                "Multiple alleles",
                                                "Explanation: Multiple alleles refer to more than two alleles controlling a single trait, such as the ABO blood group."
                                        ),
                                        new Question("Which flower color pattern results from incomplete dominance between red and white snapdragons?",
                                                Arrays.asList("Red", "White", "Pink", "Green"),
                                                "Pink",
                                                "Explanation: In snapdragons, a cross between red and white flowers produces pink flowers due to incomplete dominance."
                                        ),
                                        new Question("What does codominance mean for the ABO blood group system?",
                                                Arrays.asList("Only A alleles are dominant.", "Only O alleles are recessive.", "Both A and B alleles are expressed when inherited together.", "A and B alleles are blended into a new trait."),
                                                "Both A and B alleles are expressed when inherited together.",
                                                "Explanation: In the ABO blood group, A and B alleles are codominant, meaning both are expressed equally when inherited together."
                                        ),
                                        new Question("In incomplete dominance, the genotype Rr produces what phenotype in a red and white flower cross?",
                                                Arrays.asList("Red", "White", "Pink", "Yellow"),
                                                "Pink",
                                                "Explanation: In incomplete dominance, the heterozygous genotype Rr results in an intermediate pink color."
                                        ),
                                        new Question("In codominance, how are the two alleles expressed?",
                                                Arrays.asList("They blend together.", "Only one allele is expressed.", "Both alleles are fully expressed.", "Neither allele is expressed."),
                                                "Both alleles are fully expressed.",
                                                "Explanation: Codominance occurs when both alleles are equally and fully expressed, as seen in roan cattle."
                                        ),
                                        new Question("A roan cow has a mixture of red and white hairs due to what type of inheritance?",
                                                Arrays.asList("Incomplete dominance", "Codominance", "Multiple alleles", "Polygenic inheritance"),
                                                "Codominance",
                                                "Explanation: In codominance, both red and white alleles are expressed equally, resulting in the roan fur color pattern."
                                        ),
                                        new Question("What blood type results from the genotype IAIB?",
                                                Arrays.asList("Type A", "Type B", "Type AB", "Type O"),
                                                "Type AB",
                                                "Explanation: The IAIB genotype results in type AB blood, where both A and B alleles are expressed equally."
                                        ),
                                        new Question("What is the genotype of a person with type O blood?",
                                                Arrays.asList("IAIA", "IBIB", "IAi", "ii"),
                                                "ii",
                                                "Explanation: Type O blood is the result of two recessive alleles, ii, which do not express A or B antigens."
                                        ),
                                        new Question("In incomplete dominance, a cross between a red and a white flower results in offspring that are:",
                                                Arrays.asList("Red", "White", "Pink", "Red and white"),
                                                "Pink",
                                                "Explanation: In incomplete dominance, the offspring show a blend of both parent traits, resulting in pink flowers."
                                        ),
                                        new Question("Which genotype would result in roan cattle?",
                                                Arrays.asList("RR", "WW", "RW", "rr"),
                                                "RW",
                                                "Explanation: The RW genotype results in roan cattle, where both red and white alleles are equally expressed."
                                        )
                                );

                                Collections.shuffle(allQuestions);
                                questions = allQuestions.subList(0, 10);

                                break;



                            case "Average":
                                // Full list of 20 average questions
                                List<Question> allAverageQuestions = Arrays.asList(
                                        new Question("In incomplete dominance, what is the expected phenotypic ratio in the F2 generation of a monohybrid cross?",
                                                Arrays.asList("9:3:3:1", "3:1", "1:2:1", "2:1"),
                                                "1:2:1",
                                                "Explanation: In incomplete dominance, the phenotypic ratio in the F2 generation is 1:2:1 because the heterozygous individuals exhibit a blended phenotype."
                                        ),
                                        new Question("In codominance, what happens to the phenotype of a heterozygous individual?",
                                                Arrays.asList("One allele is completely dominant.", "The traits blend together.", "Both traits are expressed equally.", "Neither allele is expressed."),
                                                "Both traits are expressed equally.",
                                                "Explanation: In codominance, both alleles contribute equally to the phenotype without blending, as in the case of roan cattle or AB blood type."
                                        ),
                                        new Question("A person with IAIB genotype will have which blood type?",
                                                Arrays.asList("Type A", "Type B", "Type AB", "Type O"),
                                                "Type AB",
                                                "Explanation: The genotype IAIB results in type AB blood, where both A and B alleles are expressed."
                                        ),
                                        new Question("Which of the following best describes codominance?",
                                                Arrays.asList("The traits blend together.", "Both alleles are equally expressed in the phenotype.", "One allele is recessive.", "One allele is completely dominant."),
                                                "Both alleles are equally expressed in the phenotype.",
                                                "Explanation: In codominance, both alleles are equally and fully expressed in the phenotype of the heterozygote."
                                        ),
                                        new Question("Which example best illustrates incomplete dominance?",
                                                Arrays.asList("Red and white flowers producing pink offspring.", "Red and white cattle producing roan offspring.", "Type AB blood being inherited.", "Green and yellow peas producing green peas."),
                                                "Red and white flowers producing pink offspring.",
                                                "Explanation: Incomplete dominance occurs when red and white flowers produce pink offspring, as neither allele is completely dominant."
                                        ),
                                        new Question("Which of the following traits is controlled by multiple alleles?",
                                                Arrays.asList("Height", "ABO blood type", "Eye color", "Skin color"),
                                                "ABO blood type",
                                                "Explanation: The ABO blood type system is controlled by multiple alleles (IA, IB, and i), resulting in four possible phenotypes (A, B, AB, O)."
                                        ),
                                        new Question("In codominance, what would the offspring of a cross between a red (RR) and a white (WW) cow look like?",
                                                Arrays.asList("All red", "All white", "Roan", "Pink"),
                                                "Roan",
                                                "Explanation: In codominance, the offspring of red and white cattle would have roan fur, with both red and white hairs equally expressed."
                                        ),
                                        new Question("How many possible phenotypes are there in the ABO blood group system?",
                                                Arrays.asList("2", "3", "4", "5"),
                                                "4",
                                                "Explanation: There are four possible phenotypes in the ABO blood group system: A, B, AB, and O."
                                        ),
                                        new Question("What is the difference between incomplete dominance and codominance?",
                                                Arrays.asList("Codominance blends the alleles, while incomplete dominance expresses both equally.", "Incomplete dominance expresses a blend, while codominance expresses both traits equally.", "Incomplete dominance shows only the dominant allele, while codominance shows the recessive allele.", "There is no difference."),
                                                "Incomplete dominance expresses a blend, while codominance expresses both traits equally.",
                                                "Explanation: In incomplete dominance, the heterozygote shows a blend of traits, while in codominance, both traits are expressed without blending."
                                        ),
                                        new Question("If a heterozygous flower exhibits pink flowers due to incomplete dominance, what are its alleles?",
                                                Arrays.asList("RR", "WW", "RW", "rr"),
                                                "RW",
                                                "Explanation: In incomplete dominance, the heterozygous genotype (RW) results in a blend of the red and white phenotypes, producing pink flowers."
                                        ),
                                        new Question("What is the genotypic ratio for a monohybrid cross showing codominance?",
                                                Arrays.asList("3:1", "9:3:3:1", "1:2:1", "4:0"),
                                                "1:2:1",
                                                "Explanation: The genotypic ratio in codominance is 1:2:1, as both alleles are expressed equally in the heterozygous condition."
                                        ),
                                        new Question("If a red-flowered plant (RR) is crossed with a white-flowered plant (WW) and the offspring are pink, what type of inheritance is being displayed?",
                                                Arrays.asList("Complete dominance", "Incomplete dominance", "Codominance", "Multiple alleles"),
                                                "Incomplete dominance",
                                                "Explanation: The offspring show incomplete dominance because the red and white alleles blend to create pink flowers."
                                        ),
                                        new Question("Which of the following is not an example of codominance?",
                                                Arrays.asList("ABO blood group", "Roan cattle", "Sickle-cell anemia", "Red and white flowers producing pink flowers"),
                                                "Red and white flowers producing pink flowers",
                                                "Explanation: Red and white flowers producing pink offspring is an example of incomplete dominance, not codominance."
                                        ),
                                        new Question("Which blood type is possible for a person with genotype IBi?",
                                                Arrays.asList("Type A", "Type B", "Type AB", "Type O"),
                                                "Type B",
                                                "Explanation: The IBi genotype results in type B blood."
                                        ),
                                        new Question("In codominance, how many distinct phenotypes can be produced in the F2 generation of a monohybrid cross?",
                                                Arrays.asList("1", "2", "3", "4"),
                                                "3",
                                                "Explanation: In codominance, three phenotypes can result: homozygous dominant, heterozygous (showing both traits), and homozygous recessive."
                                        ),
                                        new Question("In incomplete dominance, what is the expected genotypic ratio in the F2 generation of a monohybrid cross?",
                                                Arrays.asList("3:1", "1:2:1", "9:3:3:1", "2:1"),
                                                "1:2:1",
                                                "Explanation: The genotypic ratio in incomplete dominance is 1:2:1, with heterozygous individuals showing a blend of both parental traits."
                                        ),
                                        new Question("What is an example of codominance?",
                                                Arrays.asList("Color blindness", "Black and White Checkered feathers", "Red, Yellow, and Orange Flowers", "Skin color"),
                                                "Black and White Checkered feathers",
                                                "Explanation: The checkered feathers exhibit both white and black traits showing traits are expressed equally."
                                        ),
                                        new Question("A roan cow has both red and white hairs. This is an example of what inheritance pattern?",
                                                Arrays.asList("Codominance", "Incomplete dominance", "Multiple alleles", "Polygenic inheritance"),
                                                "Codominance",
                                                "Explanation: In codominance, both the red and white alleles are expressed equally, resulting in roan fur with both red and white hairs."
                                        ),
                                        new Question("If a red and white flower produces pink offspring, what is this an example of?",
                                                Arrays.asList("Codominance", "Incomplete dominance", "Complete dominance", "Recessive inheritance"),
                                                "Incomplete dominance",
                                                "Explanation: Pink flowers are the result of incomplete dominance, where the two parental alleles blend in the heterozygote."
                                        ),
                                        new Question("Which of the following traits does not exhibit incomplete dominance?",
                                                Arrays.asList("Snapdragon flower color", "Human height", "Four o’clock flower color", "Andalusian chicken feather color"),
                                                "Human height",
                                                "Explanation: Human height is an example of polygenic inheritance, not incomplete dominance, which is found in snapdragon and four o’clock flowers."
                                        )
                                );

                                Collections.shuffle(allAverageQuestions);
                                questions = allAverageQuestions.subList(0, 10);  // Select 10 random questions

                                break;

                            case "Difficult":
                                List<Question> allDifficultQuestions = Arrays.asList(
                                        new Question("How does codominance differ from incomplete dominance in terms of molecular expression?",
                                                Arrays.asList("In codominance, both alleles are fully expressed at the molecular level, while in incomplete dominance, the protein product is a blend.",
                                                        "In incomplete dominance, both alleles are fully expressed, while in codominance, only one allele is expressed.",
                                                        "Both involve the expression of recessive alleles.",
                                                        "Codominance involves a blend of traits, while incomplete dominance shows both traits distinctly."),
                                                "In codominance, both alleles are fully expressed at the molecular level, while in incomplete dominance, the protein product is a blend.",
                                                "Explanation: In codominance, both alleles are fully expressed at the molecular level, while in incomplete dominance, the protein products of both alleles blend to produce an intermediate phenotype."
                                        ),
                                        new Question("If two heterozygous roan cattle (RW) are crossed, what percentage of their offspring would be expected to have roan fur?",
                                                Arrays.asList("25%", "50%", "75%", "100%"),
                                                "50%",
                                                "Explanation: A cross between two heterozygous roan cattle (RW) would result in a 1:2:1 ratio of red, roan, and white fur, with 50% of the offspring having roan fur."
                                        ),
                                        new Question("How does incomplete dominance affect the genotypic and phenotypic ratios in the F2 generation?",
                                                Arrays.asList("They are always different.",
                                                        "They are always the same.",
                                                        "Genotypic ratio is 3:1, while phenotypic ratio is 1:2:1.",
                                                        "Genotypic ratio is 9:3:3:1, while phenotypic ratio is 3:1."),
                                                "They are always the same.",
                                                "Explanation: In incomplete dominance, the genotypic and phenotypic ratios are both 1:2:1, as heterozygotes show a blend of both parental traits."
                                        ),
                                        new Question("What is the phenotypic result of a cross between a homozygous red-flowered plant (RR) and a heterozygous pink-flowered plant (RW)?",
                                                Arrays.asList("All red",
                                                        "50% red, 50% white",
                                                        "50% red, 50% pink",
                                                        "All pink"),
                                                "50% red, 50% pink",
                                                "Explanation: In this cross, 50% of the offspring would be red (RR), and 50% would be pink (RW) due to incomplete dominance."
                                        ),
                                        new Question("What kind of molecular evidence would you expect to find in codominance that you wouldn’t find in incomplete dominance?",
                                                Arrays.asList("Evidence that both alleles are fully transcribed and translated into functional proteins.",
                                                        "Evidence that both alleles blend into a single protein.",
                                                        "Evidence that one allele is silenced.",
                                                        "Evidence of alternative splicing."),
                                                "Evidence that both alleles are fully transcribed and translated into functional proteins.",
                                                "Explanation: In codominance, both alleles are fully expressed at the molecular level, meaning both are transcribed and translated into functional proteins that are fully expressed in the phenotype."
                                        ),
                                        new Question("In multiple alleles, how many alleles control a single trait?",
                                                Arrays.asList("Only two alleles",
                                                        "Three or more alleles",
                                                        "One dominant and one recessive allele",
                                                        "Four alleles"),
                                                "Three or more alleles",
                                                "Explanation: Multiple alleles refer to a situation where more than two alleles are involved in determining a single trait, such as in the ABO blood group system, which has three alleles (IA, IB, i)."
                                        ),
                                        new Question("A person with genotype IAIB has which type of blood, and why?",
                                                Arrays.asList("Type A, because IA is dominant.",
                                                        "Type AB, because both IA and IB are codominant.",
                                                        "Type O, because both alleles are recessive.",
                                                        "Type B, because IB is dominant over IA."),
                                                "Type AB, because both IA and IB are codominant.",
                                                "Explanation: A person with IAIB has type AB blood because both the A and B alleles are codominant, and both are expressed equally in the phenotype."
                                        ),
                                        new Question("In a population where codominance is present, what happens to heterozygous individuals at the molecular level?",
                                                Arrays.asList("Both alleles are silenced.",
                                                        "One allele dominates over the other.",
                                                        "Both alleles are expressed in separate regions of the organism.",
                                                        "The alleles blend together into one trait."),
                                                "Both alleles are expressed in separate regions of the organism.",
                                                "Explanation: In codominance, both alleles are expressed equally but can be found in distinct regions or aspects of the organism, such as red and white hairs in roan cattle."
                                        ),
                                        new Question("If two pink-flowered plants (RW) are crossed, and a red-flowered plant (RR) is produced, what is the probability that another offspring will be pink?",
                                                Arrays.asList("25%", "50%", "75%", "100%"),
                                                "50%",
                                                "Explanation: A cross between two heterozygous pink plants (RW) follows a 1:2:1 ratio, where 50% of the offspring will be pink (RW)."
                                        ),
                                        new Question("Why are multiple alleles important for evolution and variation within populations?",
                                                Arrays.asList("They decrease the variety of traits.",
                                                        "They allow for more combinations of genotypes and phenotypes.",
                                                        "They prevent mutations from occurring.",
                                                        "They ensure that dominant traits are always expressed."),
                                                "They allow for more combinations of genotypes and phenotypes.",
                                                "Explanation: Multiple alleles increase the potential combinations of genotypes and phenotypes, leading to greater genetic variation, which is essential for evolution and adaptation in populations."
                                        )
                                );

                                Collections.shuffle(allDifficultQuestions);
                                questions = allDifficultQuestions.subList(0, 5);  // Select 5 random questions

                                break;

                            default:
                                break;
                        }

                        break;
                    case "Sex Related Inheritance":
                        switch (difficulty) {
                            case "Easy":
                                List<Question> allSexRelatedInheritanceQuestions = Arrays.asList(
                                        new Question("What determines the sex of a human?",
                                                Arrays.asList("Autosomes", "Sex chromosomes", "Mitochondria", "Ribosomes"),
                                                "Sex chromosomes",
                                                "Explanation: The sex of a human is determined by the sex chromosomes, specifically the combination of X and Y chromosomes."
                                        ),
                                        new Question("What combination of sex chromosomes do females have?",
                                                Arrays.asList("XY", "XX", "YY", "XZ"),
                                                "XX",
                                                "Explanation: Females have two X chromosomes (XX), while males have one X and one Y chromosome (XY)."
                                        ),
                                        new Question("Males inherit their Y chromosome from which parent?",
                                                Arrays.asList("Mother", "Father", "Both parents", "Neither"),
                                                "Father",
                                                "Explanation: Males inherit the Y chromosome from their father, while they inherit the X chromosome from their mother."
                                        ),
                                        new Question("What are X-linked genes?",
                                                Arrays.asList("Genes located on the Y chromosome", "Genes located on the X chromosome", "Genes not related to sex", "Genes found on autosomes"),
                                                "Genes located on the X chromosome",
                                                "Explanation: X-linked genes are located on the X chromosome and can affect traits that are more commonly expressed in males."
                                        ),
                                        new Question("Which of the following is an example of an X-linked trait?",
                                                Arrays.asList("Hemophilia", "Height", "Skin color", "Eye color"),
                                                "Hemophilia",
                                                "Explanation: Hemophilia is an example of an X-linked trait because it is caused by a gene located on the X chromosome."
                                        ),
                                        new Question("A color-blind male has which genotype?",
                                                Arrays.asList("XCXC", "XCY", "XY", "XX"),
                                                "XCY",
                                                "Explanation: A color-blind male has the genotype XCY, where XC represents the X chromosome carrying the allele for color blindness."
                                        ),
                                        new Question("If a female is a carrier for color blindness, what is her genotype?",
                                                Arrays.asList("XcXc", "XCXC", "XCXc", "XcY"),
                                                "XCXc",
                                                "Explanation: A carrier female has one normal allele and one allele for color blindness (XCXc), allowing her to pass the trait to her offspring without expressing it herself."
                                        ),
                                        new Question("What is the probability that a son will inherit an X-linked recessive trait from his father?",
                                                Arrays.asList("25%", "50%", "0%", "100%"),
                                                "0%",
                                                "Explanation: Sons inherit the Y chromosome from their father, so they cannot inherit X-linked traits from their father."
                                        ),
                                        new Question("Which of the following traits is an example of a Y-linked trait?",
                                                Arrays.asList("Color blindness", "Hemophilia", "Hypertrichosis pinnae auris (hairy ears)", "Red-green color blindness"),
                                                "Hypertrichosis pinnae auris (hairy ears)",
                                                "Explanation: Hypertrichosis pinnae auris is a Y-linked trait, which means only males can inherit it, passed directly from father to son."
                                        ),
                                        new Question("A female with two X chromosomes carries an X-linked recessive trait. What is her likelihood of expressing the trait?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "0%",
                                                "Explanation: A female must be homozygous for the certain allele to express the trait, while those with one allele are carriers but still do not express it."
                                        ),
                                        new Question("Which of the following describes the term 'sex-linked inheritance'?",
                                                Arrays.asList("Traits that are controlled by genes on autosomes", "Traits that are linked to the X and Y chromosomes", "Traits that only appear in females", "Traits that show complete dominance"),
                                                "Traits that are linked to the X and Y chromosomes",
                                                "Explanation: Sex-linked inheritance refers to traits that are controlled by genes located on the sex chromosomes (X and Y)."
                                        ),
                                        new Question("If a color-blind female has a son, what is the probability that he will be color-blind?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "100%",
                                                "Explanation: If the mother is color-blind, each son has a 100% chance of inheriting the allele from his mother, making him color-blind."
                                        ),
                                        new Question("What is the genetic makeup of a male with normal vision whose mother is color-blind?",
                                                Arrays.asList("XCY", "XcY", "XCXC", "XcX"),
                                                "XcY",
                                                "Explanation: A male with normal vision inherits the normal allele (Xc) from his mother and the Y chromosome from his father (XcY)."
                                        ),
                                        new Question("In humans, which sex chromosome determines the male sex?",
                                                Arrays.asList("X", "Y", "Both X and Y", "None"),
                                                "Y",
                                                "Explanation: The presence of the Y chromosome determines male sex in humans."
                                        ),
                                        new Question("Which of the following statements is true regarding X-linked traits?",
                                                Arrays.asList("Males can be carriers but not express the trait.", "Females can express the trait only if they have two affected alleles.", "Both males and females can express the trait equally.", "All daughters of a color-blind father will be color-blind."),
                                                "Females can express the trait only if they have two affected alleles.",
                                                "Explanation: Females must inherit two recessive alleles (homozygous) to express an X-linked recessive trait."
                                        ),
                                        new Question("If a color-blind female (XCXC) and a normal vision male (XY) have children, what is the likelihood that their daughters will be color-blind?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "0%",
                                                "Explanation: All daughters will inherit one XC from the mother and one normal X from the father, making them carriers but not color-blind."
                                        ),
                                        new Question("Which trait is likely to be expressed more frequently in males than in females?",
                                                Arrays.asList("Sickle-cell anemia", "Color blindness", "Hemophilia", "All of the above"),
                                                "All of the above",
                                                "Explanation: All of these traits are X-linked recessive and are expressed more frequently in males because they only have one X chromosome."
                                        ),
                                        new Question("A male with a Y-linked trait will pass this trait on to:",
                                                Arrays.asList("All daughters", "All sons", "No children", "Half of his children"),
                                                "All sons",
                                                "Explanation: Y-linked traits are passed directly from father to son, so a male with a Y-linked trait will pass it on to all his sons."
                                        ),
                                        new Question("What is the result of a cross between a carrier female (XCXc) and a normal male (XY) for an X-linked trait?",
                                                Arrays.asList("50% affected males, 50% affected females", "25% affected males, 25% affected females", "25% affected males, 25% unaffected females", "50% affected males, 50% unaffected females"),
                                                "50% affected males, 50% unaffected females",
                                                "Explanation: A cross between a carrier female and a normal male will yield 50% affected males (XCY) and 50% unaffected females (XCXc)."
                                        ),
                                        new Question("Which genetic condition is more common in males due to X-linked inheritance?",
                                                Arrays.asList("Cystic fibrosis", "Color blindness", "Sickle-cell anemia", "Huntington's disease"),
                                                "Color blindness",
                                                "Explanation: Color blindness is an X-linked recessive condition that is more commonly expressed in males because they have only one X chromosome."
                                        )
                                );

                                Collections.shuffle(allSexRelatedInheritanceQuestions);
                                questions = allSexRelatedInheritanceQuestions.subList(0, 10);
                                break;

                            case "Average":
                                List<Question> allSexRelatedInheritanceAverageQuestions = Arrays.asList(
                                        new Question("How are sex-linked traits inherited differently in males and females?",
                                                Arrays.asList("Males can be carriers, but females cannot.", "Males only inherit one X chromosome.", "Females cannot express X-linked traits.", "Both A and B."),
                                                "Both A and B.",
                                                "Explanation: Males inherit only one X chromosome, meaning they cannot be carriers of X-linked traits, while females can be carriers if they have one normal allele."
                                        ),
                                        new Question("What percentage of sons born to a color-blind mother will be color-blind?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: Sons inherit their X chromosome from their mother; if the mother is color-blind (XCXC), each son has a 50% chance of being color-blind (XCY)."
                                        ),
                                        new Question("If a color-blind father (XCY) has children with a normal vision mother (XcXc), what are the chances their daughters will be color-blind?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "0%",
                                                "Explanation: All daughters will inherit one normal X chromosome from their mother and one affected X chromosome from their father, making them carriers but not color-blind."
                                        ),
                                        new Question("What does it mean to be a carrier of an X-linked trait?",
                                                Arrays.asList("The individual expresses the trait.", "The individual does not express the trait but can pass it on.", "The individual has the dominant allele.", "The individual has two recessive alleles."),
                                                "The individual does not express the trait but can pass it on.",
                                                "Explanation: A carrier has one normal allele and one affected allele, allowing them to pass on the recessive trait without expressing it themselves."
                                        ),
                                        new Question("Why are males more frequently affected by X-linked recessive disorders?",
                                                Arrays.asList("They have two X chromosomes.", "They have only one X chromosome.", "They inherit traits from both parents equally.", "They have more Y chromosomes than females."),
                                                "They have only one X chromosome.",
                                                "Explanation: Males have only one X chromosome; if it carries a recessive allele for a disorder, they will express the trait since there is no second X chromosome to mask it."
                                        ),
                                        new Question("Which trait would be least likely to be affected by sex-linked inheritance?",
                                                Arrays.asList("Hemophilia", "Red-green color blindness", "Eye color", "Duchenne muscular dystrophy"),
                                                "Eye color",
                                                "Explanation: Eye color is primarily controlled by genes on autosomes, not sex chromosomes, making it less likely to be affected by sex-linked inheritance."
                                        ),
                                        new Question("If a mother carries an X-linked recessive trait, what is the chance that a daughter will inherit it?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: Daughters have a 50% chance of inheriting the X-linked recessive trait from their carrier mother (XCXc)."
                                        ),
                                        new Question("What is the likelihood that a son will inherit a recessive X-linked disorder from a carrier mother (XCXc)?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: Sons have a 50% chance of inheriting the affected X chromosome from a carrier mother, making them color-blind or affected."
                                        ),
                                        new Question("Which trait is passed from father to son?",
                                                Arrays.asList("Color blindness", "Hemophilia", "Hypertrichosis pinnae auris", "Both A and B"),
                                                "Hypertrichosis pinnae auris",
                                                "Explanation: Hypertrichosis pinnae auris is a Y-linked trait, which can only be passed from father to son."
                                        ),
                                        new Question("How are sex-linked traits typically inherited?",
                                                Arrays.asList("In a simple dominant-recessive manner", "Through codominance", "Via the sex chromosomes", "Through polygenic inheritance"),
                                                "Via the sex chromosomes",
                                                "Explanation: Sex-linked traits are inherited through genes located on the sex chromosomes (X and Y), which can affect the expression of traits in males and females differently."
                                        ),
                                        new Question("If a male inherits an X-linked recessive trait, what is true about his mother?",
                                                Arrays.asList("She must also have the trait.", "She must be a carrier.", "She cannot be a carrier.", "She has two normal alleles."),
                                                "She must be a carrier.",
                                                "Explanation: If a male inherits an X-linked recessive trait, his mother must at least be a carrier to pass on the recessive allele."
                                        ),
                                        new Question("In a genetic cross, if a color-blind man (XCY) has children with a woman who is a carrier for color blindness (XCXc), what percentage of their daughters will be carriers?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: The daughters have a 50% chance of being carriers as they inherit one X from each parent."
                                        ),
                                        new Question("How do sex-limited traits differ from sex-linked traits?",
                                                Arrays.asList("Sex-limited traits are found only on the Y chromosome.", "Sex-limited traits are expressed in only one gender.", "Sex-linked traits are always recessive.", "Sex-limited traits affect both genders equally."),
                                                "Sex-limited traits are expressed in only one gender.",
                                                "Explanation: Sex-limited traits are expressed only in one gender, regardless of whether both genders carry the alleles."
                                        ),
                                        new Question("What is the likelihood that a daughter will inherit a Y-linked trait?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "0%",
                                                "Explanation: Daughters inherit an X chromosome from both parents, so they cannot inherit Y-linked traits that are passed from father to son."
                                        ),
                                        new Question("Which of the following represents an X-linked recessive trait?",
                                                Arrays.asList("Affected fathers cannot pass the trait to sons.", "Males are unaffected if they have one normal allele.", "Females can express the trait if they have one affected allele.", "Affected daughters must have an affected father."),
                                                "Affected fathers cannot pass the trait to sons.",
                                                "Explanation: Affected fathers pass their Y chromosome to sons and thus cannot pass X-linked traits to them."
                                        ),
                                        new Question("How is color blindness inherited in humans?",
                                                Arrays.asList("Autosomal dominant", "Autosomal recessive", "X-linked recessive", "Y-linked"),
                                                "X-linked recessive",
                                                "Explanation: Color blindness is inherited as an X-linked recessive trait, meaning males are more likely to express it due to having only one X chromosome."
                                        ),
                                        new Question("If a mother carries a Y-linked trait, what will be the result for her daughters?",
                                                Arrays.asList("All daughters will be affected.", "Daughters will inherit the trait.", "Daughters cannot inherit the trait.", "Only sons will inherit the trait."),
                                                "Daughters cannot inherit the trait.",
                                                "Explanation: Daughters inherit X chromosomes from their mothers, so they cannot inherit Y-linked traits."
                                        ),
                                        new Question("A color-blind daughter has a son. What is the chance that her son will be color-blind?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: The son will inherit one X chromosome from his mother and a Y chromosome from his father, making him color-blind."
                                        ),
                                        new Question("If a carrier female has children with a normal male, what is the probability that their son will be color-blind?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: There is a 50% chance that the son will inherit the affected allele from his mother, making him color-blind."
                                        ),
                                        new Question("What percentage of the children of a color-blind man and a carrier woman will be color-blind?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: There is a 50% chance that any son will be color-blind (XcY) and a 50% chance that any daughter will be a carrier (XCXc)."
                                        )
                                );

                                Collections.shuffle(allSexRelatedInheritanceAverageQuestions);
                                questions = allSexRelatedInheritanceAverageQuestions.subList(0, 10);
                                break;
                            case "Difficult":
                                List<Question> allSexRelatedInheritanceDifficultQuestions = Arrays.asList(
                                        new Question("A color-blind woman has a son with a normal-vision man. What percentage of their sons will be color-blind?",
                                                Arrays.asList("0%", "50%", "100%", "None of the above"),
                                                "0%",
                                                "Explanation: All sons will inherit their Y chromosome from their father and will not inherit an X chromosome from their mother (only normal X from father), so they will not be color-blind."
                                        ),
                                        new Question("In humans, what is the genetic probability that a color-blind father will pass the color-blind allele to his daughter?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: Daughters will inherit an X chromosome from their father (Xc) and a normal X from their mother, making the probability 50% for expressing color blindness."
                                        ),
                                        new Question("If a man is color-blind and his mother was a carrier, what is the probability that he inherited the color-blind allele?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: The son could inherit either X from the mother, giving him a 50% chance of being color-blind if he inherits Xc."
                                        ),
                                        new Question("How does X-linked inheritance differ from autosomal inheritance?",
                                                Arrays.asList("X-linked traits are not affected by sex.", "X-linked traits are expressed more frequently in males than females.", "X-linked inheritance only involves dominant traits.", "There is no difference."),
                                                "X-linked traits are expressed more frequently in males than females.",
                                                "Explanation: X-linked inheritance is expressed more frequently in males because they have only one X chromosome, so any recessive trait will be expressed."
                                        ),
                                        new Question("A woman is affected by an X-linked disorder and has a son with a normal-vision male. What are the chances their son will inherit the disorder?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "50%",
                                                "Explanation: Sons have a 50% chance of inheriting the affected X chromosome from their mother, making them affected."
                                        ),
                                        new Question("Which condition is typically inherited through Y-linked transmission?",
                                                Arrays.asList("Hemophilia", "Color blindness", "Male infertility", "Sickle-cell disease"),
                                                "Male infertility",
                                                "Explanation: Male infertility can be a Y-linked trait, as it is passed from father to son through the Y chromosome."
                                        ),
                                        new Question("How does the inheritance of X-linked traits challenge traditional Mendelian genetics?",
                                                Arrays.asList("X-linked traits follow Mendel’s law of segregation.", "X-linked traits can skip generations, especially in females.", "They cannot be predicted using Punnett squares.", "X-linked traits are not subject to dominance and recessiveness."),
                                                "X-linked traits can skip generations, especially in females.",
                                                "Explanation: X-linked traits can appear to skip generations in females because they may be carriers without expressing the trait if they have one normal X allele."
                                        ),
                                        new Question("In a family where the father is color-blind and the mother is a carrier, what is the probability that their daughter will be color-blind?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "0%",
                                                "Explanation: A daughter will inherit one X chromosome from her father (which will be color-blind) and one X chromosome from her mother (XC or Xc), making her a carrier but not color-blind."
                                        ),
                                        new Question("Why is it more challenging to treat X-linked disorders in males compared to females?",
                                                Arrays.asList("Males have two X chromosomes.", "Males can only inherit X-linked disorders from their mothers.", "Males are usually asymptomatic.", "Males have no normal allele to mask the effects of the disorder."),
                                                "Males have no normal allele to mask the effects of the disorder.",
                                                "Explanation: Males have only one X chromosome, so any recessive X-linked disorder will be expressed since there is no second X chromosome to mask the effect."
                                        ),
                                        new Question("If a color-blind man and a normal vision woman have children, what is the likelihood of having a color-blind daughter?",
                                                Arrays.asList("0%", "25%", "50%", "100%"),
                                                "0%",
                                                "Explanation: Daughters will inherit one normal X chromosome (XC) from their mother and the affected X chromosome (Xc) from their father, making them carriers but not color-blind."
                                        )
                                );

                                // Shuffle and select 5 questions for difficult level
                                Collections.shuffle(allSexRelatedInheritanceDifficultQuestions);
                                questions = allSexRelatedInheritanceDifficultQuestions.subList(0, 5);
                                break;

                            default:
                                break;

                        }
                        break;

                    case "DNA":
                        switch (difficulty) {
                            case "Easy":
                                // Full list of 10 easy questions
                                List<Question> allDNAEasyQuestions = Arrays.asList(
                                        new Question("What does DNA stand for?",
                                                Arrays.asList("Deoxyribonucleic acid", "Deoxyribose nucleic acid", "Dioxyribonucleic acid", "Deoxyribosomal acid"),
                                                "Deoxyribonucleic acid",
                                                "Explanation: DNA stands for Deoxyribonucleic acid, which is the molecule that carries genetic information in living organisms."
                                        ),
                                        new Question("What is the shape of the DNA molecule?",
                                                Arrays.asList("Linear", "Circular", "Double helix", "Triple helix"),
                                                "Double helix",
                                                "Explanation: The DNA molecule is structured as a double helix, resembling a twisted ladder."
                                        ),
                                        new Question("Which of the following is not a component of DNA?",
                                                Arrays.asList("Phosphate group", "Ribose sugar", "Nitrogenous bases", "Deoxyribose sugar"),
                                                "Ribose sugar",
                                                "Explanation: DNA contains deoxyribose sugar, not ribose sugar, which is found in RNA."
                                        ),
                                        new Question("Which of the following nitrogenous bases is found in DNA?",
                                                Arrays.asList("Uracil", "Thymine", "Ribose", "Adenosine"),
                                                "Thymine",
                                                "Explanation: Thymine is one of the four nitrogenous bases found in DNA, along with adenine, cytosine, and guanine."
                                        ),
                                        new Question("What is the function of DNA?",
                                                Arrays.asList("To provide energy", "To store and transmit genetic information", "To facilitate chemical reactions", "To transport nutrients"),
                                                "To store and transmit genetic information",
                                                "Explanation: The primary function of DNA is to store and transmit genetic information that guides the development and functioning of living organisms."
                                        ),
                                        new Question("How many strands does a DNA molecule have?",
                                                Arrays.asList("One", "Two", "Three", "Four"),
                                                "Two",
                                                "Explanation: A DNA molecule consists of two strands that form the double helix structure."
                                        ),
                                        new Question("What type of bond holds the nitrogenous bases together in the DNA double helix?",
                                                Arrays.asList("Covalent bonds", "Ionic bonds", "Hydrogen bonds", "Peptide bonds"),
                                                "Hydrogen bonds",
                                                "Explanation: Hydrogen bonds hold the nitrogenous bases together in the center of the DNA double helix, allowing the strands to separate during replication."
                                        ),
                                        new Question("What base pairs with adenine in DNA?",
                                                Arrays.asList("Cytosine", "Guanine", "Thymine", "Uracil"),
                                                "Thymine",
                                                "Explanation: In DNA, adenine pairs with thymine through two hydrogen bonds."
                                        ),
                                        new Question("Which scientist(s) are credited with discovering the structure of DNA?",
                                                Arrays.asList("Watson and Crick", "Mendel and Darwin", "Franklin and Wilkins", "Avery and Hershey"),
                                                "Watson and Crick",
                                                "Explanation: James Watson and Francis Crick are credited with discovering the double helix structure of DNA in 1953."
                                        ),
                                        new Question("What is the primary role of DNA replication?",
                                                Arrays.asList("To produce proteins", "To create energy", "To ensure genetic information is passed to daughter cells", "To repair cellular damage"),
                                                "To ensure genetic information is passed to daughter cells",
                                                "Explanation: The primary role of DNA replication is to ensure that genetic information is accurately passed on to daughter cells during cell division."
                                        )
                                );

                                Collections.shuffle(allDNAEasyQuestions);
                                questions = allDNAEasyQuestions.subList(0, 5);  // Select 5 random questions

                                break;
                            case "DNA":
                                switch (difficulty) {
                                    case "Easy":
                                        // Already implemented Easy questions (from previous example)
                                        break;

                                    case "Average":
                                        // Full list of 10 average questions
                                        List<Question> allDNAAverageQuestions = Arrays.asList(
                                                new Question("What is the basic unit of DNA called?",
                                                        Arrays.asList("Nucleotide", "Amino acid", "Chromosome", "Gene"),
                                                        "Nucleotide",
                                                        "Explanation: The basic unit of DNA is the nucleotide, which consists of a phosphate group, a deoxyribose sugar, and a nitrogenous base."
                                                ),
                                                new Question("What are the four nitrogenous bases found in DNA?",
                                                        Arrays.asList("Adenine, guanine, cytosine, thymine", "Adenine, uracil, cytosine, thymine", "Adenine, guanine, cytosine, uracil", "Cytosine, thymine, ribose, adenine"),
                                                        "Adenine, guanine, cytosine, thymine",
                                                        "Explanation: The four nitrogenous bases in DNA are adenine (A), guanine (G), cytosine (C), and thymine (T)."
                                                ),
                                                new Question("How does DNA replicate itself?",
                                                        Arrays.asList("By splitting in half and forming new strands", "By using RNA as a template", "By mitosis", "By fusing with proteins"),
                                                        "By splitting in half and forming new strands",
                                                        "Explanation: DNA replicates by unwinding and splitting the double helix, where each strand serves as a template for a new complementary strand."
                                                ),
                                                new Question("Which enzyme is responsible for synthesizing new DNA strands during replication?",
                                                        Arrays.asList("RNA polymerase", "DNA ligase", "DNA polymerase", "Helicase"),
                                                        "DNA polymerase",
                                                        "Explanation: DNA polymerase is the enzyme responsible for synthesizing new DNA strands by adding nucleotides complementary to the template strand."
                                                ),
                                                new Question("What is the role of the promoter in DNA?",
                                                        Arrays.asList("It initiates DNA replication.", "It signals the start of transcription.", "It helps with DNA repair.", "It binds to the ribosome."),
                                                        "It signals the start of transcription.",
                                                        "Explanation: The promoter is a region of DNA that signals the beginning of transcription, allowing RNA polymerase to bind and start synthesizing RNA."
                                                ),
                                                new Question("What type of bond connects the phosphate group of one nucleotide to the sugar of another nucleotide?",
                                                        Arrays.asList("Hydrogen bond", "Ionic bond", "Peptide bond", "Phosphodiester bond"),
                                                        "Phosphodiester bond",
                                                        "Explanation: A phosphodiester bond connects the phosphate group of one nucleotide to the deoxyribose sugar of the next nucleotide, forming the DNA backbone."
                                                ),
                                                new Question("What is the significance of complementary base pairing in DNA?",
                                                        Arrays.asList("It allows for DNA to replicate accurately.", "It stabilizes the DNA molecule.", "It influences gene expression.", "All of the above."),
                                                        "All of the above.",
                                                        "Explanation: Complementary base pairing is crucial for accurate DNA replication, stability of the DNA structure, and proper gene expression."
                                                ),
                                                new Question("How many chromosomes do humans typically have in each somatic cell?",
                                                        Arrays.asList("23", "46", "92", "48"),
                                                        "46",
                                                        "Explanation: Humans typically have 46 chromosomes in each somatic cell, organized into 23 pairs."
                                                ),
                                                new Question("Which of the following processes involves the use of DNA?",
                                                        Arrays.asList("Transcription", "Translation", "Replication", "All of the above"),
                                                        "All of the above",
                                                        "Explanation: All of these processes—transcription (making RNA), translation (making proteins), and replication (copying DNA)—involve DNA."
                                                ),
                                                new Question("What happens during the process of transcription?",
                                                        Arrays.asList("DNA is copied into RNA.", "RNA is translated into proteins.", "DNA is repaired.", "DNA is replicated."),
                                                        "DNA is copied into RNA.",
                                                        "Explanation: During transcription, a specific segment of DNA is copied into RNA by the enzyme RNA polymerase."
                                                )
                                        );

                                        Collections.shuffle(allDNAAverageQuestions);
                                        questions = allDNAAverageQuestions.subList(0, 5);  // Select 5 random questions

                                        break;
                                    case "Difficult":
                                        List<Question> allDNADifficultQuestions = Arrays.asList(
                                                new Question("What is the role of telomeres in DNA replication?",
                                                        Arrays.asList("They act as a primer for replication.", "They prevent the loss of genetic information during replication.", "They are involved in protein synthesis.", "They facilitate the binding of RNA polymerase."),
                                                        "They prevent the loss of genetic information during replication.",
                                                        "Explanation: Telomeres are repetitive DNA sequences at the ends of chromosomes that protect against the loss of genetic information during DNA replication."
                                                ),
                                                new Question("What is a mutation in DNA?",
                                                        Arrays.asList("A change in the nucleotide sequence", "A normal variation in DNA", "A replication error that is corrected", "An addition of new DNA sequences"),
                                                        "A change in the nucleotide sequence",
                                                        "Explanation: A mutation is a change in the nucleotide sequence of DNA, which can lead to changes in protein structure and function."
                                                ),
                                                new Question("How can DNA fingerprints be used in forensic science?",
                                                        Arrays.asList("To determine the age of a suspect", "To analyze protein synthesis", "To identify individuals based on unique DNA patterns", "To clone organisms"),
                                                        "To identify individuals based on unique DNA patterns",
                                                        "Explanation: DNA fingerprints analyze unique patterns in an individual's DNA, allowing forensic scientists to identify individuals with high accuracy."
                                                ),
                                                new Question("What is the significance of the antiparallel structure of DNA?",
                                                        Arrays.asList("It allows DNA to be stable and less susceptible to damage.", "It ensures proper base pairing during replication.", "It influences gene expression.", "It allows for the formation of the double helix."),
                                                        "It ensures proper base pairing during replication.",
                                                        "Explanation: The antiparallel structure of DNA (one strand running 5' to 3' and the other 3' to 5') is crucial for proper base pairing and accurate replication by DNA polymerase."
                                                ),
                                                new Question("In the context of DNA replication, what is the role of helicase?",
                                                        Arrays.asList("To synthesize new DNA strands", "To unwind the DNA double helix", "To seal the gaps between Okazaki fragments", "To create primers"),
                                                        "To unwind the DNA double helix",
                                                        "Explanation: Helicase is the enzyme responsible for unwinding the DNA double helix, allowing the replication machinery to access the single strands for replication."
                                                ),
                                                new Question("How does DNA methylation influence gene expression?",
                                                        Arrays.asList("It increases transcription rates.", "It serves as a signal for DNA repair.", "It generally represses gene expression by preventing transcription factors from binding.", "It enhances RNA splicing."),
                                                        "It generally represses gene expression by preventing transcription factors from binding.",
                                                        "Explanation: DNA methylation typically represses gene expression by adding methyl groups to cytosine bases in DNA, preventing transcription factors from accessing the DNA."
                                                )
                                        );

                                        Collections.shuffle(allDNADifficultQuestions);
                                        questions = allDNADifficultQuestions.subList(0, 3);  // Select 3 random questions

                                        break;
                                }
                                break;

                        }
                        break;


                    default:
                        break;
                }
            }

            questionTextView = findViewById(R.id.questionHolderTextView);
            questionHolderBox = findViewById(R.id.questionHolderBox);
            choicesLayout = findViewById(R.id.choicesLayout);
            tapToNext = findViewById(R.id.tapToNext);

            remainingTextView = findViewById(R.id.Remaining);

            showNextQuestion();
            updateRemainingQuestions();
            new Handler().postDelayed(() -> {
                Animation fallAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
                headerImage.setVisibility(View.VISIBLE);
                headerImage.startAnimation(fallAnimation1);
            }, 200);

            return insets;
        });

        findViewById(R.id.main).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN && isDialogShown) {
                isDialogShown = false;  // Reset dialog shown flag
                tapToNext.setVisibility(View.INVISIBLE);  // Hide the tap-to-next message
                currentQuestionIndex++;  // Go to the next question
                showNextQuestion();
                return true;
            }
            return false;
        });
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);

            questionTextView.setText(currentQuestion.getQuestion());

            Animation questionFallAnimation = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
            questionHolderBox.startAnimation(questionFallAnimation);

            choicesLayout.removeAllViews();

            for (int i = 0; i < currentQuestion.getChoices().size(); i++) {
                String choice = currentQuestion.getChoices().get(i);
                Button choiceButton = new Button(this);
                choiceButton.setText(choice);
                choiceButton.setBackgroundResource(R.drawable.rounded_box_button);  // Rounded box button
                choiceButton.setTextColor(Color.BLACK);
                choiceButton.setOnClickListener(v -> checkAnswer(choiceButton, choice, currentQuestion.getCorrectAnswer()));
                choiceButton.setVisibility(View.INVISIBLE);  // Initially invisible

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        160);
                params.setMargins(8, 8, 8, 8);
                choiceButton.setLayoutParams(params);
                choicesLayout.addView(choiceButton);

                int animationDelay = 100 * i;

                new Handler().postDelayed(() -> {
                    Animation fallAnimation1 = AnimationUtils.loadAnimation(this, R.anim.fall_from_top);
                    choiceButton.setVisibility(View.VISIBLE);
                    choiceButton.startAnimation(fallAnimation1);
                }, animationDelay);
            }

            updateRemainingQuestions();
        } else {
            showCompletionDialog();
        }
    }


    private void checkAnswer(Button clickedButton, String selectedAnswer, String correctAnswer) {
        for (int i = 0; i < choicesLayout.getChildCount(); i++) {
            View child = choicesLayout.getChildAt(i);
            if (child instanceof Button) {
                child.setEnabled(false);  // Disable all buttons
                child.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));

                if(((Button) child).getText().equals(correctAnswer)){
                    child.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

                }
            }
        }

        // Highlight the selected button based on whether it's correct or incorrect
        if (selectedAnswer.equals(correctAnswer)) {
            clickedButton.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            correctAnswers++;
            showAnswerDialog(true);
        } else {
            clickedButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            showAnswerDialog(false);
        }
    }


    private void showAnswerDialog(boolean isCorrect) {
        isDialogShown = true;  // Mark dialog as shown

        // Get the current question to access the explanation
        Question currentQuestion = questions.get(currentQuestionIndex);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
        builder.setView(dialogView);

        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        Button closeDialogButton = dialogView.findViewById(R.id.closeDialogButton);

        // Show message depending on whether the answer was correct or incorrect
        if (isCorrect) {
            dialogMessage.setText("Correct Answer!\n\n" + currentQuestion.getExplanation());
        } else {
            dialogMessage.setText("Incorrect Answer!\nThe correct answer is " + currentQuestion.getCorrectAnswer() + ".\n\n" + currentQuestion.getExplanation());
        }

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);  // Prevent closing the dialog by tapping outside
        closeDialogButton.setOnClickListener(v -> {
            dialog.dismiss();  // Close the dialog
            tapToNext.setVisibility(View.VISIBLE);  // Show the tap-to-next message

            // Update remaining questions
            updateRemainingQuestions();
        });

        // Show the dialog
        dialog.show();

        // Automatically dismiss the dialog when the user taps the screen
        new Handler().postDelayed(() -> {
            dialog.dismiss();
            tapToNext.setVisibility(View.VISIBLE);  // Show the tap-to-next message

            // Update remaining questions
            updateRemainingQuestions();
        }, 8000);  // Dismiss after 1.5 seconds
    }


    private void updateRemainingQuestions() {
        int remainingQuestions = questions.size() - currentQuestionIndex;
        int totalQuestions = questions.size();

        // Update the remainingTextView
        remainingTextView.setText(remainingQuestions + "/" + totalQuestions + " Remaining");

        // Make sure the remainingTextView is visible
        remainingTextView.setVisibility(View.VISIBLE);
    }

    private void showCompletionDialog() {
        // Calculate the percentage score
        int totalQuestions = questions.size();
        double percentage = ((double) correctAnswers / totalQuestions) * 100;

        // Save the result in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("quiz_progress", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Generate a unique key for each test session, for example using current time
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        editor.putString(currentTime,
                "Date: " + currentTime +
                        ", Topic: " + value +
                        ", Difficulty: " + difficulty +
                        ", Score: " + correctAnswers + "/" + totalQuestions +
                        ", Percentage: " + percentage + "%");
        editor.apply();  // Commit the changes

        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_quiz_result, null);

        // Find the TextView and Button in the custom layout
        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);
        Button closeDialogButton = dialogView.findViewById(R.id.closeDialogButton);

        // Set the message with the quiz results
        dialogMessage.setText("Quiz Completed!\n" +
                "Topic: " + value +
                "\nDifficulty: " + difficulty +
                "\nCorrect Answers: " + correctAnswers + "/" + totalQuestions +
                "\nPercentage: " + String.format(Locale.getDefault(), "%.2f", percentage) + "%");

        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();

        // Set the button click listener to close the dialog
        closeDialogButton.setOnClickListener(v -> {
            alertDialog.dismiss();
            finish();  // Finish the activity and return to the previous screen
        });

        alertDialog.show();
    }


}
