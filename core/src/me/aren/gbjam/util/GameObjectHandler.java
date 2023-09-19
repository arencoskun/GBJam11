package me.aren.gbjam.util;

import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.aren.gbjam.interfaces.IGameObject;

public class GameObjectHandler {
	private LinkedBlockingQueue<IGameObject> gameObjects;
	
	public GameObjectHandler() {
		gameObjects = new LinkedBlockingQueue<IGameObject>();
	}
	
	public void addObject(IGameObject obj) {
		gameObjects.add(obj);
	}
	
	public void removeObject(IGameObject obj) {
		gameObjects.remove(obj);
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
