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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.interfaces.ISettingsEntry;
import me.aren.gbjam.ui.ToggleEntry;
import me.aren.gbjam.util.GameStateHandler;
import me.aren.gbjam.util.ScoreHandler;
import me.aren.gbjam.util.SettingsHandler;

public class SettingsScreen implements Screen {
    private SpriteBatch sb;

    private final int GB_SCREEN_WIDTH = 160, GB_SCREEN_HEIGHT = 144,
            GB_TILE_WIDTH   =   8, GB_TILE_HEIGHT   =   8,
            GB_PARRAY_WIDTH =  20, GB_PARRAY_HEIGHT =  18;
    private final int SCALING_FACTOR_VIEWPORT                 = 6;
    private final String SPR_TILE_BLACK_PATH				  = "sprites/tile_black.png";
    private final String SND_CLICK = "audio/click_1.wav";

    private Texture texTileBlack;
    private Array<ISettingsEntry> settingsEntries;

    Viewport viewport;
    OrthographicCamera cam;

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont pressStartFont;
    GlyphLayout layout;

    ToggleEntry testToggle;
    ToggleEntry musicToggle;
    ToggleEntry soundToggle;
    GameStateHandler gameStateHandler;
    private int currentIndex = 0;
    private Sound clickSound;
    private SettingsHandler settingsHandler;

    public SettingsScreen(GBJamGame game, SettingsHandler settingsHandler, GameStateHandler gameStateHandler) {
        parameter.size = 8;
        pressStartFont = generator.generateFont(parameter); // font size 12 pixels
        layout = new GlyphLayout();
        layout.setText(pressStartFont, "");
        texTileBlack = new Texture(SPR_TILE_BLACK_PATH);
        this.settingsHandler = settingsHandler;
        this.gameStateHandler = gameStateHandler;

        cam 				= new OrthographicCamera();
        viewport 			= new StretchViewport(GB_SCREEN_WIDTH*SCALING_FACTOR_VIEWPORT, GB_SCREEN_HEIGHT*SCALING_FACTOR_VIEWPORT, cam);
        cam.setToOrtho(false, GB_SCREEN_WIDTH, GB_SCREEN_HEIGHT);
        settingsEntries = new Array<ISettingsEntry>();
        testToggle = new ToggleEntry(game, "Test Toggle", "test-toggle", settingsHandler);
        testToggle.setPos(110);

        musicToggle = new ToggleEntry(game, "Music", "music", settingsHandler);
        musicToggle.setPos(100);

        soundToggle = new ToggleEntry(game, "Sound", "sound", settingsHandler);
        soundToggle.setPos(90);

        settingsEntries.add(testToggle);
        settingsEntries.add(musicToggle);
        settingsEntries.add(soundToggle);

        settingsEntries.get(currentIndex).setSelected(true);

        clickSound = Gdx.audio.newSound(Gdx.files.internal(SND_CLICK));

        this.sb = game.sb;
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

    }

    private void update(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            gameStateHandler.returnToMainMenu();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if(currentIndex > 0) {
                settingsEntries.get(currentIndex).setSelected(false);
                currentIndex -= 1;
                settingsEntries.get(currentIndex).setSelected(true);
            }
            if(settingsHandler.getBool("sound")) clickSound.play();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if(currentIndex < settingsEntries.size - 1) {
                settingsEntries.get(currentIndex).setSelected(false);
                currentIndex += 1;
                settingsEntries.get(currentIndex).setSelected(true);
            }
            if(settingsHandler.getBool("sound")) clickSound.play();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        sb.setProjectionMatrix(cam.combined);
        ScreenUtils.clear(Color.BLACK);

        sb.begin();
        drawBg();
        for(ISettingsEntry settingsEntry : settingsEntries) {
            settingsEntry.render();
        }
        layout.setText(pressStartFont, "Settings");
        pressStartFont.draw(sb, "Settings", (160 - layout.width) / 2, 130);
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

    }

    @Override
    public void dispose() {

    }
}
