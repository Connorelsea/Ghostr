package com.elsealabs.ghostr;

import com.badlogic.gdx.math.Vector2;

public class MapTest extends Map {

	@Override
	public void define() {
		
		MapWall wallTest = null;
		
		MapWallSection[] wallTest_sec = {
			new MapWallSection(wallTest, MapWallSection.TYPE.WALL, 3),
			new MapWallSection(wallTest, MapWallSection.TYPE.WINDOW, 3),
			new MapWallSection(wallTest, MapWallSection.TYPE.WALL, 3)
		};
		
		wallTest = new MapWall(
			this.getWorld(),
			new Vector2(2f, 0),
			.5f,
			MapWall.ORIENTATION.VERTICAL,
			wallTest_sec
		);
		
		this.getWalls().add(wallTest);
		
		MapWall wallTest2 = null;
		
		MapWallSection[] wallTest_sec2 = {
			new MapWallSection(wallTest, MapWallSection.TYPE.WALL, 3),
			new MapWallSection(wallTest, MapWallSection.TYPE.WINDOW, 3),
			new MapWallSection(wallTest, MapWallSection.TYPE.WALL, 3)
		};
		
		wallTest2 = new MapWall(
			this.getWorld(),
			new Vector2(0, 0),
			.5f,
			MapWall.ORIENTATION.HORIZONTAL,
			wallTest_sec2
		);
		
		this.getWalls().add(wallTest2);
		
	}

}