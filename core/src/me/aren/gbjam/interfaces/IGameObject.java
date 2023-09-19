package me.aren.gbjam.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IGameObject {
	public void update(float delta);
	public void render(SpriteBatch sb);
	public void dispose();
}
