package me.aren.gbjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.objects.*;
import me.aren.gbjam.objects.tutorial.TutorialAlien;
import me.aren.gbjam.objects.tutorial.TutorialAsteroid;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.GameStateHandler;
import me.aren.gbjam.util.ScoreHandler;

public class TutorialScreen implements Screen {

	private final int GB_SCREEN_WIDTH = 160, GB_SCREEN_HEIGHT = 144,
					  GB_TILE_WIDTH   =   8, GB_TILE_HEIGHT   =   8,
					  GB_PARRAY_WIDTH =  20, GB_PARRAY_HEIGHT =  18;
	private final int SCALING_FACTOR_VIEWPORT                 = 4;
	private final Color[] GB_COLOR_PALETTE                    = new Color[4];
	private final String SPR_TILE_BLACK_PATH				  = "sprites/tile_black.png";
	private final String SND_CONGRATS						  = "audio/congrats.wav";

	private final int STAR_AMOUNT = 14;

	private Texture texTileBlack;

	private GameObjectHandler objHandler;
	private ScoreHandler scoreHandler;
	private GameStateHandler gameStateHandler;
	private Spaceship spaceship;

	GBJamGame game;
	SpriteBatch sb;
	Viewport viewport;
	OrthographicCamera cam;

	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
	FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	BitmapFont pressStartFont;
	GlyphLayout layout;
	int tutorialStage = 0;
	boolean pressedRight = false;
	boolean pressedLeft = false;
	boolean shot = false;
	private Sound sndCongrats;
	TutorialAlien tutorialAlien;
	TutorialAsteroid tutorialAsteroid;

	boolean stage1CongratsPlayed = false;
	boolean stage5CongratsPlayed = false;
	boolean stage5Started		 = false;
	long stage5Start = 0;
	long time = 0;
	long lastEnterPress = 0;
	private boolean stage5doNotDraw = false;
	ScoreText scoreText;
	AsteroidSpawner spawner;
	AlienSpawner alienSpawner;
	boolean setLives = false;

	public TutorialScreen(GBJamGame game) {
		cam 				= new OrthographicCamera();
		viewport 			= new StretchViewport(GB_SCREEN_WIDTH*SCALING_FACTOR_VIEWPORT, GB_SCREEN_HEIGHT*SCALING_FACTOR_VIEWPORT, cam);
		cam.setToOrtho(false, GB_SCREEN_WIDTH, GB_SCREEN_HEIGHT);
		
		GB_COLOR_PALETTE[0] = new Color(1f, 156f/255f, 0f/255f, 1f); // Orange
		GB_COLOR_PALETTE[1] = new Color(162f/255f, 162f/255f, 162f/255f, 1f); // Lightest Gray
		GB_COLOR_PALETTE[2] = new Color(121f/255f, 121f/255f, 121f/255f, 1f); // Gray
		GB_COLOR_PALETTE[3] = new Color(30f/255f, 27f/255f, 27f/255f, 1f); // Black
		
		texTileBlack 		= new Texture(Gdx.files.internal(SPR_TILE_BLACK_PATH));
		sndCongrats			= Gdx.audio.newSound(Gdx.files.internal(SND_CONGRATS));

		objHandler			= new GameObjectHandler();
		scoreHandler		= new ScoreHandler();
		gameStateHandler    = new GameStateHandler(game, 1);

		parameter.size = 8;
		pressStartFont = generator.generateFont(parameter); // font size 12 pixels
		layout = new GlyphLayout();
		layout.setText(pressStartFont, "");

		//spaceship 			= new Spaceship(objHandler, gameStateHandler);
		// TODO: Move these up

		this.game 			= game;
		this.sb 			= game.sb;
		//pressStartFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		//objHandler.setPause(true);
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
		time = System.currentTimeMillis();
		
		update(delta);
		cam.update();
		sb.setProjectionMatrix(cam.combined);
		ScreenUtils.clear(GB_COLOR_PALETTE[3]);
		sb.begin();


		drawBg();
		
		objHandler.drawObjects(sb);

		if(tutorialStage == 0) {
			objHandler.setPause(true);
			layout.setText(pressStartFont, "Welcome!");
			pressStartFont.draw(sb, "Welcome!", (160 - layout.width) / 2, 130);
			layout.setText(pressStartFont, "Here is a quick");
			pressStartFont.draw(sb, "Here is a quick", (160 - layout.width) / 2, 110);
			layout.setText(pressStartFont, "tutorial to get");
			pressStartFont.draw(sb, "tutorial to get", (160 - layout.width) / 2, 100);
			layout.setText(pressStartFont, "you playing in");
			pressStartFont.draw(sb, "you playing in", (160 - layout.width) / 2, 90);
			layout.setText(pressStartFont, "no time!");
			pressStartFont.draw(sb, "no time!", (160 - layout.width) / 2, 80);

			layout.setText(pressStartFont, "Use the arrow");
			pressStartFont.draw(sb, "Use the arrow", (160 - layout.width) / 2, 60);
			layout.setText(pressStartFont, "keys to move");
			pressStartFont.draw(sb, "keys to move", (160 - layout.width) / 2, 50);
			layout.setText(pressStartFont, "your spaceship.");
			pressStartFont.draw(sb, "your spaceship.", (160 - layout.width) / 2, 40);

			layout.setText(pressStartFont, "Press ENTER to");
			pressStartFont.draw(sb, "Press ENTER to", (160 - layout.width) / 2, 20);
			layout.setText(pressStartFont, "continue...");
			pressStartFont.draw(sb, "continue...", (160 - layout.width) / 2, 10);

			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				tutorialStage++;
			}
		}

		if(tutorialStage == 1) {
			objHandler.setPause(false);
			layout.setText(pressStartFont, "Now try to");
			pressStartFont.draw(sb, "Now try to", (160 - layout.width) / 2, 130);
			layout.setText(pressStartFont, "move your ship.");
			pressStartFont.draw(sb, "move your ship.", (160 - layout.width) / 2, 120);

			if(pressedRight && pressedLeft) {
				objHandler.setPause(true);
				if(!stage1CongratsPlayed) {
					sndCongrats.play();
					stage1CongratsPlayed = true;
				}
				layout.setText(pressStartFont, "Congrats!");
				pressStartFont.draw(sb, "Congrats!", (160 - layout.width) / 2, 110);
				layout.setText(pressStartFont, "Press ENTER to");
				pressStartFont.draw(sb, "Press ENTER to", (160 - layout.width) / 2, 90);
				layout.setText(pressStartFont, "continue...");
				pressStartFont.draw(sb, "continue...", (160 - layout.width) / 2, 80);
			}

			if(spaceship == null) {
				spaceship = new Spaceship(objHandler, gameStateHandler);
			}

			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				pressedRight = true;
			}

			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				pressedLeft = true;
			}

			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && pressedRight && pressedLeft) {
				tutorialStage++;
			}
		}

		if(tutorialStage == 2) {
			objHandler.setPause(false);
			if(tutorialAlien == null) tutorialAlien = new TutorialAlien(objHandler, new Vector2(72, 72));
			layout.setText(pressStartFont, "Press Z to");
			pressStartFont.draw(sb, "Press Z to", (160 - layout.width) / 2, 130);
			layout.setText(pressStartFont, "shoot.");
			pressStartFont.draw(sb, "shoot.", (160 - layout.width) / 2, 120);
			layout.setText(pressStartFont, "Try it!");
			pressStartFont.draw(sb, "Try it!", (160 - layout.width) / 2, 100);

			if(tutorialAlien.isBulletHit()) {
				objHandler.setPause(true);
				layout.setText(pressStartFont, "Congrats!");
				pressStartFont.draw(sb, "Congrats!", (160 - layout.width) / 2, 60);
				layout.setText(pressStartFont, "Press ENTER to");
				pressStartFont.draw(sb, "Press ENTER to", (160 - layout.width) / 2, 50);
				layout.setText(pressStartFont, "continue...");
				pressStartFont.draw(sb, "continue...", (160 - layout.width) / 2, 40);
			}

			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && tutorialAlien.isBulletHit()) {
				tutorialAlien.dispose();
				tutorialStage++;
			}
		}

		if(tutorialStage == 3) {
			if(tutorialAsteroid == null) tutorialAsteroid = new TutorialAsteroid(objHandler, new Vector2(80-16, 64));
			objHandler.setPause(false);

			layout.setText(pressStartFont, "Shoot rocks to");
			pressStartFont.draw(sb, "Shoot rocks to", (160 - layout.width) / 2, 130);
			layout.setText(pressStartFont, "increase your");
			pressStartFont.draw(sb, "increase your", (160 - layout.width) / 2, 120);
			layout.setText(pressStartFont, "score!");
			pressStartFont.draw(sb, "score!", (160 - layout.width) / 2, 110);

			if(tutorialAsteroid.isBulletHit()) {
				objHandler.setPause(true);
				layout.setText(pressStartFont, "Congrats!");
				pressStartFont.draw(sb, "Congrats!", (160 - layout.width) / 2, 60);
				layout.setText(pressStartFont, "Press ENTER to");
				pressStartFont.draw(sb, "Press ENTER to", (160 - layout.width) / 2, 50);
				layout.setText(pressStartFont, "continue...");
				pressStartFont.draw(sb, "continue...", (160 - layout.width) / 2, 40);
			}

			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && tutorialAsteroid.isBulletHit()) {
				tutorialAsteroid.dispose();
				tutorialStage++;
				lastEnterPress = System.currentTimeMillis();
			}
		}

		if(tutorialStage == 4) {
			objHandler.removeObject(spaceship);
			layout.setText(pressStartFont, "Aliens will shoot");
			pressStartFont.draw(sb, "Aliens will shoot", (160 - layout.width) / 2, 130);
			layout.setText(pressStartFont, "and rocks will");
			pressStartFont.draw(sb, "and rocks will", (160 - layout.width) / 2, 120);
			layout.setText(pressStartFont, "fall from space.");
			pressStartFont.draw(sb, "fall from space.", (160 - layout.width) / 2, 110);

			layout.setText(pressStartFont, "Avoid getting hit");
			pressStartFont.draw(sb, "Avoid getting hit", (160 - layout.width) / 2, 90);
			layout.setText(pressStartFont, "to survive!");
			pressStartFont.draw(sb, "to survive!", (160 - layout.width) / 2, 80);

			layout.setText(pressStartFont, "Press ENTER to");
			pressStartFont.draw(sb, "Press ENTER to", (160 - layout.width) / 2, 60);
			layout.setText(pressStartFont, "continue...");
			pressStartFont.draw(sb, "continue...", (160 - layout.width) / 2, 50);

			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && time - lastEnterPress > 100) {
				lastEnterPress = System.currentTimeMillis();
				tutorialStage++;
			}
		}

		if(tutorialStage == 5) {
			time = System.currentTimeMillis();
			if(!setLives) {
				objHandler.removeAllObjects();
				objHandler.disposeObjects();
				gameStateHandler.setLives(1);
				setLives = true;
			}

			if(!stage5doNotDraw) {
				layout.setText(pressStartFont, "Now try to");
				pressStartFont.draw(sb, "Now try to", (160 - layout.width) / 2, 130);
				layout.setText(pressStartFont, "survive for");
				pressStartFont.draw(sb, "survive for", (160 - layout.width) / 2, 120);
				layout.setText(pressStartFont, "10 seconds!");
				pressStartFont.draw(sb, "10 seconds!", (160 - layout.width) / 2, 110);

				layout.setText(pressStartFont, "Press ENTER when");
				pressStartFont.draw(sb, "Press ENTER when", (160 - layout.width) / 2, 90);
				layout.setText(pressStartFont, "you feel ready.");
				pressStartFont.draw(sb, "you feel ready.", (160 - layout.width) / 2, 80);
			}

			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && time - lastEnterPress > 100) {
				if(!stage5Started) {
					stage5Started = true;
				}
			}

			if(stage5Started) {
				objHandler.setPause(false);
				spaceship 			= new Spaceship(objHandler, gameStateHandler);
				scoreText = new ScoreText(objHandler, scoreHandler);
				spawner	= new AsteroidSpawner(objHandler, scoreHandler);
				alienSpawner = new AlienSpawner(objHandler);
				LivesBar livesBar = new LivesBar(objHandler, gameStateHandler);
				for(int i = 0; i < STAR_AMOUNT; i++) {
					new Star(objHandler);
				}
				stage5Started = false;
				stage5doNotDraw = true;

				stage5Start = System.currentTimeMillis();

			}

			if(gameStateHandler.getLives() == 0) {
				objHandler.removeAllObjects();
				objHandler.disposeObjects();
				tutorialStage = 51;
			}

			if(time > stage5Start + 10000 && stage5Start != 0) {
				objHandler.setPause(true);
				objHandler.removeAllObjects();
				if(!stage5CongratsPlayed) {
					sndCongrats.play();
					stage5CongratsPlayed = true;
				}
				tutorialStage = 55;
			}
		}

		if(tutorialStage == 51) {

			layout.setText(pressStartFont, "Yikes!");
			pressStartFont.draw(sb, "Yikes!", (160 - layout.width) / 2, 130);
			layout.setText(pressStartFont, "Let's try that");
			pressStartFont.draw(sb, "Let's try that", (160 - layout.width) / 2, 120);
			layout.setText(pressStartFont, "again.");
			pressStartFont.draw(sb, "again.", (160 - layout.width) / 2, 110);

			layout.setText(pressStartFont, "Press ENTER to");
			pressStartFont.draw(sb, "Press ENTER to", (160 - layout.width) / 2, 90);
			layout.setText(pressStartFont, "try again.");
			pressStartFont.draw(sb, "try again.", (160 - layout.width) / 2, 80);

			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				stage5doNotDraw = false;
				setLives = false;
				tutorialStage = 5;
			}
		}

		if(tutorialStage == 55) {
			layout.setText(pressStartFont, "Congrats!");
			pressStartFont.draw(sb, "Congrats!", (160 - layout.width) / 2, 130);
			layout.setText(pressStartFont, "You are now");
			pressStartFont.draw(sb, "You are now", (160 - layout.width) / 2, 120);
			layout.setText(pressStartFont, "ready to play!");
			pressStartFont.draw(sb, "ready to play!", (160 - layout.width) / 2, 110);

			layout.setText(pressStartFont, "Press ENTER to");
			pressStartFont.draw(sb, "Press ENTER to", (160 - layout.width) / 2, 90);
			layout.setText(pressStartFont, "play now or,");
			pressStartFont.draw(sb, "play now or,", (160 - layout.width) / 2, 80);
			layout.setText(pressStartFont, "press Z to");
			pressStartFont.draw(sb, "press Z to", (160 - layout.width) / 2, 70);
			layout.setText(pressStartFont, "return to the");
			pressStartFont.draw(sb, "return to the", (160 - layout.width) / 2, 60);
			layout.setText(pressStartFont, "main menu.");
			pressStartFont.draw(sb, "main menu.", (160 - layout.width) / 2, 50);

			layout.setText(pressStartFont, "Thanks for playing!");
			pressStartFont.draw(sb, "Thanks for playing!", (160 - layout.width) / 2, 30);
			layout.setText(pressStartFont, "- aren");
			pressStartFont.draw(sb, "- aren", (160 - layout.width) / 2, 20);

			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				gameStateHandler.restartScreen();
			}

			if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
				Gdx.app.exit();
			}
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
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		objHandler.disposeObjects();
	}

}
