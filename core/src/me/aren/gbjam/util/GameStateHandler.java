package me.aren.gbjam.util;

import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.screens.GameScreen;

public class GameStateHandler {
    GBJamGame game;

    public GameStateHandler(GBJamGame game) {
        this.game = game;
    }

    public void restartScreen() {
        game.setScreen(new GameScreen(game));
    }
}
