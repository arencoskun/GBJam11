package me.aren.gbjam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;

public class Bullet implements IGameObject {
	
	private final String SPR_BULLET = "sprites/bullet.png";
	private final String SPR_BULLET_FLIPPED = "sprites/bullet_flipped.png";
	
	private float speed				= 100f;
	private GameObjectHandler objHandler;
	private Texture texBullet;
	private Vector2 pos;
	private Rectangle hitbox;
	private boolean enemy;
	
	public Bullet(Vector2 pos, GameObjectHandler objHandler, boolean enemy) {
		// TODO Auto-generated constructor stub
		this.pos = pos;
		this.objHandler = objHandler;
		this.enemy = enemy;
		
		texBullet = new Texture(Gdx.files.internal(enemy ? SPR_BULLET_FLIPPED : SPR_BULLET));
		hitbox = new Rectangle(pos.x, pos.y, texBullet.getWidth(), texBullet.getHeight());
		objHandler.addBullet(this);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		if(hitbox.getX() != pos.x || hitbox.getY() != pos.y) hitbox.setPosition(pos);
		if(pos.y > 150 || pos.y <= 0) {
			dispose();
		}

		float enemyBullet = enemy ? -1 : 1;

		pos.y += enemyBullet * (speed * delta);
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
		objHandler.removeBullet(this);
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

}
