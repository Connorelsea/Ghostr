package com.elsealabs.ghostr;

import box2dLight.ConeLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class ScreenGame extends ScreenObject implements InputProcessor {
	
	private float PPM = 100;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private Body body;
	
	private RayHandler rayHandler;
	private ConeLight cone;
	
	private OrthographicCamera b2dcam;
	
	// stored elsewhere:
	float duration = .5f;
	      
	Vector2 startPosition, targetPosition;
	float time = 0;
	float startAngle, targetAngle;
	boolean moving;

	
	public ScreenGame(GameObject game, String name) {
		super(game, name);
	}
	
	@Override
	public void render(float delta)
	{
		
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			   time = 0;
			   startPosition = body.getPosition();
			   Vector3 m3 = b2dcam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			   targetPosition = new Vector2(m3.x, m3.y);
			   startAngle = body.getAngle();
			   targetAngle = targetPosition.cpy().sub(startPosition).angleRad();
			            
			   // make sure we take smallest angle path, this was the weird angles
			   if (Math.abs(targetAngle - startAngle) > MathUtils.PI) {
			      if (startAngle < targetAngle) {
			         startAngle += MathUtils.PI2;
			      } else {
			         targetAngle += MathUtils.PI2;
			      }
			   }
			   moving = true;
			}
			         
			if (moving) {
			   float alpha = time / duration;
			   if (alpha <= 1) {
			      // Interpolation.circleOut also looks good I think
			      float currentAngle = Interpolation.linear.apply(startAngle, targetAngle, alpha);
			      Vector2 currentPosition = startPosition.cpy().lerp(targetPosition, alpha);
			      body.setTransform(currentPosition, currentAngle);
			               
			      time += Gdx.graphics.getDeltaTime();
			   } else {
			      moving = false;
			   }
			}
		
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Step physics simulation
		world.step(1f/60f, 6, 2);
		
		// draw box2d world
		b2dr.render(world, b2dcam.combined);
		
		
		rayHandler.setCombinedMatrix(b2dcam.combined);
		rayHandler.updateAndRender();		
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Dynamic scaling.
	}

	@Override
	public void show()
	{
		world = new World(new Vector2(0, 0f), true);
		b2dr = new Box2DDebugRenderer();
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(5 / PPM, 5 / PPM);

		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / PPM, 200 / PPM);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		body.createFixture(fdef);
		
		rayHandler = new RayHandler(world);
		cone = new ConeLight(rayHandler, 40, Color.WHITE, 30, 160 / PPM, 200 / PPM, -90, 40);
		
		cone.attachToBody(body);
		
		// set up box2d cam
		b2dcam = new OrthographicCamera();
		b2dcam.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		
		// set up input processor
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		System.out.println("[Hiding Game]");
	}

	@Override
	public void pause() {
		System.out.println("[Pausing Game]");
	}

	@Override
	public void resume() {
		System.out.println("[Resuming Game]");
	}

	@Override
	public void dispose()
	{
		
	}
	
	/*
	 *  INPUT HANDLING
	 */
	
	@Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        
    	if(button == Buttons.LEFT){
            body.setTransform(body.getPosition(), (float) (Math.atan2( (body.getPosition().y - screenY),
            											   (screenX - body.getPosition().x) ) ));
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}