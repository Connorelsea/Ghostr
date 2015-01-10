package com.elsealabs.ghostr;

import java.util.ArrayList;

import box2dLight.BlendFunc;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
	
	/** Touch and moving handling */
	private Vector2 touch_prev = new Vector2(0, 0);
	private Vector2 touch_target = new Vector2(150, 0);
	private Rot2D touch_angleCur = Rot2D.fromDegrees(90);
	private Rot2D touch_angleWant;
	
	/** Bits for collision filtering */
	public static final short BIT_WALL_SOLID = 0x0001;
	public static final short BIT_WALL_TRANS = 0x0002;
	public static final short BIT_LIGHT      = 0x0004;
	public static final short BIT_ENTITY     = 0x0008;
	
	public static final short MASK_WALL_SOLID = BIT_LIGHT;
	public static final short MASK_WALL_TRANS = (short) 0;
	public static final short MASK_LIGHT      = BIT_WALL_SOLID;
	public static final short MASK_ENTITY     = BIT_WALL_SOLID | BIT_LIGHT;
	
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
		
		RayHandler.setGammaCorrection(false);
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);
		
		walls = new ArrayList<MapWall>();
		entityManager = new EntityManager();
		
		/** Create important entities */
		player = new EntityPlayer(batch, world, camera, rayHandler);
		entityManager.addEntity(player);
		
		Filter lightFilter = new Filter();
		lightFilter.categoryBits = Map.BIT_LIGHT;
		lightFilter.maskBits     = Map.MASK_LIGHT;
		
		Light.setContactFilter(lightFilter);
		
		define();
	}

	public void update()
	{
		world.step(1/60f, 6, 2);
		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.update();
		
		_updateTouchTarget();
		_updateMovement();
		_updateTurn();
		
		camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
		camera.update();
	}

	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		entityManager.render();
		for (MapWall w : walls) w.render();
		rayHandler.render();
	}
	
	public void renderDebug()
	{
		if (isRenderDebug())
		{
			getRender().render(world, camera.combined);
		}
	}
	
	public void dispose()
	{
		
	}
	
	/** Update methods */
	
	private void _updateTouchTarget()
	{
		if (Gdx.input.isTouched())
		{
			Vector3 tmp = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			touch_prev = touch_target;
			touch_target = new Vector2(tmp.x, tmp.y);
		}
	}
	
	private void _updateMovement()
	{

	}
	
	private void _updateTurn()
	{

		touch_angleWant = Rot2D.fromVector(
			touch_target.x - player.getBody().getPosition().x,
			touch_target.y - player.getBody().getPosition().y
		);
			
		double cross1 = Rot2D.cross(touch_angleCur, touch_angleWant);
		
		if (cross1 > 0.0) touch_angleCur.rotate(Rot2D.fromDegrees(10.0));
		else touch_angleCur.rotate(Rot2D.fromDegrees(-10.0));
		
		double cross2 = Rot2D.cross(touch_angleCur, touch_angleWant);
		
		if (Math.signum(cross1) != Math.signum(cross2))
			touch_angleCur.load(touch_angleWant);
		
		player.getBody().setTransform(player.getBody().getPosition(), (float) (touch_angleCur.getAngle()));
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