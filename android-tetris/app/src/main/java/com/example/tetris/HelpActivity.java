package com.example.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {
    private TetrisView mTetrisView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        mTetrisView = (TetrisView)findViewById(R.id.tetrisView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_tilbake) {
            mTetrisView.getThread().setPaused(false);
            mTetrisView.setFocusable(true);
            Intent i = new Intent("com.example.tetris.MainActivity");
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
