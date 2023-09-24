package me.aren.gbjam.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.util.GameStateHandler;
import me.aren.gbjam.util.ScoreHandler;

public class GameOverScreen implements Screen {
    private SpriteBatch sb;

    private final int GB_SCREEN_WIDTH = 160, GB_SCREEN_HEIGHT = 144,
            GB_TILE_WIDTH   =   8, GB_TILE_HEIGHT   =   8,
            GB_PARRAY_WIDTH =  20, GB_PARRAY_HEIGHT =  18;
    private final int SCALING_FACTOR_VIEWPORT                 = 4;
    private final String SPR_TILE_BLACK_PATH				  = "sprites/tile_black.png";

    private Texture texTileBlack;

    Viewport viewport;
    OrthographicCamera cam;

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont pressStartFont;
    GlyphLayout layout;
    ScoreHandler scoreHandler;
    GameStateHandler gameStateHandler;

    public GameOverScreen(GBJamGame game, ScoreHandler scoreHandler, GameStateHandler gameStateHandler) {
        this.scoreHandler = scoreHandler;
        this.gameStateHandler = gameStateHandler;
        parameter.size = 8;
        pressStartFont = generator.generateFont(parameter); // font size 12 pixels
        layout = new GlyphLayout();
        layout.setText(pressStartFont, "");
        texTileBlack = new Texture(SPR_TILE_BLACK_PATH);

        cam 				= new OrthographicCamera();
        viewport 			= new StretchViewport(GB_SCREEN_WIDTH*SCALING_FACTOR_VIEWPORT, GB_SCREEN_HEIGHT*SCALING_FACTOR_VIEWPORT, cam);
        cam.setToOrtho(false, GB_SCREEN_WIDTH, GB_SCREEN_HEIGHT);
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameStateHandler.restartScreen();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            gameStateHandler.returnToMainMenu();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        sb.setProjectionMatrix(cam.combined);
        ScreenUtils.clear(Color.BLACK);

        sb.begin();
        drawBg();

        layout.setText(pressStartFont, "Game Over!");
        pressStartFont.draw(sb, "Game Over!", (160 - layout.width) / 2, 130);
        layout.setText(pressStartFont, "Press ENTER to");
        pressStartFont.draw(sb, "Press ENTER to", (160 - layout.width) / 2, 100);
        layout.setText(pressStartFont, "try again...");
        pressStartFont.draw(sb, "try again...", (160 - layout.width) / 2, 90);

        layout.setText(pressStartFont, "Press X to");
        pressStartFont.draw(sb, "Press X to", (160 - layout.width) / 2, 70);
        layout.setText(pressStartFont, "return to the");
        pressStartFont.draw(sb, "return to the", (160 - layout.width) / 2, 60);
        layout.setText(pressStartFont, "main menu...");
        pressStartFont.draw(sb, "main menu...", (160 - layout.width) / 2, 50);

        layout.setText(pressStartFont, "Your score:");
        pressStartFont.draw(sb, "Your score:", (160 - layout.width) / 2, 30);
        layout.setText(pressStartFont, scoreHandler.getScoreString());
        pressStartFont.draw(sb, scoreHandler.getScoreString(), (160 - layout.width) / 2, 20);
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
