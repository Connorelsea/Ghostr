package com.elsealabs.ghostr;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ScreenGame2 extends ScreenObject implements InputProcessor
{
	/** Holds instances of entities for this screen */
	private EntityManager entityManager;
	
	private EntityPlayer entity_player;
	
	/** Viewing and camera related objects */
	private OrthographicCamera camera;
	private ScreenViewport viewport;
	private SpriteBatch batch;
	private Box2DDebugRenderer render;
	
	private Sprite sprite_floor;
	private Sprite test;
	
	private RayHandler rayHandler;
	private World world;
	
	Vector2 startPosition, targetPosition;
	float time = 0;
	float startAngle, targetAngle, newAngle;
	boolean moving;
	
	private Vector2 lastTouch;
	
	/** Touch and moving handling */
	private Vector2 touch_target = new Vector2(150, 0);
	private Rot2D touch_angleCur = Rot2D.fromDegrees(90);
	private Rot2D touch_angleWant;
	
	/** Animation states and their properties */
	
	private boolean flickering = false;
	private boolean flick_lightOn = false;
	private int flick_timeMin = 100;
	private int flick_timeMax = 300;
	private int flick_timeWaitDark;
	private long flick_timeStart;
	private long flick_timeElapsed;
	private int flick_amount;
	private int[] flick_elapses;

	public ScreenGame2(GameObject game, String name)
	{
		super(game, name);
	}

	@Override
	public void show()
	{
		/** Create camera related objects */
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		viewport.setUnitsPerPixel(1 / 32f);
		render = new Box2DDebugRenderer();
		
		/** Create Box2D related variables */
		world = new World(new Vector2(0, 0f), true);
		rayHandler = new RayHandler(world);
		
		/** Prepare scene rendering */
		batch = new SpriteBatch();
		sprite_floor = new Sprite(new Texture(Gdx.files.internal("wood.png")));
		sprite_floor.setPosition(0, 0);
		
		/** Create entities */
		
		entityManager = new EntityManager();
		
		entity_player = new EntityPlayer(batch, world, camera, rayHandler);
		entityManager.addEntity(entity_player);
		entityManager.addEntity(new EntityGhost(batch, world, camera, rayHandler));
		
		Gdx.input.setInputProcessor(this);
		
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		doFlicker();
	}

	@Override
	public void render(float delta)
	{
		
		/** Update animation states */
		
		if (flickering)
		{
			
			// Get time difference since last render
			flick_timeElapsed = System.currentTimeMillis() - flick_timeStart;
			
			//System.out.println(flick_timeElapsed);
			
			if (flick_amount > 0 && flick_lightOn == true && flick_timeElapsed >= flick_elapses[flick_amount - 1])
			{
				System.out.printf("Lights on, waited %dms to turn off\nTurning Lights off\n", flick_timeElapsed);
				
				// Turn off lights
				int rand = 1 + (int) (Math.random() * ((2 - 1) + 1));
				if (rand == 1) entity_player.getCone().setActive(false);
				if (rand == 2) entity_player.getInnerCone().setActive(false);
				flick_lightOn = false;
				
				// Reset time values
				flick_timeElapsed = 0;
				flick_timeStart = System.currentTimeMillis();
				
				// Find time to wait in the dark
				flick_timeWaitDark = 80 + (int) (Math.random() * ((200 - 50) + 1));
				
				// Subtract from amount of flickers left to do
				flick_amount--;
				
				System.out.println("Now waiting to turn back on\n");
			}
			
			if (flick_amount > 0 && flick_lightOn == false && flick_timeElapsed >= flick_timeWaitDark)
			{
				System.out.printf("Lights off, waited %dms to turn on again\nTurning on\n\n", flick_timeElapsed);
				
				//Turn on lights
				int rand = 1 + (int) (Math.random() * ((2 - 1) + 1));
				if (rand == 1) entity_player.getCone().setActive(true);
				if (rand == 2) entity_player.getInnerCone().setActive(true);
				flick_lightOn = true;
				
				// Reset time values
				flick_timeElapsed = 0;
				flick_timeStart = System.currentTimeMillis();
			}
			
			// If amount of flickers left to do is zero, set flickering to false and turn lights back on
			if (flick_amount == 0)
			{
				entity_player.getCone().setActive(true);
				entity_player.getInnerCone().setActive(true);
				flickering = false;
			}
		}
		
		/** Touch handling */
		
		if (Gdx.input.isTouched())
		{
			Vector3 tmp = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			touch_target = new Vector2(tmp.x, tmp.y);
		}

		touch_angleWant = Rot2D.fromVector(
			touch_target.x - entity_player.getBody().getPosition().x,
			touch_target.y - entity_player.getBody().getPosition().y
		);
			
		double cross1 = Rot2D.cross(touch_angleCur, touch_angleWant);
		
		if (cross1 > 0.0)
			touch_angleCur.rotate(Rot2D.fromDegrees(5.0));
		else
			touch_angleCur.rotate(Rot2D.fromDegrees(-5.0));
		
		double cross2 = Rot2D.cross(touch_angleCur, touch_angleWant);
		
		if (Math.signum(cross1) != Math.signum(cross2))
			touch_angleCur.load(touch_angleWant);
		
		entity_player.getBody().setTransform(entity_player.getBody().getPosition(), (float) (touch_angleCur.getAngle()));
			
		/** Update rest */
		
		world.step(1/60f, 6, 2);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.set(entity_player.getBody().getPosition().x, entity_player.getBody().getPosition().y, 0);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			sprite_floor.setSize(40, 40);
			sprite_floor.draw(batch);
		batch.end();
		
		/** Render all entities */
		entityManager.render();
		//render.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void hide()
	{
		
	}

	@Override
	public void dispose()
	{
		
	}
	
	public void doFlicker()
	{
		// Determine amount of times to flicker
		flick_amount = 5 + (int) (Math.random() * ((10 - 5) + 1));
		flick_elapses = new int[flick_amount];
		
		// Determine off time for each flicker
		for (int i = 0; i < flick_amount; i++)
			flick_elapses[i] = flick_timeMin + (int) (Math.random() * ((flick_timeMax - flick_timeMin) + 1));
		
		// Set the time the flickering started
		flick_timeStart = System.currentTimeMillis();
		
		// Trigger the start of the flickering
		flickering = true;
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub	
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		System.out.println("Touch down");
		if (lastTouch == null) lastTouch = new Vector2(screenX, screenY);
		else lastTouch.set(screenX, screenY);
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		   
		   return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		moving = false;
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) { return false;	}

	@Override
	public boolean scrolled(int amount) { return false;	}

}