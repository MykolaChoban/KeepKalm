package com.example.keepkalm;

import androidx.appcompat.app.AppCompatDialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;

public class DialogDecision extends AppCompatDialogFragment {

    public static RequestQueue requestQueue;
    public static String decisionMakerApiImage = null;
    ImageView imageView;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_decision, null);

        requestQueue = Volley.newRequestQueue(getContext());
        imageView = view.findViewById(R.id.gif_here);
        getDecisionGIF("https://yesno.wtf/api/");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("The answer is...")
                .setNegativeButton("Got it! ",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }

     private void loadImage(String url){
        Glide
                .with(this)
                .load(url)
                .into(imageView);
    }

    private String getDecisionGIF(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    decisionMakerApiImage =  response.getString("image");
                    loadImage( response.getString("image"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API ERROR", "Could not fetch the API data:" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
        return decisionMakerApiImage;
    }

}