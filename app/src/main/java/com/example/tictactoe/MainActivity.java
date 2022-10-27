package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created By Aman Singh
 */
public class MainActivity extends AppCompatActivity {

    TextView winner_status_tv;
    ImageView game_image1, game_image2, game_image3, game_image4, game_image5, game_image6, game_image7, game_image8, game_image9;
    Button reset_button ,continue_button ;
    boolean gameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gameReset(view);
            }
        });
    }

    private void init() {
        game_image1 = findViewById(R.id.game_image_1);
        game_image2 = findViewById(R.id.game_image_2);
        game_image3 = findViewById(R.id.game_image_3);
        game_image4 = findViewById(R.id.game_image_4);
        game_image5 = findViewById(R.id.game_image_5);
        game_image6 = findViewById(R.id.game_image_6);
        game_image7 = findViewById(R.id.game_image_7);
        game_image8 = findViewById(R.id.game_image_8);
        game_image9 = findViewById(R.id.game_image_9);
        reset_button =findViewById(R.id.reset_button);
    }
     //player Representation
    // 0 is cross
    // 1 is zero
    int activePlayer = 0;

    //State meanings
    // 0 - x
    //1 - 0
    //2 - null
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winPositions = {{0,1,2} , {3,4,5}, {6,7,8} , {0,3,6} , {1,4,7} , {2,5,8} , {0,4,8} , {2,4,6}};

   public void playerTap(View view) {
       ImageView  img = (ImageView) view;
       int  tappedImage = Integer.parseInt(img.getTag().toString());
       if(!gameActive) {
           gameReset(view);
       }
       if(gameState[tappedImage] == 2) {
           gameState[tappedImage] = activePlayer;
           img.setTranslationY(-1000f);

           if (activePlayer == 0) {
               img.setImageResource(R.drawable.cross);
               activePlayer = 1;
               TextView status = findViewById(R.id.game_status);
               status.setText("O's Turn");
           } else {
               img.setImageResource(R.drawable.zero);
               activePlayer = 0;
               TextView status = findViewById(R.id.game_status);
               status.setText("X's Turn");
           }
           img.animate().translationYBy(1000f).setDuration(300);
       }
       //Check if any player has won
       for(int[] winPosition: winPositions) {
          if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
                gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[0]] != 2) {

              //someone has won
              String winnerStr;
              gameActive = false;
              if(gameState[winPosition[0]] == 0) {
                  winnerStr = "Winner is X";
              }else{
                  winnerStr = "Winner is O";
              }
              ViewDialog(winnerStr);
           }
       }

   }

    private void gameReset(View view) {
       gameActive = true;
       activePlayer =0;
       for (int i=0; i<gameState.length;i++){
           gameState[i] = 2;
       }
        ((ImageView) findViewById(R.id.game_image_1)).setImageResource(0);
        ((ImageView) findViewById(R.id.game_image_2)).setImageResource(0);
        ((ImageView) findViewById(R.id.game_image_3)).setImageResource(0);
        ((ImageView) findViewById(R.id.game_image_4)).setImageResource(0);
        ((ImageView) findViewById(R.id.game_image_5)).setImageResource(0);
        ((ImageView) findViewById(R.id.game_image_6)).setImageResource(0);
        ((ImageView) findViewById(R.id.game_image_7)).setImageResource(0);
        ((ImageView) findViewById(R.id.game_image_8)).setImageResource(0);
        ((ImageView) findViewById(R.id.game_image_9)).setImageResource(0);

        TextView status = findViewById(R.id.game_status);
        status.setText("X's Turn");
    }

    public void ViewDialog(String winnerStr) {

        Dialog dialogUpdate = new Dialog(MainActivity.this);
        dialogUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpdate.setContentView(R.layout.dialog_box);
        dialogUpdate.setCanceledOnTouchOutside(false);

        TextView dialogTextView = (TextView)  dialogUpdate.findViewById(R.id.winner_status_tv);
        dialogTextView.setText(winnerStr);
        Button sucessButton = (Button) dialogUpdate.findViewById(R.id.continue_button);
        sucessButton.setEnabled(true);

        sucessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate.dismiss();
                gameReset(view);
            }
        });

        //After Update dialog box
        dialogUpdate.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialogUpdate.show();
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth() - 40;
        int height = display.getHeight() - 200;
        Window window = dialogUpdate.getWindow();
        window.setLayout(width, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }



}
