package me.aren.gbjam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import me.aren.gbjam.interfaces.IGameObject;
import me.aren.gbjam.util.GameObjectHandler;
import me.aren.gbjam.util.ScoreHandler;

public class Spaceship implements IGameObject {
	private final String SPR_SPACESHIP_PATH         = "sprites/spaceship.png";
	private final String SPR_SPACESHIP_POWERED_PATH = "sprites/spaceship_powered.png";
	private final String SND_CANT_SHOOT				= "audio/cant_shoot.wav";
	private final String SND_SHOOT					= "audio/shoot.wav";
	
	private Texture texSpaceship;
	private Texture texSpaceshipPowered;
	
	private Sound sndCantShoot;
	private Sound sndShoot;
	
	private GameObjectHandler objHandler;
	private ScoreHandler scoreHandler;
	
	private Vector2 pos;
	private float speed = 100f;
	private long lastShoot = 0;
	private long shootCooldown = 500;
	private long time = 0;
	boolean canShoot = true;
	
	public Spaceship(GameObjectHandler objHandler) {
		this.scoreHandler = scoreHandler;
		// TODO Auto-generated constructor stub
		texSpaceship 		= new Texture(Gdx.files.internal(SPR_SPACESHIP_PATH));
		texSpaceshipPowered = new Texture(Gdx.files.internal(SPR_SPACESHIP_POWERED_PATH));
		sndCantShoot		= Gdx.audio.newSound(Gdx.files.internal(SND_CANT_SHOOT));
		sndShoot			= Gdx.audio.newSound(Gdx.files.internal(SND_SHOOT));
		this.objHandler 	= objHandler;
		pos					= new Vector2((160 / 2) - 8, 4);
		objHandler.addObject(this);
	}
	
	private void cooldown() {
	    time = System.currentTimeMillis();
	    if (time > lastShoot + shootCooldown) {
	        canShoot = true;
	    }
	} 
	
	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
		// TODO: CLAMP
		cooldown();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			pos.x += speed * delta;
		} else if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			pos.x -= speed * delta;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Z) && canShoot) {
			new Bullet(new Vector2(pos.x + 4, pos.y + 10), objHandler);
			lastShoot = System.currentTimeMillis();
			canShoot = false;
			sndShoot.play();
		}
		if(Gdx.input.isKeyJustPressed(Keys.Z) && !canShoot && time - lastShoot > 100) {
			sndCantShoot.play();
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		sb.draw(texSpaceshipPowered, pos.x, pos.y);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		texSpaceship.dispose();
		texSpaceshipPowered.dispose();
	}
}
