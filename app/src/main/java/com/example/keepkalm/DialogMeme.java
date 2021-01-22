package com.example.keepkalm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class DialogMeme extends AppCompatDialogFragment {

    private ImageView imageMeme;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_meme , null);
        imageMeme = view.findViewById(R.id.imageMeme);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
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

        getMeme();

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
                    getMeme();
                    if(wantToCloseDialog)
                        d.dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }
    }

    private void displayMeme(ImageView imageView, String url){
        Picasso.get()
                .load(url)
                .into(imageView);
        imageView.setMinimumHeight(1000);
    }

    private void getMeme(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://meme-api.herokuapp.com/gimme";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //{"postLink":"https://redd.it/k4tjck","subreddit":"dankmemes","title":"humanity","url":"https://i.redd.it/opipssjjzm261.png","nsfw":false,"spoiler":false,"author":"islmakaha","ups":1550,"preview":["https://preview.redd.it/opipssjjzm261.png?width=108\u0026crop=smart\u0026auto=webp\u0026s=5426704f36ec8a58ea14d5bdb2f3bef1d345ff1c","https://preview.redd.it/opipssjjzm261.png?width=216\u0026crop=smart\u0026auto=webp\u0026s=88437148c3af6e1a642e9813d8cdd842e087e749","https://preview.redd.it/opipssjjzm261.png?width=320\u0026crop=smart\u0026auto=webp\u0026s=534d6608d7795f07ebec468fdfda76056714c730"]}
                            JSONObject JSONResponse = new JSONObject(response);
                            String imageUrl = JSONResponse.getString("url");
                            displayMeme(imageMeme, imageUrl);
                        }
                        catch(Exception e){
                            Log.e("debug", e.toString());
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


}
