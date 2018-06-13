package com.example.gahlot.braintrainer;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    RelativeLayout gamelogic;
    GridView AnswerBox;
    TextView resultText;
    TextView pointText;
    TextView textview;
    TextView timer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int lOcA;
    int score = 0;
    int numberofquestions = 0;
    Button button0,button1,button2,button3,playAgainbutton;
    private InterstitialAd mInterstitialAd;

    public void playAgain(View view) {

        score = 0;
        numberofquestions = 0;
        timer.setText("30s");
        pointText.setText("0/0");
        resultText.setText("");
        playAgainbutton.setVisibility(View.INVISIBLE);
        generateQuestions();

        new CountDownTimer(30100,1000){

            @Override
            public void onTick(long l) {
                timer.setText(String.valueOf(l / 1000) + "s");

            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                resultText.setText("Done");
                playAgainbutton.setVisibility(View.VISIBLE);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        }.start();

    }

    public void generateQuestions()
    {
        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        textview.setText(Integer.toString(a) + "+" + Integer.toString(b));

        lOcA = rand.nextInt(4);
        answers.clear();
        int incorrectAnswer;

        for (int i = 0; i<4; i++)
        {
            if (i == lOcA )
            {
                answers.add(a + b);
            } else {
                incorrectAnswer = rand.nextInt(41);

                while
                        (incorrectAnswer == a + b)
                {
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view) {

        if (view.getTag().toString().equals(Integer.toString(lOcA)))
        {
            score++;
            resultText.setText("Correct!");
        } else {
            resultText.setText("Wrong!");
        }
        numberofquestions++;
        pointText.setText(Integer.toString(score) + "/" + Integer.toString(numberofquestions));
        generateQuestions();
    }

    public void start(View view)
    {
        startButton.setVisibility(View.INVISIBLE);
        gamelogic.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAginButton));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        gamelogic = (RelativeLayout)findViewById(R.id.gameRelativeLayout);
        textview = (TextView)findViewById(R.id.sumTextView);
        timer = (TextView)findViewById(R.id.timerTextView);
        button0 = (Button) findViewById(R.id.button1);
        button1 = (Button) findViewById(R.id.button2);
        button2 = (Button) findViewById(R.id.button3);
        button3 = (Button) findViewById(R.id.button4);
        resultText = (TextView)findViewById(R.id.resultTextView);
        pointText = (TextView)findViewById(R.id.pointTextView);
        playAgainbutton = (Button)findViewById(R.id.playAginButton);

        MobileAds.initialize(this, "ca-app-pub-6234849788191173~5983138435");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6234849788191173/7843015017");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }
}
