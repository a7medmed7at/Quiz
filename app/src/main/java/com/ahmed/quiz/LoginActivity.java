package com.ahmed.quiz;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.ahmed.quiz.data.QuizContract;
import com.ahmed.quiz.model.CategoryModel;
import com.ahmed.quiz.model.QuestionModel;
import com.ahmed.quiz.network.QuizRetrofit;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String KEY_NAME = "keyName";
    public static final String KEY_QUESTIONS = "questions";
    private static final int ID_LOADER_LOGIN = 1;

    private EditText mEditName;
    private Spinner mSpinnerCategory;
    private String mCategorySelected;
    private ProgressBar mProgressBar;
    private Button mButtonStart;
    private int mIdCategorySelected;
    private View mViewSpinner;
    private ArrayList<QuestionModel> mQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mViewSpinner = findViewById(R.id.view_below_spinner);
        mProgressBar = findViewById(R.id.progressBar_load);
        mEditName = findViewById(R.id.editText_name);
        mSpinnerCategory = findViewById(R.id.spinner_category);
        mButtonStart = findViewById(R.id.button_start);

        loadSpinnerCategory();
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean matches = mEditName.getText().toString().trim().matches("");
                if (matches) {
                    Toast.makeText(LoginActivity.this,
                            "plz enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    mCategorySelected = mSpinnerCategory.getSelectedItem().toString();
                    getSupportLoaderManager().initLoader(ID_LOADER_LOGIN,
                            null, LoginActivity.this);
                }

            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        disableProgressBar();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        switch (i) {
            case ID_LOADER_LOGIN:
                showProgressBar();
                Uri uri = Uri.withAppendedPath(QuizContract.BASE_CONTENT_URI,
                        QuizContract.CategoryTable.TABLE_NAME);
                String[] projection = new String[]{QuizContract.CategoryTable._ID};
                String selection = QuizContract.CategoryTable.COLUMN_NAME + "=?";
                String[] selectionArg = new String[]{mCategorySelected};
                return new CursorLoader(this, uri, projection
                        , selection, selectionArg, null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + i);
        }

    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToNext()) {
            mIdCategorySelected = cursor.getInt(cursor.getColumnIndex(
                    QuizContract.CategoryTable._ID));
            cursor.close();
            getSupportLoaderManager().destroyLoader(ID_LOADER_LOGIN);
            loadData();

        } else {
            QuizRetrofit.callRetrofit(this);
            getSupportLoaderManager().restartLoader(ID_LOADER_LOGIN, null, this);
        }

    }

    private void loadData() {
        Uri uriQuiz = Uri.withAppendedPath(QuizContract.BASE_CONTENT_URI,
                QuizContract.QuizTable.TABLE_NAME);
        String[] projectionQuiz = new String[]{QuizContract.QuizTable.COLUMN_QUESTION,
                QuizContract.QuizTable.COLUMN_OPTION_1,
                QuizContract.QuizTable.COLUMN_OPTION_2,
                QuizContract.QuizTable.COLUMN_OPTION_3,
                QuizContract.QuizTable.COLUMN_ANSWER};

        String selectionQuiz = QuizContract.QuizTable.COLUMN_FK_CATEGORY + "=?";
        String[] selectionArgQuiz = new String[]{String.valueOf(mIdCategorySelected)};

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(uriQuiz, projectionQuiz,
                selectionQuiz, selectionArgQuiz, null);
        if (cursor.moveToNext()) {
            mQuestions = new ArrayList<>();
            do {
                QuestionModel question = new QuestionModel();
                question.setQuestion(cursor.getString(
                        cursor.getColumnIndex(QuizContract.QuizTable.COLUMN_QUESTION)));
                String o1 = cursor.getString(cursor.getColumnIndex(
                        QuizContract.QuizTable.COLUMN_OPTION_1));
                String o2 = cursor.getString(cursor.getColumnIndex(
                        QuizContract.QuizTable.COLUMN_OPTION_2));
                String o3 = cursor.getString(cursor.getColumnIndex(
                        QuizContract.QuizTable.COLUMN_OPTION_3));
                List<String> list = new ArrayList<>();
                list.add(o1);
                list.add(o2);
                list.add(o3);
                question.setOptions(list);
                question.setCorrectIndex(cursor.getInt(
                        cursor.getColumnIndex(QuizContract.QuizTable.COLUMN_ANSWER)));
                mQuestions.add(question);
            } while (cursor.moveToNext());
            cursor.close();
            moveToQuizActivity();
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        loader.reset();
    }


    private void loadSpinnerCategory() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_layout, CategoryModel.getCategories());
        mSpinnerCategory.setAdapter(arrayAdapter);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mEditName.setVisibility(View.GONE);
        mSpinnerCategory.setVisibility(View.GONE);
        mViewSpinner.setVisibility(View.GONE);
        mButtonStart.setVisibility(View.GONE);
    }

    private void disableProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mEditName.setVisibility(View.VISIBLE);
        mSpinnerCategory.setVisibility(View.VISIBLE);
        mViewSpinner.setVisibility(View.VISIBLE);
        mButtonStart.setVisibility(View.VISIBLE);
    }


    private void moveToQuizActivity() {
        Intent intent = new Intent(LoginActivity.this, QuizActivity.class);
        intent.putExtra(KEY_NAME, mEditName.getText().toString());
        intent.putParcelableArrayListExtra(KEY_QUESTIONS, mQuestions);
        startActivity(intent);
    }


}
