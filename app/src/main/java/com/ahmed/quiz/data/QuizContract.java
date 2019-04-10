package com.ahmed.quiz.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class QuizContract {

    static final String CONTENT_AUTHORITY = "com.ahmed.quiz";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private QuizContract() {
    }

    public static class CategoryTable implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME = "name";

        public static final int SCIENCE_INDEX = 1;
        public static final int GEOGRAPHY_INDEX = 2;
        public static final int MATH_INDEX = 3;
    }

    public static class QuizTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION_1 = "option1";
        public static final String COLUMN_OPTION_2 = "option2";
        public static final String COLUMN_OPTION_3 = "option3";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_FK_CATEGORY = "fk_category";
    }
}
