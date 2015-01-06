package com.elsealabs.ghostr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class MapTest extends Map {

	@Override
	public void define() {
		
		MapWall wallTest = null;
		
		MapWallSection[] wallTest_sec = {
			new MapWallSection(wallTest, MapWallSection.TYPE.WALL, 1.5f, null),
			new MapWallSection(wallTest, MapWallSection.TYPE.WINDOW, 1, null),
			new MapWallSection(wallTest, MapWallSection.TYPE.WALL, 2, null)
		};
		
		wallTest = new MapWall(
			this,
			this.getWorld(),
			new Vector2(0, 0),
			1f,
			MapWall.ORIENTATION.VERTICAL,
			wallTest_sec
		);
		
		this.getWalls().add(wallTest);
		
		MapWall wallTest2 = null;
		
		MapWallSection[] wallTest_sec2 = {
			new MapWallSection(wallTest, MapWallSection.TYPE.WALL, 2f, new Sprite( new Texture(Gdx.files.internal("test.png")) )),
			new MapWallSection(wallTest, MapWallSection.TYPE.WINDOW, 1, null),
			new MapWallSection(wallTest, MapWallSection.TYPE.WALL, 2, null)
		};
		
		wallTest2 = new MapWall(
			this,
			this.getWorld(),
			new Vector2(wallTest.getThickness(), 0),
			.5f,
			MapWall.ORIENTATION.HORIZONTAL,
			wallTest_sec2
		);
		
		this.getWalls().add(wallTest2);
		
	}

}