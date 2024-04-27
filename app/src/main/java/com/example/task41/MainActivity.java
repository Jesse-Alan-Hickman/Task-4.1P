package com.example.task41;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userNameEditText = findViewById(R.id.userName);
        Button startButton=findViewById(R.id.signInButton);
        String userName = getIntent().getStringExtra("user");

        if (userName != null && !userName.isEmpty()) {
            userNameEditText.setText(userName);
        }
        else {
            userNameEditText.setHint("Enter your username here");
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = userNameEditText.getText().toString();

                if (!userName.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, HomePageActivity.class); //Create new Explicit intent
                    intent.putExtra("user", userName);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter your username.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}