package com.elsealabs.ghostr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScreenLogo extends ScreenObject
{
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private int virtualWidth  = 720;
	private int virtualHeight = 1280;
	
	private SpriteBatch batch;
	private Sprite[] sprites;
	private int currentSprite = 1;
	private boolean calculatingSprites = true;
	private boolean in = true;
	
	private float time = 0;

	public ScreenLogo(GameObject game, String name)
	{
		super(game, name);
	}

	@Override
	public void show()
	{	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, virtualWidth, virtualHeight);
		camera.translate(virtualWidth / 2, virtualHeight / 2);
		
		viewport = new FitViewport(virtualWidth, virtualHeight);
		
		sprites = new Sprite[2];
		
		batch = new SpriteBatch();
		sprites[0] = new Sprite( new Texture(Gdx.files.internal("logo.png")) );
		sprites[0].setBounds(virtualWidth / 2 - 650 / 2, virtualHeight / 2 - 120 / 2, 650, 100);
		sprites[0].setAlpha(0);
		
		sprites[1] = new Sprite( new Texture(Gdx.files.internal("music.png")) );
		sprites[1].setBounds(virtualWidth / 2 - 650 / 2, virtualHeight / 2 - 120 / 2, 650, 100);
		sprites[1].setAlpha(0);
	}

	@Override
	public void render(float delta)
	{
		/** Calculate fading */
		
		if (calculatingSprites)
		{
			
			if (in == true)
			{
				time += Gdx.graphics.getDeltaTime();
				float alpha = MathUtils.lerp(0, 1, time / 5f);
				sprites[currentSprite - 1].setAlpha(alpha);
				
				if (alpha >= 1)
				{
					in = false;
					time = 0;
				}
			}
			else
			{
				time += Gdx.graphics.getDeltaTime();
				float alpha = MathUtils.lerp(1, 0, time / 5f);
				sprites[currentSprite - 1].setAlpha(alpha);
				
				if (alpha <= 0)
				{
					in = true;
					time = 0;
					currentSprite++;
					if (currentSprite > sprites.length) calculatingSprites = false;
				}
			}
			
		}
		else
		{
			done();
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			if (calculatingSprites) sprites[currentSprite - 1].draw(batch);
		batch.end();
		 
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
	}

	public void pause() { }
	public void resume() { }
	public void hide() { }
	public void dispose() { }

}