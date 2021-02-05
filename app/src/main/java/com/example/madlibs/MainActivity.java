package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String api_call = "http://madlibz.herokuapp.com/api/random";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.button);

        start.setOnClickListener(v -> {
            getMadLibs(v);
        } );


    }

    public void getMadLibs(View view){
        client.get(api_call, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // make json object from string
                    // make into string for intent
                    JSONObject json = new JSONObject(new String(responseBody));
                    Intent intent = new Intent(MainActivity.this, InputActivity.class);

                    // get the blanks from JSON object and put into ArrayList
                    ArrayList<String> blanks = new ArrayList<>();
                    JSONArray jsonBlanks = json.getJSONArray("blanks");

                    for (int i = 0; i < jsonBlanks.length(); i++){
                        String article = jsonBlanks.getString(i);
                        blanks.add(article);
                    }
//                    Log.d("blanks", blanks.toString());

                    // get the story from JSONobject and put into ArrayList
                    ArrayList<String> story = new ArrayList<>();
                    JSONArray jsonValues = json.getJSONArray("value");

                    for (int i = 0; i < jsonValues.length(); i++){
                        String line = jsonValues.getString(i);
                        story.add(line);
                    }
//                    Log.d("values", story.toString());


                    intent.putExtra("title", json.getString("title"));
                    intent.putStringArrayListExtra("blanks", blanks);
                    intent.putStringArrayListExtra("value", story);

//                    intent.putExtra("blanks", json.getString("blanks"));
//                    intent.putExtra("value", json.getString("value"));


//                    String response = new String(responseBody);
//                    Log.d("story", response);
                    startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("error", String.valueOf(statusCode));

            }
        });
    }
}