package com.example.projet_dupen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageButton boutonPlay = (ImageButton) findViewById(R.id.playButton);
        ImageButton boutonStats = (ImageButton) findViewById(R.id.statsButton);

        boutonPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gameplayIntent = new Intent(getApplicationContext(), GameplayActivity.class);
                startActivity(gameplayIntent);
            }
        });

        boutonStats.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent statsIntent = new Intent(getApplicationContext(), StatsActivity.class);
                startActivity(statsIntent);
            }
        });
    }
}