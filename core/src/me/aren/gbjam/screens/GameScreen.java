package me.aren.gbjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.objects.*;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.GameStateHandler;
import me.aren.gbjam.util.ScoreHandler;
import me.aren.gbjam.util.cam.Rumble;

public class GameScreen implements Screen {
	
	private final int GB_SCREEN_WIDTH = 160, GB_SCREEN_HEIGHT = 144,
					  GB_TILE_WIDTH   =   8, GB_TILE_HEIGHT   =   8,
					  GB_PARRAY_WIDTH =  20, GB_PARRAY_HEIGHT =  18;
	private final int SCALING_FACTOR_VIEWPORT                 = 6;
	private final Color[] GB_COLOR_PALETTE                    = new Color[4];
	private final String SPR_TILE_BLACK_PATH				  = "sprites/tile_black.png";

	private final int STAR_AMOUNT = 14;
	
	private Texture texTileBlack;
	private GameObjectHandler objHandler;
	private ScoreHandler scoreHandler;
	private GameStateHandler gameStateHandler;
	private Spaceship spaceship;

	private Music gameMusic;
	
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
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/game_music_final.mp3"));
		gameMusic.setVolume(.5f);
		gameMusic.setLooping(true);

		objHandler			= new GameObjectHandler();
		scoreHandler		= new ScoreHandler();
		gameStateHandler    = new GameStateHandler(game, scoreHandler);

		for(int i = 0; i < STAR_AMOUNT; i++) {
			new Star(objHandler);
		}

		spaceship 			= new Spaceship(objHandler, gameStateHandler, game.settingsHandler);
		// TODO: Move these up
		ScoreText scoreText = new ScoreText(objHandler, scoreHandler);
		AsteroidSpawner spawner	= new AsteroidSpawner(objHandler, scoreHandler, game.settingsHandler);
		AlienSpawner alienSpawner = new AlienSpawner(objHandler, game.settingsHandler);
		LivesBar livesBar = new LivesBar(objHandler, gameStateHandler);
		
		this.game 			= game;
		this.sb 			= game.sb;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(game.settingsHandler.getBool("music")) gameMusic.play();
	}
	
	private void drawBg() {
		for(int tileIx = 0; tileIx < GB_PARRAY_WIDTH; tileIx++) {
			for(int tileIy = 0; tileIy < GB_PARRAY_HEIGHT; tileIy++) {
				sb.draw(texTileBlack, tileIx * GB_TILE_WIDTH, tileIy * GB_TILE_HEIGHT);
			}
		}
	}
	
	private void update(float delta) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			gameStateHandler.returnToMainMenu();
		}
		objHandler.updateObjects(delta);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		update(delta);
		cam.update();
		sb.setProjectionMatrix(cam.combined);
		ScreenUtils.clear(GB_COLOR_PALETTE[3]);
		sb.begin();


		drawBg();
		
		objHandler.drawObjects(sb);
		if (Rumble.getRumbleTimeLeft() > 0){
			Rumble.tick(Gdx.graphics.getDeltaTime());
			cam.translate(Rumble.getPos());
		} else {
			cam.position.x = 80;
			cam.position.y = 72;
		}
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
		gameMusic.pause();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		objHandler.disposeObjects();
	}

}
