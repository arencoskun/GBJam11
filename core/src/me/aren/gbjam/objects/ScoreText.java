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
    BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
    GlyphLayout layout;
    private ScoreHandler scoreHandler;
    private int score = 0;

    public ScoreText(GameObjectHandler objectHandler, ScoreHandler scoreHandler) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 2;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        layout = new GlyphLayout();
        layout.setText(font12, String.valueOf(score));
        this.scoreHandler = scoreHandler;
        objectHandler.addObject(this);
    }
    @Override
    public void update(float delta) {
        layout.setText(font12, scoreHandler.getScoreString());
    }

    @Override
    public void render(SpriteBatch sb) {
        font12.draw(sb, scoreHandler.getScoreString(), 160 - layout.width, 142);
    }

    @Override
    public void dispose() {
        generator.dispose();
    }
}
