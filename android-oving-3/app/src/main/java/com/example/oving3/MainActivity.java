package com.example.oving3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private String[] names;
    private String[] birthdays;
    private String newName;
    private String newBirthday;
    private ArrayAdapter<String> adapterN;
    private ArrayAdapter<String> adapterB;
    public ArrayList<String> lst;
    public ArrayList<String> lst2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getStringArrayListExtra("names") == null) {
            Resources res = getResources();
            names = res.getStringArray(R.array.names);
            birthdays = res.getStringArray(R.array.birthdays);
            lst = new ArrayList<String>(Arrays.asList(names));
            lst2 = new ArrayList<String>(Arrays.asList(birthdays));
        }else{
            lst = getIntent().getStringArrayListExtra("names");
            lst2 = getIntent().getStringArrayListExtra("birthdays");
        }

        adapterN = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lst);
        adapterB = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lst2);

        initierList();

        Button b = (Button)findViewById(R.id.buttonAdd);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView nameTW = (TextView)findViewById(R.id.name);
                TextView birthdayTW = (TextView)findViewById(R.id.birthday);
                newName = nameTW.getText().toString();
                newBirthday = birthdayTW.getText().toString();
                addToList();
            }
        });

    }

    private void addToList() {
        lst.add(newName);
        lst2.add(newBirthday);
        adapterN.notifyDataSetChanged();
    }

    private void initierList() {
        ListView listView = (ListView) (findViewById(R.id.listView1));
        listView.setAdapter(adapterN);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View valgt, int posisjon, long id) {
                visPerson(posisjon);
            }
        });
    }

    private void visPerson(int nr) {
        Intent i = new Intent("com.example.oving3.editPerson");
        i.putExtra("nr", nr);
        i.putExtra("names", lst);
        i.putExtra("birthdays", lst2);
        startActivity(i);
    }

}
