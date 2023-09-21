package me.aren.gbjam.util;

import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.objects.Bullet;

public class GameObjectHandler {
	private LinkedBlockingQueue<IGameObject> gameObjects;
	private LinkedBlockingQueue<Bullet> bullets;
	
	public GameObjectHandler() {
		gameObjects = new LinkedBlockingQueue<IGameObject>();
		bullets = new LinkedBlockingQueue<Bullet>();
	}
	
	public void addObject(IGameObject obj) {
		gameObjects.add(obj);
	}
	public void addBullet(Bullet bullet) {
		gameObjects.add(bullet);
		bullets.add(bullet);
	}

	public Bullet checkIfBulletColliding(Rectangle hitbox) {
		for(Bullet bullet : bullets) {
			if(hitbox.contains(bullet.getHitbox())) {
				return bullet;
			}
		}

		return null;
	}
	
	public void removeObject(IGameObject obj) {
		gameObjects.remove(obj);
	}
	public void removeBullet(Bullet bullet) {
		gameObjects.remove(bullet);
		bullets.remove(bullet);
	}
	
	public void updateObjects(float delta) {
		for(IGameObject gameObject : gameObjects) {
			gameObject.update(delta);
		}
	}
	
	public void drawObjects(SpriteBatch sb) {
		for(IGameObject gameObject : gameObjects) {
			gameObject.render(sb);
		}
	}
	
	public void disposeObjects() {
		for(IGameObject gameObject : gameObjects) {
			gameObject.dispose();
		}
	}
}
