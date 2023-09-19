package me.aren.gbjam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.enums.AsteroidType;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;

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

    public Asteroid(GameObjectHandler objectHandler, AsteroidType type, Vector2 pos) {
        this.objectHandler = objectHandler;
        this.type = type;
        this.pos = pos;

        texTinyAsteroid   = new Texture(Gdx.files.internal(SPR_ASTEROID_TINY));
        texMediumAsteroid = new Texture(Gdx.files.internal(SPR_ASTEROID_MEDIUM));
        texLargeAsteroid  = new Texture(Gdx.files.internal(SPR_ASTEROID_LARGE));

        objectHandler.addObject(this);
    }
    @Override
    public void update(float delta) {

        if(pos.y < -32) dispose();

        switch (type) {
            case TINY:
                pos.y -= 110f * delta;
                break;
            case MEDIUM:
                pos.y -= 80f * delta;
                break;
            case LARGE:
                pos.y -= 40f * delta;
                break;
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
