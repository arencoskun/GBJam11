package me.aren.gbjam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.aren.gbjam.GBJamGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	private final static int ORIGINAL_WIDTH = 160;
	private final static int ORIGINAL_HEIGHT = 144;
	private final static int SCALING_FACTOR =  4;
	
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(ORIGINAL_WIDTH * SCALING_FACTOR, ORIGINAL_HEIGHT * SCALING_FACTOR);
		config.setTitle("Galaxy Miner - GBJam11");
		config.setWindowIcon("sprites/alien_happy.png");
		new Lwjgl3Application(new GBJamGame(), config);
	}
}
