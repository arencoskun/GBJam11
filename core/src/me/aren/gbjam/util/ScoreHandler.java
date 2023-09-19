package me.aren.gbjam.util;

public class ScoreHandler {
    int score;
    public ScoreHandler() {
        score = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getScoreString() {
        return String.valueOf(score);
    }

    public int incrementScore(int step) {
        score += step;
        return score;
    }
}
