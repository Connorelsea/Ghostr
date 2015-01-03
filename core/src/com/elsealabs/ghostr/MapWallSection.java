package com.elsealabs.ghostr;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class MapWallSection
{
	/** The type of the section */
	public enum TYPE { WALL, WINDOW, DOOR }
	private TYPE type;
	
	private MapWall wall;
	private int length;
	
	private BodyDef bdef;
	private Body body;
	private PolygonShape shape;
	private FixtureDef fdef;
	
	/**
	 * Constructor taking all the necessary values for creating
	 * a section of one of the walls in the map.
	 * 
	 * @param type The type of the section
	 */
	public MapWallSection(MapWall wall, TYPE type)
	{
		this.wall = wall;
		this.type = type;
	}
	
	public void createBody()
	{
		body = wall.getWorld().createBody(bdef);
		
		fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1;
		
		body.createFixture(fdef);
	}
	
	/** Getters and setters */
	
	public MapWall getWall() {
		return wall;
	}

	public void setWall(MapWall wall) {
		this.wall = wall;
	}

	public BodyDef getBdef() {
		return bdef;
	}

	public void setBdef(BodyDef bdef) {
		this.bdef = bdef;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public PolygonShape getShape() {
		return shape;
	}

	public void setShape(PolygonShape shape) {
		this.shape = shape;
	}

	public FixtureDef getFdef() {
		return fdef;
	}

	public void setFdef(FixtureDef fdef) {
		this.fdef = fdef;
	}
	
	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}