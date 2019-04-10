package com.ahmed.quiz.network;

import com.ahmed.quiz.model.QuizModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceHolderApi {

    @GET("Quiz.php?api_questions")
    Call<QuizModel> getQuiz();
}
