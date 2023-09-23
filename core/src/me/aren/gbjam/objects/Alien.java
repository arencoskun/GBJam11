package me.aren.gbjam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;

public class Alien implements IGameObject {
    private final String SPR_ALIEN_HAPPY					  = "sprites/alien_happy.png";
    private final String SPR_ALIEN_NEUTRAL					  = "sprites/alien_neutral.png";
    private final String SPR_ALIEN_NOFACE					  = "sprites/alien_noface.png";
    private final String SPR_ALIEN_PATTERN					  = "sprites/alien_pattern.png";
    private final String SPR_ALIEN_SAD						  = "sprites/alien_sad.png";
    private final String SND_ALIEN_SHOT                       = "audio/alien_hit.wav";
    private final String SND_ALIEN_SHOT_FIRED                 = "audio/alien_bullet.wav";

    private Texture texAlien;
    private GameObjectHandler objectHandler;
    private Vector2 pos;
    private Rectangle hitbox;
    private long time = 0;
    private long lastSpawn = 0;
    private long spawnCooldown = 3000;
    private Vector2 velocity = new Vector2(1, 0);
    private float speed = 50f;
    private Sound sndAlienShot;
    private Sound sndAlienBulletFired;
    int hitNumber = 0;
    int hitNeeded = 3;
    private long lastHit = 0;
    private long time2 = 0;


    public Alien(GameObjectHandler objectHandler, Vector2 pos) {
        this.objectHandler = objectHandler;
        this.pos = pos;

        texAlien = new Texture(Gdx.files.internal(SPR_ALIEN_NEUTRAL));
        sndAlienShot = Gdx.audio.newSound(Gdx.files.internal(SND_ALIEN_SHOT));
        sndAlienBulletFired = Gdx.audio.newSound(Gdx.files.internal(SND_ALIEN_SHOT_FIRED));
        this.pos = pos;
        hitbox = new Rectangle(pos.x, pos.y, texAlien.getWidth(), texAlien.getHeight());
        objectHandler.addObject(this);
    }

    private void startSpawnCounter() {
        time = System.currentTimeMillis();
        if (time > lastSpawn + spawnCooldown) {
            spawnBullet();
        }
    }

    private void spawnBullet() {
        new Bullet(new Vector2(pos.x + 4, pos.y - 5), objectHandler, true);
        new Bullet(new Vector2(pos.x - 12, pos.y - 5), objectHandler, true);
        new Bullet(new Vector2(pos.x + 20, pos.y - 5), objectHandler, true);
        sndAlienBulletFired.play();
        lastSpawn = System.currentTimeMillis();
    }

    @Override
    public void update(float delta) {
        if(System.currentTimeMillis() > lastHit + 1000) {
            if(hitNumber != 2) {
                texAlien = new Texture(Gdx.files.internal(SPR_ALIEN_NEUTRAL));
            } else {
                texAlien = new Texture(Gdx.files.internal(SPR_ALIEN_SAD));
            }
        }
        hitbox.setPosition(pos);
        startSpawnCounter();

        if(pos.x > 160 - texAlien.getWidth()) {
            velocity.x = -1;
        } else if(pos.x < 0) {
            velocity.x = +1;
        }

        pos.x += speed * delta * velocity.x;

        Bullet collisionBullet = objectHandler.checkIfBulletColliding(hitbox);

        if(collisionBullet != null) {
            hitNumber++;
            sndAlienShot.play();
            texAlien = new Texture(Gdx.files.internal(SPR_ALIEN_PATTERN));
            lastHit = System.currentTimeMillis();
            objectHandler.removeBullet(collisionBullet);
            if(hitNumber >= hitNeeded) {
                dispose();
                objectHandler.removeObject(this);
            }
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(texAlien, pos.x, pos.y);
    }

    @Override
    public void dispose() {
        texAlien.dispose();
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
