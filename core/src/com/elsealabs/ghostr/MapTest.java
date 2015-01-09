package com.elsealabs.ghostr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class MapTest extends Map {

	@Override
	public void define() {

		MapWallSection[] wallTest_sec = {
			new MapWallSection(MapWallSection.TYPE.WALL, 3, null),
			new MapWallSection(MapWallSection.TYPE.WINDOW, 3, null),
			new MapWallSection(MapWallSection.TYPE.WALL, 3, null)
		};
		
		MapWall wallTest = new MapWall(
			this,
			this.getWorld(),
			new Vector2(0, 0),
			1f,
			MapWall.ORIENTATION.VERTICAL,
			wallTest_sec
		);
		
		this.getWalls().add(wallTest);
		
		MapWallSection[] wallTest_sec2 = {
			new MapWallSection(MapWallSection.TYPE.WALL, 5, new Sprite( new Texture(Gdx.files.internal("test.png")) )),
			new MapWallSection(MapWallSection.TYPE.WINDOW, 3, null),
			new MapWallSection(MapWallSection.TYPE.WALL, 5, null)
		};
		
		MapWall wallTest2 = new MapWall(
			this,
			this.getWorld(),
			new Vector2(wallTest.getThickness(), 0),
			1f,
			MapWall.ORIENTATION.HORIZONTAL,
			wallTest_sec2
		);
		
		this.getWalls().add(wallTest2);
		
	}

}