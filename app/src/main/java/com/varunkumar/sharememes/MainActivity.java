package com.varunkumar.sharememes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    String url, urlMain;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = " https://meme-api.com/gimme";
        imageView = findViewById(R.id.imageView);
        lottieAnimationView = findViewById(R.id.lottieAnimation);
        next(null);
    }

    void loadMeme() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            urlMain = response.getString("url");
                            Glide.with(MainActivity.this).load(urlMain).
                                    listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable final GlideException e,
                                                                    final Object model, final Target<Drawable> target,
                                                                    final boolean isFirstResource) {
                                            lottieAnimationView.setVisibility(View.INVISIBLE);

                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(final Drawable resource,
                                                                       final Object model,
                                                                       final Target<Drawable> target,
                                                                       final DataSource dataSource,
                                                                       final boolean isFirstResource) {
                                            lottieAnimationView.setVisibility(View.INVISIBLE);

                                            return false;
                                        }
                                    }).into(imageView);
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Something Went Wrong!☹", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void next(View view) {
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
        loadMeme();
       // lottieAnimationView.setVisibility(View.INVISIBLE);
    }

    public void share(View view)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this cool meme I got from Reddit " + urlMain);
        Intent chooser = Intent.createChooser(intent, "Share this meme using...");
        startActivity(chooser);
    }
}