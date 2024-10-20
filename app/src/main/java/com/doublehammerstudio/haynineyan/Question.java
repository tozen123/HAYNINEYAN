package com.doublehammerstudio.haynineyan;

import java.util.List;

public class Question {
    private String question;
    private List<String> choices;
    private String correctAnswer;
    private String explanation;

    public Question(String question, List<String> choices, String correctAnswer, String explanation) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }
}
