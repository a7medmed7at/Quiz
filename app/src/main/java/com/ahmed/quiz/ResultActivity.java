package com.ahmed.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView mTextName;
    private TextView mTextScore;
    private Button mButtonPlayAgain;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mTextName = findViewById(R.id.text_name);
        mTextScore = findViewById(R.id.text_score);
        mButtonPlayAgain = findViewById(R.id.button_play_again);

        Intent intent = getIntent();
        int score = intent.getIntExtra(QuizActivity.KEY_SCORE, 0);
        mTextScore.setText(String.valueOf(score));


        String stringName = intent.getStringExtra(LoginActivity.KEY_NAME);
        String[] s = stringName.split(" ");
        String name = "-- Hi, " + s[0] + " your score is --";
        mTextName.setText(name);
        mButtonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


    }


}
