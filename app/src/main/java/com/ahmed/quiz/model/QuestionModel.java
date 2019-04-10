package com.ahmed.quiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionModel implements Parcelable {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("options")
    @Expose
    private List<String> options;
    @SerializedName("correct_index")
    @Expose
    private int correctIndex;

    public QuestionModel() {
    }

    protected QuestionModel(Parcel in) {
        question = in.readString();
        options = in.createStringArrayList();
        correctIndex = in.readInt();
    }

    public static final Creator<QuestionModel> CREATOR = new Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectAnswer() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeStringList(options);
        dest.writeInt(correctIndex);
    }
}
