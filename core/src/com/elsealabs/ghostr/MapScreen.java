package com.elsealabs.ghostr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MapScreen extends ScreenObject {
	
	
	private Map map;
	private boolean hasMap = false;

	public MapScreen(GameObject game, String name) {
		super(game, name);
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
	
	/** Getters and setters */
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		hasMap = true;
		this.map = map;
	}
	
}