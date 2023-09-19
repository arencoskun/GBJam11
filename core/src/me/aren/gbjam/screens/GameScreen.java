package me.aren.gbjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.enums.AsteroidType;
import me.aren.gbjam.objects.*;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.ScoreHandler;

public class GameScreen implements Screen {
	
	private final int GB_SCREEN_WIDTH = 160, GB_SCREEN_HEIGHT = 144,
					  GB_TILE_WIDTH   =   8, GB_TILE_HEIGHT   =   8,
					  GB_PARRAY_WIDTH =  20, GB_PARRAY_HEIGHT =  18;
	private final int SCALING_FACTOR_VIEWPORT                 = 4;
	private final Color[] GB_COLOR_PALETTE                    = new Color[4];
	private final String SPR_TILE_BLACK_PATH				  = "sprites/tile_black.png";
	private final String SPR_ALIEN_HAPPY					  = "sprites/alien_happy.png";
	private final String SPR_ALIEN_NEUTRAL					  = "sprites/alien_neutral.png";
	private final String SPR_ALIEN_NOFACE					  = "sprites/alien_noface.png";
	private final String SPR_ALIEN_PATTERN					  = "sprites/alien_pattern.png";
	private final String SPR_ALIEN_SAD						  = "sprites/alien_sad.png";

	private final int STAR_AMOUNT = 14;
	
	private Texture texTileBlack;
	
	private GameObjectHandler objHandler;
	private ScoreHandler scoreHandler;
	private Spaceship spaceship;
	
	GBJamGame game;
	SpriteBatch sb;
	Viewport viewport;
	OrthographicCamera cam;
	
	public GameScreen(GBJamGame game) {
		cam 				= new OrthographicCamera();
		viewport 			= new StretchViewport(GB_SCREEN_WIDTH*SCALING_FACTOR_VIEWPORT, GB_SCREEN_HEIGHT*SCALING_FACTOR_VIEWPORT, cam);
		cam.setToOrtho(false, GB_SCREEN_WIDTH, GB_SCREEN_HEIGHT);
		
		GB_COLOR_PALETTE[0] = new Color(1f, 156f/255f, 0f/255f, 1f); // Orange
		GB_COLOR_PALETTE[1] = new Color(162f/255f, 162f/255f, 162f/255f, 1f); // Lightest Gray
		GB_COLOR_PALETTE[2] = new Color(121f/255f, 121f/255f, 121f/255f, 1f); // Gray
		GB_COLOR_PALETTE[3] = new Color(30f/255f, 27f/255f, 27f/255f, 1f); // Black
		
		texTileBlack 		= new Texture(Gdx.files.internal(SPR_TILE_BLACK_PATH));

		objHandler			= new GameObjectHandler();
		scoreHandler		= new ScoreHandler();

		for(int i = 0; i < STAR_AMOUNT; i++) {
			new Star(objHandler);
		}
		spaceship 			= new Spaceship(objHandler, scoreHandler);
		Asteroid rockTiny	= new Asteroid(objHandler, AsteroidType.TINY, new Vector2(10, 144));
		Asteroid rockMedium	= new Asteroid(objHandler, AsteroidType.MEDIUM, new Vector2(50, 144));
		Asteroid rockLarge	= new Asteroid(objHandler, AsteroidType.LARGE, new Vector2(100, 144));
		ScoreText scoreText = new ScoreText(objHandler, scoreHandler);
		
		this.game 			= game;
		this.sb 			= game.sb;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	private void drawBg() {
		for(int tileIx = 0; tileIx < GB_PARRAY_WIDTH; tileIx++) {
			for(int tileIy = 0; tileIy < GB_PARRAY_HEIGHT; tileIy++) {
				sb.draw(texTileBlack, tileIx * GB_TILE_WIDTH, tileIy * GB_TILE_HEIGHT);
			}
		}
	}
	
	private void update(float delta) {
		objHandler.updateObjects(delta);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		update(delta);
		cam.update();
		sb.setProjectionMatrix(cam.combined);
		ScreenUtils.clear(0, 0, 0, 1);
		sb.begin();

		drawBg();
		
		objHandler.drawObjects(sb);
		
		sb.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		objHandler.disposeObjects();
	}

}
