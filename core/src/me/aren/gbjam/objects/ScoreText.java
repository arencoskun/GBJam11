package me.aren.gbjam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.ScoreHandler;

public class ScoreText implements IGameObject {
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont pressStartFont;
    GlyphLayout layout;
    private ScoreHandler scoreHandler;
    private int score = 0;

    public ScoreText(GameObjectHandler objectHandler, ScoreHandler scoreHandler) {
        parameter.size = 16;
        pressStartFont = generator.generateFont(parameter); // font size 12 pixels
        layout = new GlyphLayout();
        layout.setText(pressStartFont, String.valueOf(score));
        this.scoreHandler = scoreHandler;
        objectHandler.addObject(this);
    }
    @Override
    public void update(float delta) {
        layout.setText(pressStartFont, scoreHandler.getScoreString());
    }

    @Override
    public void render(SpriteBatch sb) {
        pressStartFont.draw(sb, scoreHandler.getScoreString(), 160 - layout.width, 142);
    }

    @Override
    public void dispose() {
        generator.dispose();
    }
}
