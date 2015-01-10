package com.elsealabs.ghostr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.elsealabs.ghostr.MapWall.ORIENTATION;

public class MapScreen extends ScreenObject {
	
	
	private Map map;
	private boolean hasMap;
	
	private boolean debug;
	
	private enum RENDER_MODE {
		IDLE, /** Choose to place a wall or a entity */
		WALL_ORIENTATION, /** Choose wall orientation */
		WALL_CREATION, /** Input the order of wall sections or place wall */
		ENTITY
	}
	private RENDER_MODE renderMode;
	
	private BitmapFont font;
	private ORIENTATION debugOrientation;
	private MapWall debugWall;

	public MapScreen(GameObject game, String name) {
		super(game, name);
		_setDefaults();
	}
	
	private void _setDefaults()
	{
		hasMap = false;
		debug = true;
		renderMode = RENDER_MODE.IDLE;
		font = new BitmapFont();
		font.setScale(0.1f);
	}
	
	public void show()
	{
		map.show();
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta) {
		
		if (hasMap)
		{
			
			map.update();
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			map.render();
			if (map.isRenderDebug()) map.getRender().render(map.getWorld(), map.getCamera().combined);
			
			if (debug) _doDebug();
		}
		
	}

	@Override
	public void resize(int width, int height) {
		map.getViewport().update(width, height, false);
	}
	
	@Override
	public void dispose() {
		map.dispose();
	}

	public void pause() { }
	public void resume() { }
	public void hide() { }
	
	private void _doDebug()
	{
		
		System.out.println("DEBUG");
		
		if (renderMode == RENDER_MODE.IDLE)
		{
			if (Gdx.input.isKeyJustPressed(Keys.W)) renderMode = RENDER_MODE.WALL_ORIENTATION;
			if (Gdx.input.isKeyJustPressed(Keys.E)) renderMode = RENDER_MODE.ENTITY;
		}
		
		if (renderMode == RENDER_MODE.WALL_ORIENTATION)
		{
			
			if (Gdx.input.isKeyJustPressed(Keys.V))
			{
				debugOrientation = ORIENTATION.VERTICAL;
				renderMode = RENDER_MODE.WALL_CREATION;
			}
			else if (Gdx.input.isKeyJustPressed(Keys.H))
			{
				debugOrientation = ORIENTATION.HORIZONTAL;
				renderMode = RENDER_MODE.WALL_CREATION;
			}
			
		}
		
		if (Gdx.input.justTouched())
		{
			
			
		}
		
		map.getBatch().begin();
			font.draw(map.getBatch(), "DEBUG", 0, 10);
		map.getBatch().end();
		
	}
	
	/** Getters and setters */
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		hasMap = true;
		this.map = map;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
}