package com.example.projet_dupen;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class mot {

    private final String mot, motSansAccent;
    private String motCache;
    private final List<String> lettresTrouvees;
    private int errorCount;

    public mot(String mot) {
        this.mot = mot;
        this.lettresTrouvees = new ArrayList<>();
        this.motSansAccent = supprAccent(mot.toLowerCase());
        this.motCache = hide(mot);
        this.errorCount = 0;
    }

    public String getMot() {
        return mot;
    }

    public int getLongueurMot() {
        return mot.length();
    }

    public String getMotSansAccent() {
        return motSansAccent;
    }

    public String getMotCache() {
        return motCache;
    }

    public void setMotCache(String motCache) {
        this.motCache = motCache;
    }

    public static String supprAccent(String s) {
        String strTemp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(strTemp).replaceAll("");
    }

    public String hide(String mot) {
        int tailleMot = mot.length();
        StringBuilder motCache = new StringBuilder();
        addLettre("-");
        for (int i=0 ; i<tailleMot ; i++) {

            if (Character.toString(this.motSansAccent.charAt(i)).equals("-")) {
                motCache.append("-");
            } else { motCache.append("_"); }

            if (i<tailleMot-1) {
                motCache.append(" ");
            }
        }
        return motCache.toString();
    }

    public void addLettre(String lettre) {
        lettre = lettre.toLowerCase();
        this.lettresTrouvees.add(lettre);
    }

    public boolean checkLettreInMot(String lettre) {
        lettre = lettre.toLowerCase();
        return this.getMotSansAccent().contains(lettre);
    }

    public void updateMotCache() {
        int tailleMot = this.motSansAccent.length();
        StringBuilder motCache = new StringBuilder();
        for (int i=0 ; i<tailleMot ; i++) {

            if (this.lettresTrouvees.contains(Character.toString(this.motSansAccent.charAt(i)))) {
                motCache.append(this.motSansAccent.charAt(i));
            } else { motCache.append("_"); }

            if (i < tailleMot - 1) {
                motCache.append(" ");
            }
        }
        setMotCache(motCache.toString().toUpperCase());
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void incrementErrorCount() {
        this.errorCount += 1;
    }
}
