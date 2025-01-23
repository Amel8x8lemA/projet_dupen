package com.example.projet_dupen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameplayActivity extends AppCompatActivity {

    private GameplayActivity activity;
    private TextView lettreDevine, motMystereDisplay;
    private mot mysteryWord;
    private boolean guessResult;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        this.activity = this;

        MyTask myTask = new MyTask(new OnPostExecutedCallback() {
            @Override
            public void onPostExecuted(String mot) {
                mysteryWord = new mot(mot); // TODO faire appel à une API qui génère des mots aléatoirement
                motMystereDisplay = (TextView) findViewById(R.id.mysteryWord);
                motMystereDisplay.setText(mysteryWord.getMotCache());
            }
        });

        myTask.execute();

        ImageButton boutonAccueil = (ImageButton) findViewById(R.id.homeButton);

        boutonAccueil.setOnClickListener(v -> {
            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
        });

    }

    public void displayGuessLetter(View v) {
        Button lettre = (Button) findViewById(v.getId());
        lettreDevine = (TextView) findViewById(R.id.letterGuess);

        lettreDevine.setText((String) lettre.getText());
    }

    public void guess(View v) {
        lettreDevine = (TextView) findViewById(R.id.letterGuess);
        TextView compteurErreur = (TextView) findViewById(R.id.errorCounter);

        guessResult = mysteryWord.checkLettreInMot((String)lettreDevine.getText());

        if (!lettreDevine.getText().equals("_")) {

            if (guessResult) {
                mysteryWord.addLettre((String)lettreDevine.getText()); // ajoute la lettre à la liste des lettres trouvées
                updateMysteryWord(); // appelle la fonction de mise à jour du mot à trou affiché

            } else {
                mysteryWord.incrementErrorCount(); // incrémente le compteur d'erreur
                compteurErreur.setText(String.valueOf(mysteryWord.getErrorCount())); // met à jour l'affichage du compteur d'erreur
                updatePenduImageDisplay(); // mise à jour de l'image du pendu affiché
            }

            Button boutonLettre = getButtonFromLetter((String)lettreDevine.getText());
            changeBoutonLettreState(boutonLettre); // appel de la fonction pour changer l'état du boutton lié à la lettre envoyée
            endGame(); // appel de la fonction qui déclenche la fin de partie (popup)
        }

        lettreDevine.setText("_");
    }

    public void updateMysteryWord() { // fonction de mise à jour du mot à trou affiché
        motMystereDisplay = (TextView) findViewById(R.id.mysteryWord);
        mysteryWord.updateMotCache();
        motMystereDisplay.setText(mysteryWord.getMotCache());
    }

    public Button getButtonFromLetter(String lettre) {
        lettre = lettre.toUpperCase();
        String boutonLettreId = "letter" + lettre;
        int id = getResources().getIdentifier(boutonLettreId, "id", getPackageName());
        return (Button) findViewById(id);
    }

    public void changeBoutonLettreState(Button boutonLettre) {
        if (guessResult) {
            boutonLettre.setBackgroundColor(0x66008D00); // change la couleur du bouton

        } else {
            boutonLettre.setBackgroundColor(0x668D0000); // change la couleur du bouton
        }
        boutonLettre.getBackground().setAlpha(140); // rend le bouton translucide
        boutonLettre.setEnabled(false); // désactive le bouton
    }

    public void updatePenduImageDisplay() {
        ImageView imagePendu = (ImageView) findViewById(R.id.imagePendu);

        int nbErrors = mysteryWord.getErrorCount();

        switch(nbErrors){
            case 1:
                imagePendu.setImageResource(R.drawable.pendu_etape1);
                break;
            case 2:
                imagePendu.setImageResource(R.drawable.pendu_etape2);
                break;
            case 3:
                imagePendu.setImageResource(R.drawable.pendu_etape3);
                break;
            case 4:
                imagePendu.setImageResource(R.drawable.pendu_etape4);
                break;
            case 5:
                imagePendu.setImageResource(R.drawable.pendu_etape5);
                break;
            case 6:
                imagePendu.setImageResource(R.drawable.pendu_etape6);
                break;
            case 7:
                imagePendu.setImageResource(R.drawable.pendu_etape7);
                break;
            case 8:
                imagePendu.setImageResource(R.drawable.pendu_etape8);
                break;
            case 9:
                imagePendu.setImageResource(R.drawable.pendu_etape9);
                break;
            case 10:
                imagePendu.setImageResource(R.drawable.pendu_etape10);
                break;
            case 11:
                imagePendu.setImageResource(R.drawable.pendu_etape11);
                break;
            case 12:
                imagePendu.setImageResource(R.drawable.pendu_etape12);
                break;
            default:
                imagePendu.setImageResource(R.drawable.pendu_etape0);
                break;
        }
    }

    private void endGame() {
        if (!mysteryWord.getMotCache().contains("_") || mysteryWord.getErrorCount() >= 12) { // si la partie est terminée (mot deviné ou trop d'erreurs), création de la popup de fin
            PopupTemplate endgamePopup = new PopupTemplate(activity);
            if (!mysteryWord.getMotCache().contains("_")) { // si c'est une victoire, on met le titre "VICTOIRE" en VERT
                endgamePopup.setTitle("VICTOIRE");
                endgamePopup.getTitleView().setTextColor(0xFF00FF00);
                MajStats(true); // Appel de la méthode de mise à jour des stats (après victoire)
            } else if (mysteryWord.getErrorCount() >= 12) { // si c'est une défaite, on met le titre "DÉFAITE" en rouge
                endgamePopup.setTitle("DÉFAITE");
                endgamePopup.getTitleView().setTextColor(0xFFFF0000);
                MajStats(false); // Appel de la méthode de mise à jour des stats (après défaite)
            }
            endgamePopup.setSubTitle("mot : " + mysteryWord.getMot() + "\n" + "erreurs : " + mysteryWord.getErrorCount() + "/12");
            endgamePopup.getHomeButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(homeIntent);
                }
            });
            endgamePopup.getRestartButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gameplayIntent = new Intent(getApplicationContext(), GameplayActivity.class);
                    startActivity(gameplayIntent);
                }
            });
            endgamePopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            endgamePopup.setCancelable(false);
            endgamePopup.build();
        }
    }

    private void MajStats(boolean Victoire) {

        // Chargement des statistiques existantes
        ArrayMap<String, int[]> loadedStats = StatsManager.loadStats(getApplicationContext());

        // Récupération de la clé (correspondant à la longueur du mot à deviner)
        String stat_key = String.valueOf(mysteryWord.getLongueurMot());

        // Vérifier si la stat_key existe déjà
        if (!loadedStats.containsKey(stat_key)) {
            // Si elle n'existe pas, on l'initialise avec des valeurs par défaut (0 victoires & 0 défaites)
            loadedStats.put(stat_key, new int[]{0, 0});
        }

        // Incrémentation du score
        if (Victoire) {
            // Incrémentation de la victoire
            int[] stats = loadedStats.get(stat_key);
            if (stats != null && stats.length == 2) {
                stats[0]++; // Incrémenter le nombre de victoires
            }
        } else {
            // Incrémentation de la défaite
            int[] stats = loadedStats.get(stat_key);
            if (stats != null && stats.length == 2) {
                stats[1]++; // Incrémenter le nombre de victoires
            }
        }

        // Enregistrement des statistiques mises à jour
        StatsManager.saveStats(getApplicationContext(), loadedStats);
    }

}