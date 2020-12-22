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

    // Register first click to generate mines
    boolean reset_game = true;
    // Game turn inactive when a mine is clicked
    boolean game_active = true;
    // Global Adapter
    CustomSquares adapter;

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
        adapter = new CustomSquares(this);
        adapter.setSquares(squares);

        // setting the gridview to be populated using Custom Adapter
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(adapter);

        // onclick for the grid pieces
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Check if the clicked field is a minefield
                checkMine(adapter, position);

                // if a mine has not been clicked the game is active
                if (game_active) {
                    // Change color to gray when clicked
                    adapter.getItem(position).setVisible(true);

                    // Generate mines on first click or when game is reset
                    if(reset_game){
                        generateMines(adapter,position);
                        reset_game=false;
                    }

                    Log.d("Click position = ", String.valueOf(position));

                    Log.d("Position = ", String.valueOf(position));

                }

                // Sync the changes with the ui
                adapter.notifyDataSetChanged();
            }
        });
    }

    // Check if the user clicked on a mine
    public void checkMine(CustomSquares adapter, int position){
        if (adapter.getItem(position).isMine()){
            revealBoard(adapter);
            game_active=false;
        }
    }

    // Reveal all the pieces in board ( When a mine is clicked )
    public void revealBoard(CustomSquares adapter){
        for (int i=0; i<100; i++){
            adapter.getItem(i).setVisible(true);
        }
    }

    // Generate 20 random mines on baord
    public void generateMines(CustomSquares adapter, int position){
        int randomNum;
        Random random = new Random();
        int i = 0;
        Log.d("Here = ", "function call");
        while(i<20){
            randomNum = random.nextInt(99);
            // Check if field is not a mine field, or the clicked position is not the selected position/
            if (!adapter.getItem(randomNum).isMine() && randomNum!=position){
                Log.d("Here = ", "in loop");
                adapter.getItem(randomNum).setMine(true);
                i++;
            }
        }
    }

    // Reset game when button is pressed
    public void resetGame(View v){
        // Reset the game variables
        reset_game = true;
        game_active = true;

        // Reset the board
        for (int i=0; i<100; i++){
            adapter.getItem(i).setVisible(false);
            adapter.getItem(i).setMine(false);
            adapter.getItem(i).setFlagged(false);
        }

        // Update the ui
        adapter.notifyDataSetChanged();

        Log.d("Game Reset = ", "Success");
    }




}