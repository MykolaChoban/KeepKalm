
package com.example.keepkalm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class DialogChuckNorrisJoke extends AppCompatDialogFragment {

    private TextView jokeTextView;
    private SharedPreferences prefs;
    private String userName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        prefs = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName = prefs.getString("user_full_name","Chuck Norris");

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_chuch_norris_joke, null);

        jokeTextView = view.findViewById(R.id.joke_text_view);

        fetchJoke(userName, jokeTextView);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Chuck Norris said:")
                .setNegativeButton("Done",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    public void fetchJoke(String userName, final TextView textView){
        //documentation http://www.icndb.com/api/
        String url = "https://api.icndb.com/jokes/random?firstName="+userName+"&lastName=";
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //ex: { "type": "success", "value": { "id": 388, "joke": "The Manhattan Project was not intended to create nuclear weapons, it was meant to recreate the destructive power in a Chuck Norris Roundhouse Kick. They didn't even come close.", "categories": [] } }
                            JSONObject jsonObjectResponse = response.getJSONObject("value");
                            textView.setText(jsonObjectResponse.getString("joke").replaceAll("  "," ").replaceAll("&quot;","\""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "An error occurred.");
            }
        });
        queue.add(request);
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
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;
                    //Do stuff, possibly set wantToCloseDialog to true then...
                    fetchJoke(userName, jokeTextView);
                    if(wantToCloseDialog)
                        d.dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }
    }
}