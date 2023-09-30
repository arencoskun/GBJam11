package me.aren.gbjam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.aren.gbjam.screens.GameScreen;
import me.aren.gbjam.screens.MainMenuScreen;
import me.aren.gbjam.screens.TutorialScreen;
import me.aren.gbjam.util.SettingsHandler;

public class GBJamGame extends Game {
	public SpriteBatch sb;
	public ShapeRenderer sr;

	public static final String VERSION_STRING = "v1.1";
	public SettingsHandler settingsHandler;
	
	@Override
	public void create () {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		settingsHandler = new SettingsHandler();
		setScreen(new MainMenuScreen(this, settingsHandler));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		sb.dispose();
	}
}
