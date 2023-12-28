package me.aren.gbjam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.GBJamGame;
import me.aren.gbjam.interfaces.ISettingsEntry;
import me.aren.gbjam.util.SettingsHandler;

public class ToggleEntry implements ISettingsEntry {
    private SpriteBatch sb;

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont pressStartFont;
    GlyphLayout layout;
    String text;
    boolean selected;
    float y;
    String originalText;
    boolean value = false;
    String key;
    SettingsHandler settingsHandler;
    private Sound clickSound;

    public ToggleEntry(GBJamGame game, String text, String key, SettingsHandler settingsHandler) {
        this.sb = game.sb;

        parameter.size = 8;
        pressStartFont = generator.generateFont(parameter); // font size 12 pixels
        layout = new GlyphLayout();
        this.settingsHandler = settingsHandler;
        this.text = text;
        this.key = key;
        layout.setText(pressStartFont, text);
        originalText = text;
        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/click_1.wav"));
        value = settingsHandler.getBool(key);
    }

    private void update() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z) && selected) {
            value = !value;
            settingsHandler.setBool(key, value);
            if(settingsHandler.getBool("sound")) clickSound.play();
        }
    }

    public void render() {
        update();
        pressStartFont.draw(sb, text, 0, y);
        layout.setText(pressStartFont, value ? "On" : "Off");
        pressStartFont.draw(sb, value ? "On" : "Off", 160 - getTextWidth(), y);
    }

    @Override
    public SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected) {
            layout.setText(pressStartFont, "> " + originalText);
            text = "> " + originalText;
        } else {
            layout.setText(pressStartFont, originalText);
            text = originalText;
        }
    }

    @Override
    public boolean getSelected() {
        return selected;
    }

    public float getTextWidth() {
        return layout.width;
    }

    public float getTextHeight() {
        return layout.height;
    }

    public void setPos(int y) {
        this.y = y;
    }
}
