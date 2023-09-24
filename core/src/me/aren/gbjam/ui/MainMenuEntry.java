package me.aren.gbjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.GBJamGame;

import java.util.function.Function;

public class MainMenuEntry {
    private SpriteBatch sb;

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont pressStartFont;
    GlyphLayout layout;
    String text;
    boolean selected;
    Vector2 pos;
    String originalText;

    public MainMenuEntry(GBJamGame game, String text) {
        this.sb = game.sb;

        parameter.size = 8;
        pressStartFont = generator.generateFont(parameter); // font size 12 pixels
        layout = new GlyphLayout();
        this.text = text;
        this.pos = pos;
        layout.setText(pressStartFont, text);
        originalText = text;
    }

    public void render() {
        pressStartFont.draw(sb, text, pos.x, pos.y);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected) {
            layout.setText(pressStartFont, "> " + text + " <");
            text = "> " + text + " <";
        } else {
            layout.setText(pressStartFont, originalText);
            text = originalText;
        }
    }

    public float getTextWidth() {
        return layout.width;
    }

    public float getTextHeight() {
        return layout.height;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }
}
