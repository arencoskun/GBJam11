package me.aren.gbjam.objects.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.objects.Bullet;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.SettingsHandler;

public class TutorialAsteroid implements IGameObject {
    private final String SPR_ASTEROID_LARGE	 = "sprites/asteroid_large.png";
    private final String SPR_ASTEROID_MED	 = "sprites/asteroid_medium.png";
    private final String SND_EXPLOSION       = "audio/explosion.wav";

    private Texture texLargeAsteroid;
    private GameObjectHandler objectHandler;
    private Vector2 pos;
    private Rectangle hitbox;
    private Sound explosionSound;
    private boolean isBulletHit;
    private SettingsHandler settingsHandler;

    public TutorialAsteroid(GameObjectHandler objectHandler, SettingsHandler settingsHandler, Vector2 pos) {
        this.objectHandler = objectHandler;
        this.pos = pos;
        this.settingsHandler = settingsHandler;

        texLargeAsteroid = new Texture(Gdx.files.internal(SPR_ASTEROID_LARGE));
        int hitboxWidth = texLargeAsteroid.getWidth();
        int hitboxHeight = texLargeAsteroid.getHeight();
        int hitboxAddition = 10;
        hitbox = new Rectangle(pos.x - (float) hitboxAddition / 2, pos.y - (float) hitboxAddition / 2, hitboxWidth + hitboxAddition, hitboxHeight + hitboxAddition);
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(SND_EXPLOSION));
        objectHandler.addObject(this);
    }

    @Override
    public void update(float delta) {
        Bullet collision = objectHandler.checkIfBulletColliding(hitbox);

        if(collision != null) {
            isBulletHit = true;
            if(settingsHandler.getBool("sound")) explosionSound.play();
            objectHandler.removeObject(collision);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(texLargeAsteroid, pos.x, pos.y);
    }

    @Override
    public void dispose() {
        texLargeAsteroid.dispose();
        explosionSound.dispose();
        objectHandler.removeObject(this);
    }

    public boolean isBulletHit() {
        return isBulletHit;
    }
}
