package com.elsealabs.ghostr;

import java.util.ArrayList;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
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
	
	/** Bits for collision filtering */
	public static final short BIT_WALL   = 0x0002;
	public static final short BIT_WINDOW = 0x0004;
	public static final short BIT_DOOR   = 0x0008;
	public static final short BIT_LIGHT  = 0x0010;
	public static final short BIT_ENTITY = 0x0020;
	
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
		rayHandler.setCombinedMatrix(camera.combined);
		
		walls = new ArrayList<MapWall>();
		entityManager = new EntityManager();
		
		/** Create important entities */
		player = new EntityPlayer(batch, world, camera, rayHandler);
		//entityManager.addEntity(player);
		
		//Filter filter = new Filter();
		//filter.categoryBits = BIT_LIGHT;
		//filter.maskBits = BIT_WALL;
		
		PointLight.setContactFilter(Map.BIT_LIGHT, (short) 1, Map.BIT_WALL);
		new PointLight(rayHandler, 20, Color.BLUE, 50, -4f, -4f);
		
		define();
	}

	public void update()
	{
		world.step(1/60f, 6, 2);
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.update();
	}

	public void render()
	{
		//camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
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
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public RayHandler getRayHandler() {
		return rayHandler;
	}

	public void setRayHandler(RayHandler rayHandler) {
		this.rayHandler = rayHandler;
	}

	public ArrayList<MapWall> getWalls() {
		return walls;
	}

	public void setWalls(ArrayList<MapWall> walls) {
		this.walls = walls;
	}

	public Entity getPlayer() {
		return player;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}
	
}