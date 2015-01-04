package com.elsealabs.ghostr;

import java.util.ArrayList;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
	private boolean renderDebug = true;
	
	/** Map components */
	private EntityManager entityManager;
	private RayHandler rayHandler;
	private ArrayList<MapWall> walls;
	private World world;
	
	/** Important entities */
	private Entity player;
	
	public abstract void define();
	
	public void show()
	{
		/** Create camera related objects */
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		viewport.setUnitsPerPixel(1 / 32f);
		render = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		
		/** Create map components */
		world = new World(new Vector2(0, 0), true);
		rayHandler = new RayHandler(world);
		
		walls = new ArrayList<MapWall>();
		entityManager = new EntityManager();
		
		/** Create important entities */
		player = new EntityPlayer(batch, world, camera, rayHandler);
		entityManager.addEntity(player);
		
	}
	
	public void update()
	{
		world.step(1/60f, 6, 2);
		rayHandler.update();
	}

	public void render()
	{
		camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
		camera.update();
		
		for (MapWall w : walls) w.render();
		entityManager.render();
		rayHandler.render();
		if (renderDebug) render.render(world, camera.combined);
	}
	
	public void dispose()
	{
		
	}
	
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