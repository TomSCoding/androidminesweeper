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
import android.widget.TextView;
import android.widget.Toast;

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
    // Marking mode or uncover mode
    boolean uncover = true;
    // Places covered
    int covered_count = 0;
    // Check for game win
    boolean game_win;
    // game lost
    boolean game_lost = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a list of squares to populate the grid
        List<Square> squares = new ArrayList<>();
        for(int x=0; x<10; x++) {
            for(int y=0; y<10; y++) {
                // Assign the default element image from android
                squares.add(new Square(x,y, false, false, false, 0));
            }
        }

        // Set the mines number
        TextView mineButton = findViewById(R.id.score_title);
        mineButton.setText("Mines: 20");

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
                if(uncover && !adapter.getItem(position).isFlagged()){
                    checkMine(adapter, position);
                }

                // Check win
                checkWin();

                // if a mine has not been clicked the game is active
                if (game_active) {
                    // If its uncover mode, uncover the field clicked
                    if (uncover && !adapter.getItem(position).isFlagged()){
                        // Change color to gray when clicked
                        adapter.getItem(position).setVisible(true);
                    }

                    // Generate mines on first click or when game is reset
                    if(reset_game){
                        generateMines(adapter,position);
                        getNumberOfMines();
                        reset_game=false;
                    }
                    // If the mode is set to check, check fields
                    if (!uncover){
                        // Set the flagged value to true or false
                        if (adapter.getItem(position).isFlagged()){
                            adapter.getItem(position).setFlagged(false);
                            covered_count--;
                        } else {
                            adapter.getItem(position).setFlagged(true);
                            covered_count++;
                        }
                        // Display the covered fields
                        TextView mineButton = findViewById(R.id.covered_title);
                        mineButton.setText("Covered: "+String.valueOf(covered_count));
                    }
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
            game_lost=true;
        }
    }

    // Get number of mines around field and record them
    public void getNumberOfMines(){
        // Check indexes
        Integer[] arrIndexes = {-11, -10, -9, -1, 1, 9, 10, 11};
        // loop through indexes
        for (int i=0; i<100; i++){
            // Check if number is within range
            int mine_count = 0;
            // Check for all the values around to see if they contain a mine
            for (int current : arrIndexes){
                int tempI = i;
                tempI = current+tempI;
                if (0 < tempI && tempI < 100){
                    if (adapter.getItem(tempI).isMine()){
                        mine_count++;
                    }
                }
            }
            adapter.getItem(i).setMine_number(mine_count);
            Log.d("Mine count = ", String.valueOf(mine_count));
        }
    }

    // Reveal all the pieces in board ( When a mine is clicked )
    public void revealBoard(CustomSquares adapter){
        for (int i=0; i<100; i++){
            adapter.getItem(i).setVisible(true);
            adapter.getItem(i).setFlagged(false);
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
        game_lost = false;
        game_win = false;
        covered_count = 0;
        TextView mineButton = findViewById(R.id.covered_title);
        mineButton.setText("Covered: "+String.valueOf(covered_count));

        // Reset the board
        for (int i=0; i<100; i++){
            adapter.getItem(i).setVisible(false);
            adapter.getItem(i).setMine(false);
            adapter.getItem(i).setFlagged(false);
            adapter.getItem(i).setMine_number(0);
        }

        // Update the ui
        adapter.notifyDataSetChanged();

        Log.d("Game Reset = ", "Success");
    }

    // Reset game when button is pressed
    public void changeMode(View v){
        // Reset the game variables
        uncover = !uncover;

        // Change text on button
        Button btn = findViewById(R.id.mode_btn);

        if (uncover) {
            btn.setText(R.string.mark_mode);
            Log.d("Mode changed to = ", "Uncover");
        } else {
            btn.setText(R.string.uncover_mode);
            Log.d("Mode changed to = ", "Mark");
        }

    }

    // Check win
    public void checkWin(){
        game_win = true;
        for (int i=0; i<100; i++){
            if(!adapter.getItem(i).isMine() && !adapter.getItem(i).isVisible()){
                game_win=false;
            }
        }
        // If player won , stop the game and display victory
        if (game_win&&!game_lost){
            game_active=false;
            Toast toast = Toast.makeText(getApplicationContext(), "Game Over, You Won!!!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}