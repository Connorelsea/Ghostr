package com.elsealabs.ghostr;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class EntityPlayer extends Entity {
	
	private RayHandler rayHandler;
	private ConeLight  cone;
	private ConeLight  innerCone;

	public EntityPlayer(SpriteBatch batch, World world, OrthographicCamera camera, RayHandler rayHandler)
	{
		super(batch, world, camera, rayHandler);
	}

	@Override
	public void show()
	{
		this.setBdef(new BodyDef());
		this.getBdef().type = BodyDef.BodyType.DynamicBody;
		this.getBdef().position.set(new Vector2(0, 0));
		this.setBody(this.getWorld().createBody(this.getBdef()));
		
		this.setShape(new PolygonShape());
		this.getShape().setAsBox(.5f, .5f);
		
		this.setFdef(new FixtureDef());
		this.getFdef().shape = this.getShape();
		this.getFdef().density = .1f;
		
		this.getBody().createFixture(this.getFdef());
		
		cone = new ConeLight(getRayHandler(), 40, Color.GRAY, 30, 0, 0, 180, 28);
		innerCone = new ConeLight(getRayHandler(), 50, Color.GRAY, 30, 0, 0, 180, 18);
		innerCone.setSoft(false);
		cone.attachToBody(this.getBody());
		innerCone.attachToBody(this.getBody());
		
		this.getBody().setTransform(new Vector2(10, 13), 360);
	}
	
	@Override
	public void update()
	{
		//TODO move entity update code to this (refactoring) (player)
	}

	@Override
	public void render()
	{
		rayHandler.setCombinedMatrix(this.getCamera().combined);
		rayHandler.updateAndRender();
	}

	@Override
	public void dispose()
	{
		
	}
	
	/** Getters and setters */
	
	public RayHandler getRayHandler() {
		return rayHandler;
	}

	public void setRayHandler(RayHandler rayHandler) {
		this.rayHandler = rayHandler;
	}

	public ConeLight getCone() {
		return cone;
	}

	public void setCone(ConeLight cone) {
		this.cone = cone;
	}

	public ConeLight getInnerCone() {
		return innerCone;
	}

	public void setInnerCone(ConeLight innerCone) {
		this.innerCone = innerCone;
	}
	
}