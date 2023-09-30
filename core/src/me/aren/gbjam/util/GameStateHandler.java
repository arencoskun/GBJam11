package me.aren.gbjam.util;

import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.screens.GameOverScreen;
import me.aren.gbjam.screens.GameScreen;
import me.aren.gbjam.screens.MainMenuScreen;

public class GameStateHandler {
    GBJamGame game;
    ScoreHandler scoreHandler;
    int lives = 3;
    boolean isInTutorial = false;

    public GameStateHandler(GBJamGame game, ScoreHandler scoreHandler) {
        this.game = game;
        this.scoreHandler = scoreHandler;
    }
    public GameStateHandler(GBJamGame game, int lives) {
        this.game = game;
        this.lives = lives;
    }

    public void restartScreen() {
        game.setScreen(new GameScreen(game));
    }

    public int getLives() {
        return lives;
    }

    public void removeLive() {
        lives--;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void returnToMainMenu() {
        game.setScreen(new MainMenuScreen(game, game.settingsHandler));
    }

    public void gameOver() {
        if(!isInTutorial) game.setScreen(new GameOverScreen(game, scoreHandler, this));
    }

    public void setInTutorial(boolean inTutorial) {
        isInTutorial = inTutorial;
    }
}
