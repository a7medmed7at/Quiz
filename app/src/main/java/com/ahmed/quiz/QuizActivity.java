package com.ahmed.quiz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmed.quiz.model.QuestionModel;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    public static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "questionCount";
    private static final String KEY_ANSWERED = "answered";
    private static final String KEY_QUESTION_LIST = "questionList";

    private TextView mTextQuestionNumber;
    private TextView mTextQuestion;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioOption1;
    private RadioButton mRadioOption2;
    private RadioButton mRadioOption3;
    private Button mButtonNextQuestion;

    private ArrayList<QuestionModel> mQuestionsModel;
    private String mName;
    private QuestionModel mCurrentQuestion;
    private int mCurrentQuestionCounter;
    private int mQuestionsSize;
    private Boolean mAnswered;
    private int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mTextQuestionNumber = findViewById(R.id.text_question_number);
        mRadioGroup = findViewById(R.id.radio_group);
        mTextQuestion = findViewById(R.id.text_question);
        mRadioOption1 = findViewById(R.id.radio_option1);
        mRadioOption2 = findViewById(R.id.radio_option2);
        mRadioOption3 = findViewById(R.id.radio_option3);
        mButtonNextQuestion = findViewById(R.id.button_Next);


        if (savedInstanceState == null) {
            mQuestionsModel = new ArrayList<>();
            getInfoFromIntent();
            showQuestion();
        } else {
            mQuestionsModel = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            mQuestionsSize = mQuestionsModel.size() - 3;
            mCurrentQuestionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            mCurrentQuestion = mQuestionsModel.get(mCurrentQuestionCounter - 1);
            mScore = savedInstanceState.getInt(KEY_SCORE);
            mAnswered = savedInstanceState.getBoolean(KEY_ANSWERED);
            mName = savedInstanceState.getString(LoginActivity.KEY_NAME);
        }
        mButtonNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAnswered) {
                    if (mRadioOption1.isChecked() ||
                            mRadioOption2.isChecked() || mRadioOption3.isChecked()) {
                        checkAnswer();
                    } else
                        Toast.makeText(QuizActivity.this, "plz select an answer"
                                , Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void getInfoFromIntent() {
        Intent intent = getIntent();
        mQuestionsModel = intent.getParcelableArrayListExtra(LoginActivity.KEY_QUESTIONS);
        Collections.shuffle(mQuestionsModel);
        mQuestionsSize = mQuestionsModel.size() - 3;
        mName = intent.getStringExtra(LoginActivity.KEY_NAME);
    }

    private void showQuestion() {
        mRadioGroup.clearCheck();
        if (mCurrentQuestionCounter < mQuestionsSize) {
            mCurrentQuestion = mQuestionsModel.get(mCurrentQuestionCounter);

            mTextQuestion.setText(mCurrentQuestion.getQuestion());
            mRadioOption1.setText(mCurrentQuestion.getOptions().get(0));
            mRadioOption2.setText(mCurrentQuestion.getOptions().get(1));
            mRadioOption3.setText(mCurrentQuestion.getOptions().get(2));
            mCurrentQuestionCounter++;
            String question = mCurrentQuestionCounter + "/" + mQuestionsSize;
            mTextQuestionNumber.setText(question);
            mAnswered = false;

        }
        if (mCurrentQuestionCounter == mQuestionsSize)
            mButtonNextQuestion.setText("Result");
    }

    private void checkAnswer() {
        mAnswered = true;
        RadioButton rbSelected = findViewById(mRadioGroup.getCheckedRadioButtonId());
        int answerNr = mRadioGroup.indexOfChild(rbSelected);
        if (answerNr + 1 == mCurrentQuestion.getCorrectAnswer()) {
            mScore++;
        }
        if (mCurrentQuestionCounter == mQuestionsSize) {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra(KEY_SCORE, mScore);
            intent.putExtra(LoginActivity.KEY_NAME, mName);
            startActivity(intent);
            finish();
        } else
            showQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_SCORE, mScore);
        outState.putInt(KEY_QUESTION_COUNT, mCurrentQuestionCounter);
        outState.putBoolean(KEY_ANSWERED, mAnswered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, mQuestionsModel);
        outState.putString(LoginActivity.KEY_NAME, mName);
    }


}
