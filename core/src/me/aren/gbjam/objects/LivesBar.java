package me.aren.gbjam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.GameStateHandler;

public class LivesBar implements IGameObject {
    private final String SPR_SPACESHIP_PATH         = "sprites/spaceship.png";

    private Texture texSpaceship;
    private GameStateHandler gameStateHandler;
    private GameObjectHandler objectHandler;

    public LivesBar(GameObjectHandler objectHandler, GameStateHandler gameStateHandler) {
        this.objectHandler = objectHandler;
        this.gameStateHandler = gameStateHandler;
        texSpaceship = new Texture(Gdx.files.internal(SPR_SPACESHIP_PATH));

        objectHandler.addObject(this);
    }

    @Override
    public void update(float delta) {
        if(gameStateHandler.getLives() == 0) gameStateHandler.restartScreen();
    }

    @Override
    public void render(SpriteBatch sb) {
        for(int i = 0; i < gameStateHandler.getLives(); i++) {
            sb.draw(texSpaceship, 16*i, 126);
        }
    }

    @Override
    public void dispose() {

    }
}
