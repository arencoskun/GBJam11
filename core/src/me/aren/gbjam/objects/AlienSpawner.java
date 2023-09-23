package me.aren.gbjam.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.enums.AsteroidType;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;

import java.util.Random;

public class AlienSpawner implements IGameObject {
    private Random random;
    private GameObjectHandler objectHandler;

    private long time = 0;
    private long lastSpawn = 0;
    private long spawnCooldown = 5000;

    public AlienSpawner(GameObjectHandler objectHandler) {
        random = new Random();
        this.objectHandler = objectHandler;

        objectHandler.addObject(this);
    }

    private void startSpawnCounter() {
        time = System.currentTimeMillis();
        if (time > lastSpawn + spawnCooldown) {
            spawnAlien();
        }
    }

    private void spawnAlien() {
        System.out.println("Spawn asteroid");

        Vector2 spawnPos = new Vector2(random.nextInt(0, 150), random.nextInt(100, 120));
        new Alien(objectHandler, spawnPos);

        lastSpawn = System.currentTimeMillis();
    }

    @Override
    public void update(float delta) {
        startSpawnCounter();
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {
        objectHandler.removeObject(this);
    }
}
