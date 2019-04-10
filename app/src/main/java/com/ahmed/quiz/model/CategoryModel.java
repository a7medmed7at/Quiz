package com.ahmed.quiz.model;

public class CategoryModel {
    public static final String SCIENCES = "Science";
    public static final String GEOGRAPHY = "Geography";
    public static final String MATH = "Math";

    public static String[] getCategories() {
        return new String[]{SCIENCES ,GEOGRAPHY ,MATH};
    }

}
