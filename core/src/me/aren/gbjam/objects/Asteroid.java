package me.aren.gbjam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.enums.AsteroidType;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.ScoreHandler;

import java.util.Random;

public class Asteroid implements IGameObject {
    private final String SPR_ASTEROID_TINY	 = "sprites/asteroid_tiny.png";
    private final String SPR_ASTEROID_MEDIUM = "sprites/asteroid_medium.png";
    private final String SPR_ASTEROID_LARGE	 = "sprites/asteroid_large.png";

    private AsteroidType type;
    private Texture texTinyAsteroid;
    private Texture texMediumAsteroid;
    private Texture texLargeAsteroid;
    private GameObjectHandler objectHandler;
    private Vector2 pos;
    private Rectangle hitbox;
    private ScoreHandler scoreHandler;
    private Random random;
    int hitboxAddition = 0;

    public Asteroid(GameObjectHandler objectHandler, AsteroidType type, ScoreHandler scoreHandler, Vector2 pos) {
        this.objectHandler = objectHandler;
        this.scoreHandler = scoreHandler;
        this.type = type;
        this.pos = pos;

        random = new Random();
        texTinyAsteroid   = new Texture(Gdx.files.internal(SPR_ASTEROID_TINY));
        texMediumAsteroid = new Texture(Gdx.files.internal(SPR_ASTEROID_MEDIUM));
        texLargeAsteroid  = new Texture(Gdx.files.internal(SPR_ASTEROID_LARGE));
        int hitboxWidth = 0, hitboxHeight = 0;

        switch (type) {
            case TINY:
                hitboxWidth = texTinyAsteroid.getWidth();
                hitboxHeight = texTinyAsteroid.getHeight();
                break;
            case MEDIUM:
                hitboxWidth = texMediumAsteroid.getWidth();
                hitboxHeight = texMediumAsteroid.getHeight();
                break;
            case LARGE:
                hitboxWidth = texLargeAsteroid.getWidth();
                hitboxHeight = texLargeAsteroid.getHeight();
                break;
        }
        hitboxAddition     = type == AsteroidType.TINY ? 20 : 10;
        hitbox             = new Rectangle(pos.x - (float) hitboxAddition / 2, pos.y - (float) hitboxAddition / 2, hitboxWidth + hitboxAddition, hitboxHeight + hitboxAddition);

        objectHandler.addObject(this);
    }
    @Override
    public void update(float delta) {

        if(hitbox.getX() != pos.x || hitbox.getY() != pos.y) {
            hitbox.setX(pos.x - (float) hitboxAddition / 2);
            hitbox.setY(pos.y - (float) hitboxAddition / 2);
        }

        if(pos.y < -32) dispose();

        switch (type) {
            case TINY:
                pos.y -= 90f * delta;
                break;
            case MEDIUM:
                pos.y -= 60f * delta;
                break;
            case LARGE:
                pos.y -= 40f * delta;
                break;
        }
        Bullet collision = objectHandler.checkIfBulletColliding(hitbox);
        if(collision != null) {
            switch (type) {
                case LARGE:
                    int spawnAmountL = random.nextInt(1, 4);
                    int[] diffs = new int[4];
                    for(int j = 0; j < 4; j++) {

                        diffs[j] = random.nextInt(-20, 20);

                    }
                    for(int i = 0; i < spawnAmountL; i++) {
                        Vector2 newSpawnPosL = new Vector2(pos.x + diffs[i], pos.y + diffs[i]);
                        new Asteroid(objectHandler, AsteroidType.MEDIUM, scoreHandler, newSpawnPosL);
                    }
                    dispose();
                    break;
                case MEDIUM:
                    int spawnAmountM = random.nextInt(0, 3);
                    for(int j = 0; j < spawnAmountM; j++) {
                        Vector2 newSpawnPosM = new Vector2(pos.x + random.nextInt(-12, 12), pos.y);
                        new Asteroid(objectHandler, AsteroidType.TINY, scoreHandler, newSpawnPosM);
                    }
                    dispose();
                    break;
                default:
                    dispose();
                    break;
            }
            objectHandler.removeBullet(collision);
            scoreHandler.incrementScore(1);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        switch (type) {
            case TINY:
                sb.draw(texTinyAsteroid, pos.x, pos.y);
                break;
            case MEDIUM:
                sb.draw(texMediumAsteroid, pos.x, pos.y);
                break;
            case LARGE:
                sb.draw(texLargeAsteroid, pos.x, pos.y);
                break;
        }
    }

    @Override
    public void dispose() {
        texLargeAsteroid.dispose();
        texMediumAsteroid.dispose();
        texTinyAsteroid.dispose();

        objectHandler.removeObject(this);
    }
}
