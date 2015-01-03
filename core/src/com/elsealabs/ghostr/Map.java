package com.elsealabs.ghostr;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class Map
{
	/** Relative to display and rendering */
	private OrthographicCamera camera;
	private ScreenViewport viewport;
	private SpriteBatch batch;
	private Box2DDebugRenderer render;
	private boolean renderDebug;
	
	private World world;
	
	public Map()
	{
		_init();
	}
	
	private void _init()
	{
		/** Create camera related objects */
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		viewport.setUnitsPerPixel(1 / 32f); 
		render = new Box2DDebugRenderer();
	}
	
	public abstract void show();
	public abstract void render();
	public abstract void dispose();
	
	/** Getters and setters */
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public ScreenViewport getViewport() {
		return viewport;
	}

	public void setViewport(ScreenViewport viewport) {
		this.viewport = viewport;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public Box2DDebugRenderer getRender() {
		return render;
	}

	public void setRender(Box2DDebugRenderer render) {
		this.render = render;
	}

	public boolean isRenderDebug() {
		return renderDebug;
	}

	public void setRenderDebug(boolean renderDebug) {
		this.renderDebug = renderDebug;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
}