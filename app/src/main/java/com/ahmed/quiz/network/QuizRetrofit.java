package com.ahmed.quiz.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ahmed.quiz.model.QuestionModel;
import com.ahmed.quiz.model.QuizModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizRetrofit {
    private static Boolean sInitialized = false;

    public static void callRetrofit(final Context context) {

        if (sInitialized) {
            return;
        }
        sInitialized = true;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://t2.someotherhost.com/_hg/quiz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        PlaceHolderApi placeHolderApi = retrofit.create(PlaceHolderApi.class);
        Call<QuizModel> call = placeHolderApi.getQuiz();
        call.enqueue(new Callback<QuizModel>() {
            @Override
            public void onResponse(@NonNull Call<QuizModel> call, @NonNull Response<QuizModel> response) {
                if (response.isSuccessful()) {
                    QuizModel body = response.body();
                    List<QuestionModel> sciences = body.getScience();
                    List<QuestionModel> geography = body.getGeography();
                    List<QuestionModel> math = body.getMath();
                    if (sciences.size() > 0 && geography.size() > 0 && math.size() > 0) {
                        QuizInsertData.insetData(context, sciences, geography, math);
                    } else
                        sInitialized = false;

                } else
                    sInitialized = false;

            }

            @Override
            public void onFailure(@NonNull Call<QuizModel> call, @NonNull Throwable t) {
                sInitialized = false;
            }
        });
    }


}
