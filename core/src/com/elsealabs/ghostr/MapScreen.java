package com.elsealabs.ghostr;

public class MapScreen extends ScreenObject {
	
	private Map map;

	public MapScreen(GameObject game, String name) {
		super(game, name);
	}

	@Override
	public void show() {
		map.show();
	}

	@Override
	public void render(float delta) {
		map.render();
		if (map.isRenderDebug()) map.getRender().render(map.getWorld(), map.getCamera().combined);
	}

	@Override
	public void resize(int width, int height) {
		map.getViewport().update(width, height);
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
		this.map = map;
	}
	
}