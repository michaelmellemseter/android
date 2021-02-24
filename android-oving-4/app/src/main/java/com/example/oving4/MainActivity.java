package com.example.oving4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements Fragment1.Callback{
    int nr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onItemSelected(int value) {
        nr = value;
        Fragment2 frag = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        frag.showDetails(value);
    }

    public void updatePicture(int value) {
        Fragment2 frag = (Fragment2) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        frag.showDetails(value);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_next:
                if (nr < 3) {
                    nr += 1;
                    updatePicture(nr);
                }
                return true;
            case R.id.menu_previous:
                if (nr >  0) {
                    nr -= 1;
                    updatePicture(nr);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
