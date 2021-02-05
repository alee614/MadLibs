package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Result extends AppCompatActivity {
    String story;
    Button home;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        home = findViewById(R.id.Home);
        result = findViewById(R.id.result);

        Intent intent = getIntent();
        story = intent.getStringExtra("story");

        result.setText(story);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome(v);
            }
        });

    }

    public void goHome(View view){
        Intent intent = new Intent(Result.this, MainActivity.class);
        startActivity(intent);
    }
}