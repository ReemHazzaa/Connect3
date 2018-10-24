package com.example.reem.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0: yellow; 1: red; 2:empty

    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    int activePlayer = 0;

    boolean gameActive = true;

    public void dropIn(View view) {
        // The "counter" var is to check which image is pressed.
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;

            //Dropping the counter in:
            //1. Translate it outside the screen
            counter.setTranslationY(-1500);
            //2. Set the img resource
            if (activePlayer == 1) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            } else {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            }
            //3. Drop the img down
            counter.animate().translationYBy(1500).rotation(3600).setDuration(400);

            // loop through winning positions & check if a player has won
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    String winner = "";
                    //Someone has won
                    gameActive = false;

                    if (activePlayer == 1) {
                        //The yellow won
                        winner = "Yellow";
                    } else {
                        winner = "Red";
                    }
                    //Toast.makeText(this, winner + " has won!", Toast.LENGTH_SHORT).show();

                    Button playAgainButton = findViewById(R.id.playAgainButton);
                    TextView winnerTextView = findViewById(R.id.winnerTextView);

                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setText(winner + " has won!");
                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view){
        // Hide play again button and winner txtview
        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        // loop through the gridLayout and remove all img sources
        android.support.v7.widget.GridLayout gridLayout = (android.support.v7.widget.GridLayout)findViewById(R.id.gridLayout);
        //GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            // remove all img sources
            counter.setImageDrawable(null);
        }
        // Reset gameState variables
        for(int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        activePlayer = 0;

        gameActive = true;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
