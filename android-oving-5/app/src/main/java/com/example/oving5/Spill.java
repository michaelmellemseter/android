package com.example.oving5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Spill extends AppCompatActivity {
    private HttpWrapperThreaded network;
    final static String TAG = "Spill";
    final static String urlToServer = "http://tomcat.stud.iie.ntnu.no/studtomas/tallspill.jsp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spill_layout);

        Log.w(TAG,"Contacting server...");
        network = new HttpWrapperThreaded(this, urlToServer);

        network.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET,
                createRequestValues(getIntent().getStringExtra("name"), getIntent().getStringExtra("card")));

        Button b = (Button)findViewById(R.id.restart);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //slette cookies
                Intent i = new Intent("com.example.oving5.MainActivity");
                startActivity(i);
            }
        });

        Button b2 = (Button)findViewById(R.id.sendInn);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //lagre cookies
                network.runHttpRequestInThread(HttpWrapperThreaded.HttpRequestType.HTTP_GET, createRequestValues2());
            }
        });
    }

    private Map<String,String> createRequestValues(String navn, String kortnummer){
        Map<String,String> valueList = new HashMap<String,String>();
        valueList.put("navn", navn);
        valueList.put("kortnummer", kortnummer);
        return valueList;
    }

    private Map<String,String> createRequestValues2(){
        Map<String,String> valueList = new HashMap<String,String>();
        TextView number = (TextView)findViewById(R.id.number);
        valueList.put("tall", number.getText().toString());
        return valueList;
    }

    public void showResponse(String response){
        Log.w(TAG,"Server responded with: " + response);
        TextView name = (TextView)findViewById(R.id.tekst);
        name.setText(response);
    }
}
