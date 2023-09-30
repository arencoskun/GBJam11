package me.aren.gbjam.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Objects;

public class SettingsHandler {
    Preferences prefs;

    public SettingsHandler() {
        prefs = Gdx.app.getPreferences("Galaxy Miner");

        if(!prefs.getBoolean("music-modified")) {
            prefs.putBoolean("music", true);
        }

        if(!prefs.getBoolean("sounds-modified")) {
            prefs.putBoolean("sounds", true);
        }
        prefs.flush();
    }

    public int getInt(String key) {
        return prefs.getInteger(key);
    }

    public void setInt(String key, int value) {
        prefs.putInteger(key, value);
        prefs.flush();
    }

    public String getString(String key) {
        return prefs.getString(key);
    }

    public void setString(String key, String value) {
        prefs.putString(key, value);
        prefs.flush();
    }

    public boolean getBool(String key) {
        return prefs.getBoolean(key);
    }

    public void setBool(String key, boolean value) {
        prefs.putBoolean(key, value);
        if(Objects.equals(key, "music") && !getBool("music-modified")) prefs.putBoolean("music-modified", true);
        if(Objects.equals(key, "sounds") && !getBool("sounds-modified")) prefs.putBoolean("sounds-modified", true);
        prefs.flush();
    }
}
