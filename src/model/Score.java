package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.prefs.Preferences;

public class Score {
    private static Score score = new Score();
    private int value = 0;

    private ArrayList<Integer> bestScores = new ArrayList<>();
    private Preferences pref = Preferences.userNodeForPackage(Score.class);

    public static Score getObj() {
        return score;
    }

    public void update() {
        value++;
    }

    public void resetScore() {
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public ArrayList<Integer> getBestScores() {
        updateInfo();
        return bestScores;
    }


    public int getBestScore() {
        return getBestScores().get(0);
    }

    public void save() {
        updateInfo();
        bestScores.add(value);
        savePref();
    }

    private void updateInfo() {
        bestScores = getArrayListFromString(pref.get("bestScores", ""));
        Collections.sort(bestScores);
        Collections.reverse(bestScores);
        if(bestScores.size() > 50){
            bestScores.removeAll(bestScores.subList(50, bestScores.size() - 1));
        }
    }

    private void savePref() {
        pref.put("bestScores", bestScores.toString());
    }

    private ArrayList<Integer> getArrayListFromString(String listString) {
        ArrayList<Integer> list = new ArrayList<>();

        listString = listString.substring(1, listString.length() - 1);

        String[] items = listString.split(", ");

        for (String item : items) {
            list.add(Integer.parseInt(item));
        }

        return list;
    }

    private Score() {
    }
}
