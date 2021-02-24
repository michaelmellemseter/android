package com.example.oving7;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            db = new DatabaseManager(this);
            db.clean();
            long id = db.insert("Bud Kurniawan","Android Application Development: A Beginners Tutorioal");
            id = db.insert("Mildrid Ljosland", "Programmering i C++");
            id = db.insert("Else Lervik", "Programmering i C++");
            id = db.insert("Mildrid Ljosland", "Algoritmer og datastrukturer");
            id = db.insert("Helge Hafting", "Algoritmer og datastrukturer");


            File dir = getFilesDir();
            File file = new File(dir, "data.txt");
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();
                Boolean authorB = true;
                String author = "";
                String title;
                while (line != null) {
                    if (authorB) {
                        author = line;
                        authorB = false;
                    } else {
                        title = line;
                        id = db.insert(author, title);
                        authorB = true;
                    }
                    line = bufferedReader.readLine();
                }

            } catch (IOException e) {
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                    }
                }
            }

            ArrayList<String> res = db.getAllBooks();
            showResults(res);
        }
        catch (Exception e) {
            String tekst = e.getMessage();
            TextView t = (TextView)findViewById(R.id.tw1);
            t.setText(tekst);
        }
    }
    public void showResults(ArrayList<String> list) {
        StringBuffer res = new StringBuffer("");
        for (String s : list)  {
            res.append(s+"\n");
        }
        TextView t = (TextView)findViewById(R.id.tw1);
        t.setText(res);
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String color = sharedPref.getString(Settings.LIST, "");

        if (color.equals("White")) {
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        } else if (color.equals("Green")) {
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        }else if (color.equals("Red")) {
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent("com.example.oving7.Settings");
            startActivity(i);
            return true;
        }

        if (id == R.id.action_authors) {
            Intent i = new Intent("com.example.oving7.AuthorActivity");
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}