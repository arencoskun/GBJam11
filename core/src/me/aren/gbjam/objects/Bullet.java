package me.aren.gbjam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;

public class Bullet implements IGameObject {
	
	private final String SPR_BULLET = "sprites/bullet.png";
	
	private float speed				= 100f;
	private GameObjectHandler objHandler;
	private Texture texBullet;
	private Vector2 pos;
	
	public Bullet(Vector2 pos, GameObjectHandler objHandler) {
		// TODO Auto-generated constructor stub
		this.pos = pos;
		this.objHandler = objHandler;
		
		texBullet = new Texture(Gdx.files.internal(SPR_BULLET));
		objHandler.addObject(this);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		if(pos.y > 150) {
			dispose();
		}
		
		pos.y += speed * delta;
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		sb.draw(texBullet, pos.x, pos.y);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		texBullet.dispose();
		objHandler.removeObject(this);
	}

}
