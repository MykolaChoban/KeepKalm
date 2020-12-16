package com.example.keepkalm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DialogRandom extends AppCompatDialogFragment {

    private TextView randomText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_random , null);
        randomText = view.findViewById(R.id.randomText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Did you know that...")
                .setNegativeButton("No more",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Another one", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        setRandomText(randomText);

        return builder.create();
    }

    //  https://stackoverflow.com/questions/2620444/how-to-prevent-a-dialog-from-closing-when-a-button-is-clicked
    //onStart() is where dialog.show() is actually called on
    //the underlying dialog, so we have to do it there or
    //later in the lifecycle.
    //Doing it in onResume() makes sure that even if there is a config change
    //environment that skips onStart then the dialog will still be functioning
    //properly after a rotation.
    @Override
    public void onResume()
    {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null){
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            Button neutralButton = (Button) d.getButton(Dialog.BUTTON_NEUTRAL);

            neutralButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;
                    String messageHeader = "Hey, did you know that ";
                    String messageFooter = "\n#App #KeepKalm ";
                    shareText(messageHeader + randomText.getText().toString() + messageFooter);
                    if(wantToCloseDialog)
                        d.dismiss();
                }
            });
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;
                    //Do stuff, possibly set wantToCloseDialog to true then...
                    setRandomText(randomText);
                    if(wantToCloseDialog)
                        d.dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }
    }

    private void setRandomText(final TextView textView){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://uselessfacts.jsph.pl/random.json?language=en";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //{"postLink":"https://redd.it/k4tjck","subreddit":"dankmemes","title":"humanity","url":"https://i.redd.it/opipssjjzm261.png","nsfw":false,"spoiler":false,"author":"islmakaha","ups":1550,"preview":["https://preview.redd.it/opipssjjzm261.png?width=108\u0026crop=smart\u0026auto=webp\u0026s=5426704f36ec8a58ea14d5bdb2f3bef1d345ff1c","https://preview.redd.it/opipssjjzm261.png?width=216\u0026crop=smart\u0026auto=webp\u0026s=88437148c3af6e1a642e9813d8cdd842e087e749","https://preview.redd.it/opipssjjzm261.png?width=320\u0026crop=smart\u0026auto=webp\u0026s=534d6608d7795f07ebec468fdfda76056714c730"]}
                            JSONObject JSONResponse = new JSONObject(response);
                            String textResponse = JSONResponse.getString("text");
                            textView.setText(textResponse);
                        }
                        catch(Exception e){
                            Log.i("debug", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("debug",e.toString());
            }
        });
        queue.add(stringRequest);
    }

    private void shareText(String textToShare){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}