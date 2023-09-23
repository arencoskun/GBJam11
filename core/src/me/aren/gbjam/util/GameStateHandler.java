package me.aren.gbjam.util;

import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.screens.GameScreen;

public class GameStateHandler {
    GBJamGame game;
    int lives = 3;

    public GameStateHandler(GBJamGame game) {
        this.game = game;
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
}
