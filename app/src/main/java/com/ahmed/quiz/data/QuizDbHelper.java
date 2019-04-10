package com.ahmed.quiz.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ahmed.quiz.data.QuizContract.CategoryTable;
import com.ahmed.quiz.data.QuizContract.QuizTable;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static int DATABASE_VERSION = 1;

    QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CATEGORY_TABLE =
                "CREATE TABLE " + CategoryTable.TABLE_NAME + " ( " +
                        CategoryTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CategoryTable.COLUMN_NAME + " TEXT NOT NULL );";

        final String SQL_QUIZ_TABLE =
                "CREATE TABLE " + QuizTable.TABLE_NAME + " (" +
                        QuizTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        QuizTable.COLUMN_QUESTION + " TEXT NOT NULL," +
                        QuizTable.COLUMN_OPTION_1 + " TEXT NOT NULL," +
                        QuizTable.COLUMN_OPTION_2 + " TEXT NOT NULL," +
                        QuizTable.COLUMN_OPTION_3 + " TEXT NOT NULL," +
                        QuizTable.COLUMN_ANSWER + " INTEGER NOT NULL," +
                        QuizTable.COLUMN_FK_CATEGORY + " INTEGER NOT NULL," +
                        " FOREIGN KEY(" + QuizTable.COLUMN_FK_CATEGORY + ")" +
                        " REFERENCES " + CategoryTable.TABLE_NAME + "(" + CategoryTable._ID + ")" +
                        " ON DELETE CASCADE );";

        db.execSQL(SQL_CATEGORY_TABLE);
        db.execSQL(SQL_QUIZ_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + CategoryTable.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + QuizTable.TABLE_NAME);
        onCreate(db);
    }
}
