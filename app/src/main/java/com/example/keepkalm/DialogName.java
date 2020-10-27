package com.example.keepkalm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import static android.provider.Contacts.SettingsColumns.KEY;

public class DialogName extends AppCompatDialogFragment {
    private SharedPreferences prefs;
    private EditText nameEditText;
    private Intent mainActivityIntent;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.activity_dialog_name, null);

        builder.setView(view)
                .setTitle("Who are you?")
                /*.setPositiveButton("Validate my name", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!nameEditText.getText().toString().matches("")){
                            SharedPreferences prefs = getContext().getSharedPreferences("userInfo",android.content.Context.MODE_PRIVATE); //PreferenceManager.getDefaultSharedPreferences( getContext() );
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("user_full_name",nameEditText.getText().toString());
                            System.out.println("lol" + nameEditText.getText().toString());
                            editor.commit();
                            editor.apply();
                            startActivity(mainActivityIntent);
                        }else{
                            //animation: https://github.com/daimajia/AndroidViewAnimations
                            YoYo.with(Techniques.Shake).duration(200).repeat(5).playOn(nameEditText);
                            Toast.makeText(getContext(),"Don't skip me... I do it for you!",Toast.LENGTH_SHORT).show();
                        }
                    }
                })*/;

        mainActivityIntent = new Intent(getContext(), MainActivity.class);
        nameEditText = view.findViewById(R.id.edit_person_name);
        nameEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    if (!nameEditText.getText().toString().matches("")){
                        SharedPreferences prefs = getContext().getSharedPreferences("userInfo",android.content.Context.MODE_PRIVATE); //PreferenceManager.getDefaultSharedPreferences( getContext() );
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("user_full_name",nameEditText.getText().toString());
                        System.out.println("lol"+nameEditText.getText().toString());
                        editor.commit();
                        editor.apply();
                        startActivity(mainActivityIntent);
                    }else{
                        //animation: https://github.com/daimajia/AndroidViewAnimations
                        YoYo.with(Techniques.Shake).duration(200).repeat(5).playOn(nameEditText);
                        Toast.makeText(getContext(),"Don't skip me... I do it for you!",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        return builder.create();
    }
}