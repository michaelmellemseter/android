package com.example.oving6client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button)findViewById(R.id.knapp);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView number1 = (TextView)findViewById(R.id.number1);
                TextView number2 = (TextView)findViewById(R.id.number2);
                int nr1 = Integer.parseInt(number1.getText().toString());
                int nr2 = Integer.parseInt(number2.getText().toString());
                int[] numbers = new int[2];
                numbers[0] = nr1;
                numbers[1] = nr2;

                Client klient = new Client(numbers);
                klient.start();

                try{ TimeUnit.SECONDS.sleep(1); }
                catch(Exception e){e.printStackTrace();}

                String answer = klient.getAnswer();
                TextView answerTW = (TextView)findViewById(R.id.answer);
                answerTW.setText(answer);
            }
        });


    }
}
