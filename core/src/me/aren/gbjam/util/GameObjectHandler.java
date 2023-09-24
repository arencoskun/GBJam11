package me.aren.gbjam.util;

import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.objects.Asteroid;
import me.aren.gbjam.objects.Bullet;
import me.aren.gbjam.objects.tutorial.TutorialAsteroid;

public class GameObjectHandler {
	private LinkedBlockingQueue<IGameObject> gameObjects;
	private LinkedBlockingQueue<Bullet> bullets;
	private LinkedBlockingQueue<Asteroid> asteroids;
	private boolean pause = false;
	
	public GameObjectHandler() {
		gameObjects = new LinkedBlockingQueue<IGameObject>();
		bullets = new LinkedBlockingQueue<Bullet>();
		asteroids = new LinkedBlockingQueue<Asteroid>();
	}
	
	public void addObject(IGameObject obj) {
		gameObjects.add(obj);
	}
	public void addBullet(Bullet bullet) {
		gameObjects.add(bullet);
		bullets.add(bullet);
	}

	public void addAsteroid(Asteroid asteroid) {
		gameObjects.add(asteroid);
		asteroids.add(asteroid);
		System.out.println("Added asteroid");
	}
	
	public void removeObject(IGameObject obj) {
		gameObjects.remove(obj);
	}
	public void removeBullet(Bullet bullet) {
		gameObjects.remove(bullet);
		bullets.remove(bullet);
	}

	public void removeAsteroid(Asteroid asteroid) {
		gameObjects.remove(asteroid);
		asteroids.remove(asteroid);
	}

	public Bullet checkIfBulletColliding(Rectangle hitbox) {
		for(Bullet bullet : bullets) {
			if(hitbox.contains(bullet.getHitbox())) {
				return bullet;
			}
		}

		return null;
	}

	public Asteroid checkIfAsteroidColliding(Rectangle hitbox) {
		for(Asteroid asteroid : asteroids) {
			if(asteroid.getHitbox().contains(hitbox) || hitbox.contains(asteroid.getHitbox())) {
				return asteroid;
			}
		}

		return null;
	}
	
	public void updateObjects(float delta) {
		if(!pause) {
			for (IGameObject gameObject : gameObjects) {
				gameObject.update(delta);
			}
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

	public void removeAllObjects() {
		for(IGameObject gameObject : gameObjects) {
			removeObject(gameObject);
		}

		for(Asteroid asteroid : asteroids) {
			removeAsteroid(asteroid);
		}

		for(Bullet bullet : bullets) {
			removeBullet(bullet);
		}
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
}
