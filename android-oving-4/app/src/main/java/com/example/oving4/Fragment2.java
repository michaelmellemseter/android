package com.example.oving4;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;

public class Fragment2 extends Fragment {
    int imageId;
    String name;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment2, container, false);
    }
    public void showDetails(int nr) {
        Resources res= getResources();

        TypedArray images = res.obtainTypedArray(R.array.pictures);
        ImageView imageView = (ImageView)getView().findViewById(R.id.bilde);
        imageView.setImageDrawable(images.getDrawable(nr));

        String[] desc = res.getStringArray(R.array.desc);
        TextView descView = (TextView)getView().findViewById(R.id.beskrivelse);
        descView.setText(desc[nr]);
    }


}
