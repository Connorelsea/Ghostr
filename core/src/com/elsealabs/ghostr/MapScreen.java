package com.elsealabs.ghostr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MapScreen extends ScreenObject {
	
	
	private Map map;
	private boolean hasMap = false;
	private boolean debug = true;

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
			
			if (debug)
			{
				
				System.out.println("DEBUG");
				
				if (Gdx.input.justTouched()) {
					
//					Vector3 pos = map.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
//					
//					MapWall wall = null;
//					
//					MapWallSection[] wallTest_sec = {
//							new MapWallSection(wall, MapWallSection.TYPE.WALL, 3, null),
//							new MapWallSection(wall, MapWallSection.TYPE.WINDOW, 3, null),
//							new MapWallSection(wall, MapWallSection.TYPE.WALL, 3, null)
//						};
//					
//					map.getWalls().add(
//						wall = new MapWall(
//							map,
//							map.getWorld(),
//							new Vector2(pos.x, pos.y),
//							2,
//							MapWall.ORIENTATION.VERTICAL,
//							wallTest_sec
//						)
//					);
				}
				
			}
			
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

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
}