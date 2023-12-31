package me.aren.gbjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.objects.Star;
import me.aren.gbjam.ui.MainMenuEntry;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.GameStateHandler;
import me.aren.gbjam.util.ScoreHandler;

public class MainMenuScreen implements Screen {
    private final int GB_SCREEN_WIDTH = 160, GB_SCREEN_HEIGHT = 144,
            GB_TILE_WIDTH   =   8, GB_TILE_HEIGHT   =   8,
            GB_PARRAY_WIDTH =  20, GB_PARRAY_HEIGHT =  18;
    private final int SCALING_FACTOR_VIEWPORT                 = 4;
    private final String SPR_TILE_BLACK_PATH				  = "sprites/tile_black.png";
    private final String SND_CLICK = "audio/click_1.wav";
    private final int STAR_AMOUNT = 30;

    private Texture texTileBlack;
    private Sound clickSound;
    GBJamGame game;
    SpriteBatch sb;
    Array<MainMenuEntry> mainMenuEntries;

    Viewport viewport;
    OrthographicCamera cam;

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont pressStartFont;
    GlyphLayout layout;

    private GameObjectHandler objHandler;
    private ScoreHandler scoreHandler;
    private GameStateHandler gameStateHandler;
    private int currentIndex = 0;

    MainMenuEntry playEntry;
    MainMenuEntry tutorialEntry;
    MainMenuEntry exitEntry;

    Music menuMusic;

    public MainMenuScreen(GBJamGame game) {
        parameter.size = 8;
        pressStartFont = generator.generateFont(parameter); // font size 12 pixels
        layout = new GlyphLayout();
        layout.setText(pressStartFont, "Galaxy Miner");
        cam 				= new OrthographicCamera();
        viewport 			= new StretchViewport(GB_SCREEN_WIDTH*SCALING_FACTOR_VIEWPORT, GB_SCREEN_HEIGHT*SCALING_FACTOR_VIEWPORT, cam);
        cam.setToOrtho(false, GB_SCREEN_WIDTH, GB_SCREEN_HEIGHT);
        clickSound = Gdx.audio.newSound(Gdx.files.internal(SND_CLICK));
        this.game = game;
        this.sb = game.sb;
        mainMenuEntries = new Array<MainMenuEntry>();

        texTileBlack 		= new Texture(Gdx.files.internal(SPR_TILE_BLACK_PATH));

        objHandler			= new GameObjectHandler();
        scoreHandler		= new ScoreHandler();
        gameStateHandler    = new GameStateHandler(game, scoreHandler);

        playEntry = new MainMenuEntry(game, "Play");
        tutorialEntry = new MainMenuEntry(game, "Tutorial");
        exitEntry = new MainMenuEntry(game, "Exit");

        for(int i = 0; i < STAR_AMOUNT; i++) {
            new Star(objHandler);
        }

        mainMenuEntries.add(playEntry);
        mainMenuEntries.add(tutorialEntry);
        mainMenuEntries.add(exitEntry);

        playEntry.setSelected(true);
        playEntry.setPos(new Vector2((160 - playEntry.getTextWidth()) / 2, (144 - playEntry.getTextHeight()) / 2 + 10));
        tutorialEntry.setPos(new Vector2((160 - tutorialEntry.getTextWidth()) / 2, (144 - tutorialEntry.getTextHeight()) / 2));
        exitEntry.setPos(new Vector2((160 - exitEntry.getTextWidth()) / 2, (144 - exitEntry.getTextHeight()) / 2 - 10));

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/mainmenu_final.mp3"));
        menuMusic.setLooping(true);

    }

    private void drawBg() {
        for(int tileIx = 0; tileIx < GB_PARRAY_WIDTH; tileIx++) {
            for(int tileIy = 0; tileIy < GB_PARRAY_HEIGHT; tileIy++) {
                sb.draw(texTileBlack, tileIx * GB_TILE_WIDTH, tileIy * GB_TILE_HEIGHT);
            }
        }
    }

    @Override
    public void show() {
        menuMusic.play();
    }

    private void update(float delta) {
        objHandler.updateObjects(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if(currentIndex < 2) {
                mainMenuEntries.get(currentIndex).setSelected(false);
                currentIndex++;
                mainMenuEntries.get(currentIndex).setSelected(true);
                clickSound.play();
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if(currentIndex > 0) {
                mainMenuEntries.get(currentIndex).setSelected(false);
                currentIndex--;
                mainMenuEntries.get(currentIndex).setSelected(true);
                clickSound.play();
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(currentIndex == 0) {
                game.setScreen(new GameScreen(game));
            }

            if(currentIndex == 1) {
                game.setScreen(new TutorialScreen(game));
            }

            if(currentIndex == 2) {
                Gdx.app.exit();
            }
        }

        playEntry.setPos(new Vector2((160 - playEntry.getTextWidth()) / 2, (144 - playEntry.getTextHeight()) / 2 + 10));
        tutorialEntry.setPos(new Vector2((160 - tutorialEntry.getTextWidth()) / 2, (144 - tutorialEntry.getTextHeight()) / 2));
        exitEntry.setPos(new Vector2((160 - exitEntry.getTextWidth()) / 2, (144 - exitEntry.getTextHeight()) / 2 - 10));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        drawBg();
        objHandler.drawObjects(sb);
        layout.setText(pressStartFont, "Galaxy Miner");
        pressStartFont.draw(sb, "Galaxy Miner", (160 - layout.width) / 2, 120);
        update(delta);
        for(MainMenuEntry entry : mainMenuEntries) {
            entry.render();
        }

        layout.setText(pressStartFont, "Made for GBJam11");
        pressStartFont.draw(sb, "Made for GBJam11", (160 - layout.width) / 2, 20);
        sb.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        menuMusic.pause();
    }

    @Override
    public void dispose() {

    }
}
