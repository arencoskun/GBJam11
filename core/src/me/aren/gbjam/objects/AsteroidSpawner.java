package me.aren.gbjam.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.enums.AsteroidType;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.ScoreHandler;

import java.util.Random;

public class AsteroidSpawner implements IGameObject {
    private GameObjectHandler objectHandler;
    private Random random;
    private long time = 0;
    private long lastSpawn = 0;
    private long spawnCooldown = 2000;
    private int spawnCount = 0;
    private ScoreHandler scoreHandler;

    public AsteroidSpawner(GameObjectHandler objectHandler, ScoreHandler scoreHandler) {
        this.objectHandler = objectHandler;
        this.scoreHandler = scoreHandler;
        random = new Random();

        objectHandler.addObject(this);
    }

    private void startSpawnCounter() {
        time = System.currentTimeMillis();
        if (time > lastSpawn + spawnCooldown) {
            spawnAsteroid();
        }
    }

    private void spawnAsteroid() {
        System.out.println("Spawn asteroid");

        if(spawnCount % 5 == 0 && spawnCount != 0 && spawnCooldown > 1000) {
            spawnCooldown -= 300;
        }

        AsteroidType spawnType = AsteroidType.values()[random.nextInt(0, 3)];
        Vector2 spawnPos = new Vector2(random.nextInt(0, spawnType == AsteroidType.LARGE ? 130 : 140), spawnType == AsteroidType.LARGE ? 120 : 130);
        new Asteroid(objectHandler, spawnType, scoreHandler, spawnPos);

        lastSpawn = System.currentTimeMillis();
        spawnCount++;
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

    }
}
