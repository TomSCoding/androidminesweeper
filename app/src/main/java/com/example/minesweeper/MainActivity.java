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

    boolean game_active;
    // Main Array that tracks moves
    Square[][] move_board;
    // Register first click
    boolean reset_game = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the game to active
        game_active = true;



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

        // onclick for the grid pieces
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Change color to gray when clicked
                adapter.getItem(position).setVisible(true);

                Log.d("Click position = ", String.valueOf(position));

                Log.d("Position = ", String.valueOf(position));
                adapter.notifyDataSetChanged();

            }
        });
    }



}