package com.ahmed.quiz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizModel {
    @SerializedName("Science")
    @Expose
    private List<QuestionModel> science;
    @SerializedName("Geography")
    @Expose
    private List<QuestionModel> geography;
    @SerializedName("Math")
    @Expose
    private List<QuestionModel> math;

    public List<QuestionModel> getScience() {
        return science;
    }

    public List<QuestionModel> getGeography() {
        return geography;
    }

    public List<QuestionModel> getMath() {
        return math;
    }
}
