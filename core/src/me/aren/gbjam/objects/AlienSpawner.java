package me.aren.gbjam.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.ARandom;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.SettingsHandler;

public class AlienSpawner implements IGameObject {
    private ARandom random;
    private GameObjectHandler objectHandler;

    private long time = 0;
    private long lastSpawn = 0;
    private long spawnCooldown = 5000;
    private SettingsHandler settingsHandler;

    public AlienSpawner(GameObjectHandler objectHandler, SettingsHandler settingsHandler) {
        random = new ARandom();
        this.objectHandler = objectHandler;
        this.settingsHandler = settingsHandler;

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
        new Alien(objectHandler, settingsHandler, spawnPos);

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
