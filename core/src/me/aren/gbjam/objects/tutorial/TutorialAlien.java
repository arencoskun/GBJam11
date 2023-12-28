package me.aren.gbjam.objects.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.objects.Bullet;
import me.aren.gbjam.util.GameObjectHandler;
import com.badlogic.gdx.audio.Sound;
import me.aren.gbjam.util.SettingsHandler;

public class TutorialAlien implements IGameObject {
    private final String SPR_ALIEN_HAPPY = "sprites/alien_happy.png";
    private final String SPR_ALIEN_SAD = "sprites/alien_sad.png";
    private final String SND_ALIEN_SHOT  = "audio/alien_hit.wav";

    private Texture texAlien;
    private GameObjectHandler objectHandler;
    private Vector2 pos;
    private Rectangle hitbox;
    private Sound sndAlienShot;
    private boolean bulletHit = false;
    private SettingsHandler settingsHandler;

    public TutorialAlien(GameObjectHandler objectHandler, SettingsHandler settingsHandler, Vector2 pos) {
        this.objectHandler = objectHandler;
        this.pos = pos;
        this.settingsHandler = settingsHandler;
        texAlien = new Texture(Gdx.files.internal(SPR_ALIEN_HAPPY));
        hitbox = new Rectangle(pos.x - 4, pos.y - 4, texAlien.getWidth() + 8, texAlien.getHeight() + 8);
        sndAlienShot = Gdx.audio.newSound(Gdx.files.internal(SND_ALIEN_SHOT));

        objectHandler.addObject(this);
    }

    @Override
    public void update(float delta) {
        hitbox.setPosition(pos);
        Bullet collisionBullet = objectHandler.checkIfBulletColliding(hitbox);

        if(collisionBullet != null) {
            if(settingsHandler.getBool("sound")) sndAlienShot.play();
            objectHandler.removeBullet(collisionBullet);
            bulletHit = true;
            texAlien = new Texture(Gdx.files.internal(SPR_ALIEN_SAD));
            //dispose();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(texAlien, pos.x, pos.y);
    }

    @Override
    public void dispose() {
        texAlien.dispose();
        sndAlienShot.dispose();
        objectHandler.removeObject(this);
    }

    public boolean isBulletHit() {
        return bulletHit;
    }
}
