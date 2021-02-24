package com.example.oving5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button)findViewById(R.id.knapp);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView name = (TextView)findViewById(R.id.name);
                TextView card = (TextView)findViewById(R.id.card);
                if (name.getText().toString().equals("") || card.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Vennligst skriv inn et navn og et kortnummer", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent("com.example.oving5.Spill");
                    i.putExtra("name", name.getText().toString());
                    i.putExtra("card", card.getText().toString());
                    startActivity(i);
                }
            }
        });
    }
}
