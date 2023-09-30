package me.aren.gbjam.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreHandler {
    int score;
    Preferences preferences;
    public ScoreHandler() {
        score = 0;
        preferences = Gdx.app.getPreferences("Galaxy Miner");
        if(preferences.getInteger("highscore") == -1) preferences.putInteger("highscore", 0);
        preferences.flush();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setHighscore(int highscore) {
        System.out.println(preferences.getInteger("highscore"));
        if(highscore > preferences.getInteger("highscore")) {
            preferences.putInteger("highscore", highscore);
            preferences.flush();
        }
    }

    public int getHighScore() {
        return preferences.getInteger("highscore");
    }

    public String getHighScoreString() {
        return String.valueOf(getHighScore());
    }

    public String getScoreString() {
        return String.valueOf(score);
    }

    public int incrementScore(int step) {
        score += step;
        return score;
    }
}
