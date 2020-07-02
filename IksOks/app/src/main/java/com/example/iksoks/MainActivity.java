package com.example.iksoks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PlayerNames.PlayerNameDialogListener, PopupMenu.OnMenuItemClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private String player1Name;
    private String player2Name;

    private int player1Points;
    private int player2Points;

    private TextView player1TextView;
    private TextView player2TextView;

    private int gameNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDialog();

        roundCount = 0;
        gameNum = 0;

        player1TextView = (TextView) findViewById(R.id.player1_textview);
        player2TextView = (TextView) findViewById(R.id.player2_textview);

        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")){
            return;
        }

        if(player1Turn){
            ((Button) view).setText("X");
        }else{
            ((Button) view).setText("O");
        }

        roundCount++;
        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }

            if(gameNum % 2 == 0) {
                player1Turn = true;
            }else{
                player1Turn = false;
            }
        }else if(roundCount == 9){
            draw();

            if(gameNum % 2 == 0) {
                player1Turn = true;
            }else{
                player1Turn = false;
            }
        }else{
            player1Turn = !player1Turn;
        }



    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];

        for(int i = 0;i < 3; i++){
            for(int j = 0;j < 3;j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for(int i = 0; i < 3; i++){
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
                && !field[i][0].equals("")){
                return true;
            }
        }

        for(int i = 0; i < 3; i++){
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }

        return false;
    }

    private void player1Wins(){
        roundCount = 0;

        gameNum++;


        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        player1TextView.setText(player1Name + ": " + (++player1Points));

        Toast.makeText(getBaseContext(),  player1Name + " wins!", Toast.LENGTH_SHORT).show();
    }

    private void player2Wins(){
        roundCount = 0;

        gameNum++;

        player1Turn = true;

        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        player2TextView.setText(player2Name + ": " + (++player2Points));

        Toast.makeText(getBaseContext(), player2Name + " wins!", Toast.LENGTH_SHORT).show();
    }

    private void draw(){
        roundCount = 0;

        gameNum++;

        if(gameNum % 2 == 0) {
            player1Turn = true;
        }else{
            player1Turn = false;
        }

        player1Turn = true;

        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3; j++){
                buttons[i][j].setText("");
            }
        }


        Toast.makeText(getBaseContext(), "Draw!", Toast.LENGTH_SHORT).show();
    }

    public void openDialog(){
        PlayerNames playerNames = new PlayerNames();
        playerNames.show(getSupportFragmentManager(), "Player name dialog");
    }

    @Override
    public void applyText(String player1, String player2) {
        player1Name = player1;
        player2Name = player2;

        TextView player1TextView = (TextView) findViewById(R.id.player1_textview);
        TextView player2TextView = (TextView) findViewById(R.id.player2_textview);

        player1TextView.setText(player1Name + ": 0");
        player2TextView.setText(player2Name + ": 0");
    }

    private void resetFunction(){
        gameNum = 0;

        roundCount = 0;

        player1Points = 0;
        player2Points = 0;

        player1Turn = true;

        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        player1TextView.setText(player1Name + ": 0");
        player2TextView.setText(player2Name + ": 0");
    }

    public void showMenu(View v){
        PopupMenu menu = new PopupMenu(this, v);
        menu.setOnMenuItemClickListener(this);
        menu.inflate(R.menu.popup_menu);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.change_names:
                openDialog();
                resetFunction();
                break;
            case R.id.reset_button:
                resetFunction();
                break;
        }

        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
        outState.putString("player1Name", player1Name);
        outState.putString("player2Name", player2Name);
        outState.putInt("gameNum", gameNum);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
        player1Name = savedInstanceState.getString("player1Name");
        player2Name = savedInstanceState.getString("player2Name");
        gameNum = savedInstanceState.getInt("gameNum");
    }
}
