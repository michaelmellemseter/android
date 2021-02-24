package com.example.oving3;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class editPerson extends AppCompatActivity {
    ArrayList<String> nameList;
    ArrayList<String> birthdayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final int number = getIntent().getIntExtra("nr", 100);
        nameList = getIntent().getStringArrayListExtra("names");
        birthdayList = getIntent().getStringArrayListExtra("birthdays");
        visValgtPerson(number);

        Button b = (Button)findViewById(R.id.buttonSave);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView nameTW = (TextView)findViewById(R.id.nameEdit);
                TextView birthdayTW = (TextView)findViewById(R.id.birthdayEdit);
                String nameEdit = nameTW.getText().toString();
                String birthdayEdit = birthdayTW.getText().toString();
                nameList.set(number, nameEdit);
                birthdayList.set(number, birthdayEdit);
                Intent i = new Intent("com.example.oving3.MainActivity");
                i.putExtra("names", nameList);
                i.putExtra("birthdays", birthdayList);
                startActivity(i);
            }
        });

    }

    private void visValgtPerson(int nr) {
        if (nr >= 0) {
            TextView nameTW = (TextView)findViewById(R.id.nameEdit);
            TextView birthdayTW = (TextView)findViewById(R.id.birthdayEdit);
            String nameString = nameList.get(nr);
            String birthdayString = birthdayList.get(nr);
            nameTW.setText(nameString);
            birthdayTW.setText(birthdayString);
        }
    }
}
