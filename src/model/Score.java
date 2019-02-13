package model;

public class Score {
    private static Score score = new Score();
    private int value = 0;

    public static Score getObj() {
        return score;
    }

    public void update() {
        value++;
    }

    public int getValue() {
        return value;
    }

    private Score() {
    }
}
