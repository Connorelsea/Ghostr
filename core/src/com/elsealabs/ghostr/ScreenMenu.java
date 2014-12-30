package com.elsealabs.ghostr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenMenu extends ScreenObject
{
	
	private OrthographicCamera camera;
	
	private SpriteBatch batch;
	private Sprite sprite;
	
	private float aspectRatio;
	private int viewWidth;
	private int viewHeight;
	
	private int unitWidth;
	private int unitHeight;

	public ScreenMenu(GameObject game, String name)
	{
		super(game, name);
	}

	@Override
	public void show()
	{
		// Set up unit widths
		
		unitWidth = 720;
		unitWidth = 1280;
		
		// Setup other things
		batch = new SpriteBatch();
		sprite = new Sprite( new Texture(Gdx.files.internal("ghostr.jpg")) );
		sprite.setPosition(0, 0);
		sprite.setSize(unitWidth, unitHeight);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(19, 19, 19, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			sprite.draw(batch);
		batch.end();
		 
	}

	@Override
	public void resize(int width, int height)
	{
		viewWidth  = Gdx.graphics.getWidth();
		viewHeight = Gdx.graphics.getHeight();
		
		aspectRatio = (float) viewHeight / (float) viewWidth;
		
		camera = new OrthographicCamera();
		camera.viewportWidth  = unitWidth;
		camera.viewportHeight = unitHeight * aspectRatio;
		camera.position.set(unitWidth / 2, unitHeight / 2, 0);
		
		//camera.translate(viewWidth / 2, viewHeight / 2);
		camera.update();
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

}