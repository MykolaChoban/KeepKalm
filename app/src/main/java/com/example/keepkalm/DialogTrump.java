package com.example.keepkalm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DialogTrump extends AppCompatDialogFragment {

    private TextView quote_TextView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_trump_quote , null);
        quote_TextView = view.findViewById(R.id.quote_TextView);

        setTrumpQuote();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Donald trump said:")
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
                    setTrumpQuote();
                    if(wantToCloseDialog)
                        d.dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }
    }

    public void setTrumpQuote(){
        //documentation https://www.tronalddump.io/

        //String url = "https://www.tronalddump.io/random/quote";
        String url = "https://api.whatdoestrumpthink.com/api/v1/quotes/random";

        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley", response.toString());
                        //{"appeared_at":"2016-02-12T19:45:53.000Z","created_at":"2019-12-13T16:58:39.994Z","quote_id":"CL9VtedcQDO99eRYYKvmew","tags":["Ted Cruz"],"updated_at":"2019-12-13T17:26:27.045Z","value":"If @TedCruz doesn’t clean up his act, stop cheating, & doing negative ads, I have standing to sue him for not being a natural born citizen.","_embedded":{"author":[{"author_id":"wVE8Y7BoRKCBkxs1JkqAvw","bio":null,"created_at":"2019-12-13T16:43:24.728Z","name":"Donald Trump","slug":"donald-trump","updated_at":"2019-12-13T16:43:24.728Z","_links":{"self":{"href":"http://www.tronalddump.io/author/wVE8Y7BoRKCBkxs1JkqAvw"}}}],"source":[{"created_at":"2019-12-13T16:53:10.990Z","filename":null,"quote_source_id":"rMwWqTIlTAGZEWi90v6Q6Q","remarks":null,"updated_at":"2019-12-13T16:53:10.990Z","url":"https://twitter.com/realDonaldTrump/status/698231571594276866","_links":{"self":{"href":"http://www.tronalddump.io/quote-source/rMwWqTIlTAGZEWi90v6Q6Q"}}}]},"_links":{"self":{"href":"http://www.tronalddump.io/quote/CL9VtedcQDO99eRYYKvmew"}}}
                        try {
                            quote_TextView.setText(response.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "An error occurred.");
            }
        }){
            //FIXME: without it, the request response is 400, i checked the header of postman and find out this and it works
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
                return params;
            }
        };
        queue.add(request);
    }
}
