package com.elsealabs.ghostr;

import com.badlogic.gdx.Gdx;

public class MapScreen extends ScreenObject {
	
	
	private Map map;
	private boolean hasMap;

	public MapScreen(GameObject game, String name) {
		super(game, name);
		_setDefaults();
	}
	
	private void _setDefaults()
	{
		hasMap = false;
	}
	
	public void show()
	{
		map.show();
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float delta)
	{
		if (hasMap)
		{			
			map.update();
			map.render();
			map.renderDebug();
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