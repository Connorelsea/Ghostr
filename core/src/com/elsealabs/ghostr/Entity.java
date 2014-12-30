package com.elsealabs.ghostr;

import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
	
	/** Entity meta-data related objects */
	private String userData;
	private boolean visible;
	
	/** Box2D related objects */
	private Body body;
	private BodyDef bdef;
	private FixtureDef fdef;
	private PolygonShape shape;
	
	/** Objects from other classes */
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private RayHandler rayHandler;
	
	/** Sprite and image related objects */
	private Sprite sprite;
	private Texture texture;
	
	public Entity(SpriteBatch batch, World world, OrthographicCamera camera, RayHandler rayHandler)
	{
		this.setBatch(batch);
		this.setWorld(world);
		this.setCamera(camera);
		this.setRayHandler(rayHandler);
		visible = true;
	}
	
	/** Abstract methods to be implemented by the child class */
	
	public abstract void show();
	public abstract void render();
	public abstract void dispose();
	
	/** Add this Entity's Box2D body to the world */
	
	public void createBody(World world)
	{
		body = world.createBody(this.getBdef());
	}
	
	/** Getters and setter */
	
	public String getUserData() {
		return userData;
	}
	
	public void setUserData(String userData) {
		this.userData = userData;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Body getBody() {
		return body;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}
	
	public BodyDef getBdef() {
		return bdef;
	}
	
	public void setBdef(BodyDef bdef) {
		this.bdef = bdef;
	}
	
	public FixtureDef getFdef() {
		return fdef;
	}
	
	public void setFdef(FixtureDef fdef) {
		this.fdef = fdef;
	}
	
	public PolygonShape getShape() {
		return shape;
	}
	
	public void setShape(PolygonShape shape) {
		this.shape = shape;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public RayHandler getRayHandler() {
		return rayHandler;
	}

	public void setRayHandler(RayHandler rayHandler) {
		this.rayHandler = rayHandler;
	}
	
}