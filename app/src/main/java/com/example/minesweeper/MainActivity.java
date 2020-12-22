package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a list of squares to populate the grid
        List<Square> squares = new ArrayList<>();
        for(int x=0; x<10; x++) {
            for(int y=0; y<10; y++) {
                // Assign the default element image from android
                squares.add(new Square(x,y, false, false, false));
            }
        }

        // add squares to custom adapter
        CustomSquares adapter = new CustomSquares(this);
        adapter.setSquares(squares);

        // setting the gridview to be populated using Custom Adapter
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(adapter);

    }



}