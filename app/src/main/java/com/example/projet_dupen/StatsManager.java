package com.example.projet_dupen;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;
import androidx.annotation.NonNull;
import java.util.Map;
import java.util.Arrays;

public class StatsManager {

    private static final String PREFS_NAME = "StatsPrefs";

    // Méthode pour enregistrer les statistiques
    public static void saveStats(@NonNull Context context, @NonNull ArrayMap<String, int[]> stats) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convertir chaque tableau d'entiers en une chaîne de caractères et les enregistrer
        for (Map.Entry<String, int[]> entry : stats.entrySet()) {
            String key = entry.getKey();
            int[] value = entry.getValue();
            String stringValue = Arrays.toString(value); // Convertir le tableau d'entiers en une chaîne de caractères
            editor.putString(key, stringValue); // Enregistrer la chaîne de caractères dans les préférences partagées
        }

        editor.apply();
    }


    // Méthode pour charger les statistiques
    public static ArrayMap<String, int[]> loadStats(@NonNull Context context) {
        ArrayMap<String, int[]> stats = new ArrayMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            String stringValue = (String) entry.getValue();
            int[] value = convertStringToIntArray(stringValue); // Convertir la chaîne de caractères en un tableau d'entiers
            stats.put(key, value);
        }

        return stats;
    }

    // Méthode pour convertir une chaîne de caractères en un tableau d'entiers
    private static int[] convertStringToIntArray(String stringValue) {
        stringValue = stringValue.replace("[", "").replace("]", ""); // Retirer les crochets de début et de fin
        String[] stringArray = stringValue.split(", "); // Séparer la chaîne de caractères en éléments individuels
        int[] intArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]); // Convertir chaque élément en entier
        }
        return intArray;
    }

}