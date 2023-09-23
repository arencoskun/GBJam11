package me.aren.gbjam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.aren.gbjam.screens.GameScreen;
import me.aren.gbjam.screens.TutorialScreen;

public class GBJamGame extends Game {
	public SpriteBatch sb;
	public ShapeRenderer sr;
	
	@Override
	public void create () {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		setScreen(new TutorialScreen(this));
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
