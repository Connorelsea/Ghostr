package com.elsealabs.ghostr;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class EntityGhost extends Entity {
	
	private PointLight light;

	public EntityGhost(SpriteBatch batch, World world, OrthographicCamera camera, RayHandler rayHandler) {
		super(batch, world, camera, rayHandler);
	}

	@Override
	public void show()
	{	
		/** Define Box2D body */
		setBdef(new BodyDef());
		getBdef().position.set(5, 5);
		getBdef().type = BodyType.DynamicBody;
		setBody(getWorld().createBody(getBdef()));
		
		setShape(new PolygonShape());
		getShape().setAsBox(.5f, .5f);
		
		setFdef(new FixtureDef());
		getFdef().shape = getShape();
		getFdef().density = 1;
		getFdef().friction = 0.5f;
		
		getBody().createFixture(getFdef());
		
		/** Define lights */
		light = new PointLight(getRayHandler(), 30, Color.CYAN, 2, 0, 0);
		light.attachToBody(getBody());
		
		/** Define Sprite */
		changeSpriteTo(new Sprite(new Texture(Gdx.files.internal("ghost_cyan.png"))), 1.5f, 1.5f);
	}

	@Override
	public void render()
	{
		this.getBatch().begin();
			this.getSprite().draw(this.getBatch());
		this.getBatch().end();
	}

	@Override
	public void dispose()
	{
		
	}

}