package com.example.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TetrisView mTetrisView;
    //private String mFinish;
    //private String mPause;
    //private String mRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTetrisView = (TetrisView)findViewById(R.id.tetrisView);
        Resources res = getResources();
        //mFinish = res.getString(R.string.finish);
        //mPause=res.getString(R.string.pause);
        //mRestart=res.getString(R.string.resume);
    }

    public void setAppLocale(String localeCode) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            conf.locale = new Locale(localeCode.toLowerCase());
        }
        res.updateConfiguration(conf, dm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_finish) {
            mTetrisView.getThread().setRunning(false);
            finish();
            return true;
        }

        if (id == R.id.action_pause) {
            mTetrisView.getThread().setPaused(true);
            return true;
        }

        if (id == R.id.action_norwegian) {
            setAppLocale("no");
            mTetrisView.getThread().setPaused(true);
            Intent i = new Intent("com.example.tetris.MainActivity");
            startActivity(i);
            return true;
        }

        if (id == R.id.action_english) {
            setAppLocale("en");
            mTetrisView.getThread().setPaused(true);
            Intent i = new Intent("com.example.tetris.MainActivity");
            startActivity(i);
            return true;
        }

        if (id == R.id.action_restart) {
            mTetrisView.getThread().setPaused(true);
            Intent i = new Intent("com.example.tetris.MainActivity");
            startActivity(i);
            return true;
        }

        if (id == R.id.action_help) {
            mTetrisView.getThread().setPaused(true);
            Intent i = new Intent("com.example.tetris.HelpActivity");
            startActivity(i);
            return true;
        }

        if (id == R.id.action_resume) {
            mTetrisView.getThread().setPaused(false);
            mTetrisView.setFocusable(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        mTetrisView.getThread().setPaused(true);
    }
}
