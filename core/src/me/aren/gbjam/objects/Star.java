package me.aren.gbjam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;

import java.util.Random;

public class Star implements IGameObject {

	private final String SPR_STAR_1_PATH = "sprites/star_1.png";

	private Texture starTexture;
	private Random randomGen;
	private Vector2 pos;
	private GameObjectHandler objectHandler;

	public Star(GameObjectHandler objectHandler) {
		this.objectHandler = objectHandler;

		starTexture = new Texture(Gdx.files.internal(SPR_STAR_1_PATH));
		randomGen = new Random();
		pos = new Vector2(randomGen.nextInt(4, 154), randomGen.nextInt(130, 140));
		objectHandler.addObject(this);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		if(pos.y < 0) {
			pos = new Vector2(randomGen.nextInt(4, 154), randomGen.nextInt(110, 140));
		}

		pos.y -= 100 * delta;
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		sb.draw(starTexture, pos.x, pos.y, 2, 2);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		starTexture.dispose();
	}

}
