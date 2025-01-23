package com.example.projet_dupen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ImageButton boutonAccueil = (ImageButton) findViewById(R.id.homeButton);

        boutonAccueil.setOnClickListener(v -> {
            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
        });

        ArrayMap<String, int[]> loadedStats = StatsManager.loadStats(getApplicationContext());

        TableLayout statsTable = findViewById(R.id.statsTable);

        // Parcour des statistiques et ajout des lignes au tableau
        for (Map.Entry<String, int[]> entry : loadedStats.entrySet()) {
            String wordLength = entry.getKey();
            int[] stats = entry.getValue();
            int victories = stats[0];
            int defeats = stats[1];

            // Création d'une nouvelle ligne
            TableRow row = new TableRow(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.25f);

            row.setBackgroundColor(Color.WHITE);
            row.setPadding(8, 8, 8, 8);

            // Ajout des colonnes
            TextView wordLengthView = new TextView(this);
            wordLengthView.setText(wordLength + " lettres");
            wordLengthView.setTextColor(Color.BLACK);
            wordLengthView.setGravity(Gravity.CENTER);
            wordLengthView.setTypeface(null, Typeface.BOLD);
            wordLengthView.setLayoutParams(params);

            TextView victoriesView = new TextView(this);
            victoriesView.setText(String.valueOf(victories));
            victoriesView.setTextColor(Color.parseColor("#00C200"));
            victoriesView.setGravity(Gravity.CENTER);
            victoriesView.setTypeface(null, Typeface.BOLD);
            victoriesView.setLayoutParams(params);

            TextView defeatsView = new TextView(this);
            defeatsView.setText(String.valueOf(defeats));
            defeatsView.setTextColor(Color.parseColor("#C20000"));
            defeatsView.setGravity(Gravity.CENTER);
            defeatsView.setTypeface(null, Typeface.BOLD);
            defeatsView.setLayoutParams(params);

            TextView winrateView = new TextView(this);
            if(defeats==0) {
                winrateView.setText("100%");
            } else if(victories==0) {
                winrateView.setText("0%");
            } else {
                double totalgames = victories +defeats;
                double percentage = (victories / totalgames) * 100;
                String displayText = String.format("%.1f%%", percentage);
                winrateView.setText(displayText);
            }
            winrateView.setTextColor(Color.BLACK);
            winrateView.setGravity(Gravity.CENTER);
            winrateView.setTypeface(null, Typeface.BOLD);
            winrateView.setLayoutParams(params);

            // Ajout des TextViews dans la ligne
            row.addView(wordLengthView);
            row.addView(victoriesView);
            row.addView(defeatsView);
            row.addView(winrateView);

            // Ajout de la ligne au tableau
            statsTable.addView(row);
        }

    }
}