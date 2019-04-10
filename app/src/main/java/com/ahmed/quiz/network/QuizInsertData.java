package com.ahmed.quiz.network;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.ahmed.quiz.data.QuizContract;
import com.ahmed.quiz.model.CategoryModel;
import com.ahmed.quiz.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

class QuizInsertData {
    private static ArrayList<ContentValues> allValues;

    static void insetData(Context context, List<QuestionModel> sciences,
                          List<QuestionModel> geography, List<QuestionModel> math) {
        insertCategoryTable(context);
        allValues =new ArrayList<>();
        getSciences(sciences);
        getGeography(geography);
        getMath(math);


        insertQuizTable(context);


    }


    synchronized private static void insertCategoryTable(@NonNull Context context) {
        String[] strings = CategoryModel.getCategories();
        ContentValues[] values = new ContentValues[strings.length];
        for (int i = 0; i < 3; i++) {
            ContentValues value = new ContentValues();
            value.put(QuizContract.CategoryTable.COLUMN_NAME, strings[i]);
            values[i] = value;
        }

        ContentResolver contentResolver = context.getContentResolver();
        Uri uriCategory = Uri.withAppendedPath(QuizContract.BASE_CONTENT_URI, QuizContract.CategoryTable.TABLE_NAME);
        contentResolver.bulkInsert(uriCategory, values);
    }

    private static void getSciences(@NonNull List<QuestionModel> sciences) {
        for (QuestionModel model : sciences) {
            ContentValues values = new ContentValues();
            values.put(QuizContract.QuizTable.COLUMN_QUESTION, model.getQuestion());
            values.put(QuizContract.QuizTable.COLUMN_OPTION_1, model.getOptions().get(0));
            values.put(QuizContract.QuizTable.COLUMN_OPTION_2, model.getOptions().get(1));
            values.put(QuizContract.QuizTable.COLUMN_OPTION_3, model.getOptions().get(2));
            values.put(QuizContract.QuizTable.COLUMN_ANSWER, model.getCorrectAnswer() + 1);
            values.put(QuizContract.QuizTable.COLUMN_FK_CATEGORY, QuizContract.CategoryTable.SCIENCE_INDEX);
            allValues.add(values);

        }
    }

    private static void getGeography(@NonNull List<QuestionModel> geography) {
        for (QuestionModel model : geography) {
            ContentValues values = new ContentValues();
            values.put(QuizContract.QuizTable.COLUMN_QUESTION, model.getQuestion());
            values.put(QuizContract.QuizTable.COLUMN_OPTION_1, model.getOptions().get(0));
            values.put(QuizContract.QuizTable.COLUMN_OPTION_2, model.getOptions().get(1));
            values.put(QuizContract.QuizTable.COLUMN_OPTION_3, model.getOptions().get(2));
            values.put(QuizContract.QuizTable.COLUMN_ANSWER, model.getCorrectAnswer() + 1);
            values.put(QuizContract.QuizTable.COLUMN_FK_CATEGORY, QuizContract.CategoryTable.GEOGRAPHY_INDEX);
            allValues.add(values);

        }
    }

    private static void getMath(@NonNull List<QuestionModel> math) {

        for (QuestionModel model : math) {
            ContentValues values = new ContentValues();
            values.put(QuizContract.QuizTable.COLUMN_QUESTION, model.getQuestion());
            values.put(QuizContract.QuizTable.COLUMN_OPTION_1, model.getOptions().get(0));
            values.put(QuizContract.QuizTable.COLUMN_OPTION_2, model.getOptions().get(1));
            values.put(QuizContract.QuizTable.COLUMN_OPTION_3, model.getOptions().get(2));
            values.put(QuizContract.QuizTable.COLUMN_ANSWER, model.getCorrectAnswer() + 1);
            values.put(QuizContract.QuizTable.COLUMN_FK_CATEGORY, QuizContract.CategoryTable.MATH_INDEX);
            allValues.add(values);

        }
    }

    synchronized private static void insertQuizTable(@NonNull Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uriQuiz = Uri.withAppendedPath(QuizContract.BASE_CONTENT_URI,
                QuizContract.QuizTable.TABLE_NAME);
        allValues.trimToSize();
        ContentValues[] values = allValues.toArray(new ContentValues[allValues.size()]);
        contentResolver.bulkInsert(uriQuiz, values);


    }


}
