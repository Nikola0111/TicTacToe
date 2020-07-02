package com.example.iksoks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class PlayerNames extends AppCompatDialogFragment {
    private EditText editTextPlayer1;
    private EditText editTextPlayer2;

    private PlayerNameDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.player_name_layout,null);

        editTextPlayer1 = view.findViewById(R.id.player1EditText);
        editTextPlayer2 = view.findViewById(R.id.player2EditText);

        builder.setView(view).setTitle("Enter player names").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String player1 = editTextPlayer1.getText().toString();
                String player2 = editTextPlayer2.getText().toString();

                listener.applyText(player1, player2);
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PlayerNameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement PlayerNamesDialogListener!");
        }
    }

    public interface PlayerNameDialogListener{
        void applyText(String player1, String player2);
    }
}
