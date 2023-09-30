package me.aren.gbjam.interfaces;

import me.aren.gbjam.util.SettingsHandler;

public interface ISettingsEntry {
    public SettingsHandler getSettingsHandler();
    public void setSelected(boolean selected);
    public boolean getSelected();
    public float getTextWidth();
    public float getTextHeight();
    public void render();
}
