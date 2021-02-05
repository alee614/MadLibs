package com.example.madlibs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class InputActivity extends AppCompatActivity {

    // from the layout
    private Button generate;
    private LinearLayout linearLayout;

    // receive Intent
    private String receivedMessage;
    private ArrayList<String> blanks;
    private ArrayList<String> value;
    private TextView heading;

    // initalized
    private String story = "";
    private ArrayList<TextInputEditText> inputs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // grab the linearview
        linearLayout = findViewById(R.id.inputs);
        generate = findViewById(R.id.button_generate);

        heading = findViewById(R.id.heading);

        // receive Intent "blanks"
        Intent intent = getIntent();
        receivedMessage = intent.getStringExtra("title");
        blanks = intent.getStringArrayListExtra("blanks");
        value = intent.getStringArrayListExtra("value");

        // will give error if there is nothing in here
//        Log.d("Data from Main activity", receivedMessage);
//        Log.d("Blanks from main", Integer.toString(blanks.size()));
//        Log.d("Value from main", value.toString());

//         for every blank, create a new TextInput inside linearview
        heading.setText(receivedMessage);

        if (blanks != null) {
            for (int i = 0; i < blanks.size(); i++) {
                TextInputLayout box = new TextInputLayout(this);
                box.setHint(blanks.get(i));

                TextInputEditText editText = new TextInputEditText(box.getContext());
                box.addView(editText);

                // arraylist to put editText
                inputs.add(editText);
                linearLayout.addView(box);
            }
        } else {
            Log.e("Error", "No blanks pulled from API.");
        }

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check to see if the inputs are all there
                if (allInputted()){
                    // Log.d("inputs", "all there sis");
                    launchNextActivity(v);
                }
                else{
                    toastWarning(v);
                }

            }
        });

    }

    public boolean allInputted(){
        for (TextInputEditText editText : inputs) {
//            Log.d("length: ", String.valueOf(editText.getText().length()));
            if (editText.getText().length() == 0){
                return false;
            }
        }
       return true;

    }


    public void launchNextActivity(View view){

        for ( int i = 0; i < inputs.size(); i++){
            // grab from the values ArrayList first
            String sentence = value.get(i);
            story = story + sentence;
            Log.d("value", sentence);
            // grab the textinput
            // clean it
            TextInputEditText input = inputs.get(i);
            String description = input.getText().toString().trim();
            // concat it
            story = story + description;
            Log.d("input", description);
        }

        Log.d("total story", story);
        finalIntent(story);

        // grab each editText
        // loop through

        // create an arraylist of editText with


    }
    // toast if not all the info is filled out

    public void toastWarning(View view){
        CharSequence text = "Missing field";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

//        Log.d("toast", "should work");

    }

    public void finalIntent(String extra){
        Intent intent = new Intent(InputActivity.this, Result.class);
        Log.d("story", extra);
        intent.putExtra("story", extra);
        startActivity(intent);

    }
}