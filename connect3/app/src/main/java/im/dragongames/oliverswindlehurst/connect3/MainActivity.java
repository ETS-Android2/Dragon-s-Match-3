package im.dragongames.oliverswindlehurst.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private AdView bannerAdView;

    //0 = Yellow and 1 is Red
    int activePlayer = 0;

    boolean gameIsActive = true;

    //2 means unplayed
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    //This is a multi-dimentional array to list all the possible win conditions
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    //This is the button click function that has been named "dropIn"
    public void dropIn (View view)
    {
        ImageView counter = (ImageView)view;

        int tapedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tapedCounter] == 2 && gameIsActive)
        {
            gameState[tapedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0)
            {
                counter.setImageResource(R.drawable.yellowtile);
                activePlayer = 1;
            }
            else
            {
                counter.setImageResource(R.drawable.redtile);
                activePlayer = 0;
            }

            counter.animate().translationY(5f).rotation(3600f).setDuration(600);

            for (int [] winningPosition : winningPositions)
            {
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2)

                {
                    String winner = "Red";

                    if(gameState[winningPosition[0]] == 0)
                    {
                        winner = "Yellow";
                    }

                    //Someone has won
                    gameIsActive = false;

                    TextView winnerTextView = findViewById(R.id.winnerTextView);
                    winnerTextView.setText(winner + " Has Won!!");


                    LinearLayout winnerLayout = findViewById(R.id.winnerLayout);
                    winnerLayout.setVisibility(View.VISIBLE);
                    }
                    else
                {
                    boolean gameIsOver = true;

                    for (int counterState : gameState)
                    {
                        if (counterState == 2) gameIsOver = false;
                    }
                    if (gameIsOver)
                    {
                        TextView winnerTextView = findViewById(R.id.winnerTextView);
                        winnerTextView.setText("It's a draw!");


                        LinearLayout winnerLayout = findViewById(R.id.winnerLayout);
                        winnerLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        //else
        //{
        //    Toast.makeText(getApplicationContext(),"That space is already taken", Toast.LENGTH_SHORT).show();
        //}
    }

    public void playAgain (View view)
    {
        gameIsActive = true;

        LinearLayout winnerLayout = findViewById(R.id.winnerLayout);
        winnerLayout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++)
        {
            gameState[i] = 2;
        }

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++)
        {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Banner Ad Code
        bannerAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAdView.loadAd(adRequest);
    }
}
