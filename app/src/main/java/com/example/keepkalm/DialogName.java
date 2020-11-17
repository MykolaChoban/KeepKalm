package com.example.keepkalm;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class DialogName extends AppCompatDialogFragment {

    private SharedPreferences prefs;
    private EditText nameEditText;
    private Intent mainActivityIntent;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("Debug message", "dialog executed");
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.activity_dialog_name, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Who are you?")
                /*.setPositiveButton("Validate my name", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })*/;

        mainActivityIntent = new Intent(getContext(), MainActivity.class);
        nameEditText = view.findViewById(R.id.edit_person_name);

        nameEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    if (!nameEditText.getText().toString().matches("")){
                        prefs = getContext().getSharedPreferences("userInfo",android.content.Context.MODE_PRIVATE); //PreferenceManager.getDefaultSharedPreferences( getContext() );
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("user_full_name",nameEditText.getText().toString());
                        editor.apply();
                        startActivity(mainActivityIntent);
                    }else{
                        //animation: https://github.com/daimajia/AndroidViewAnimations
                        YoYo.with(Techniques.Shake).duration(200).repeat(5).playOn(nameEditText);
                        Toast.makeText(getContext(),"Please, don't skip me please",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        return builder.create();
    }
}