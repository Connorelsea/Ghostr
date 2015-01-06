package com.elsealabs.ghostr;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	private float length;
	
	private BodyDef bdef;
	private Body body;
	private PolygonShape shape;
	private FixtureDef fdef;
	
	private float pixelWidth;
	private float pixelLength;
	
	private Sprite sprite;
	private boolean hasSprite = false;
	
	/**
	 * Constructor taking all the necessary values for creating
	 * a section of one of the walls in the map.
	 * 
	 * @param type The type of the section
	 */
	public MapWallSection(MapWall wall, TYPE type, float length, Sprite sprite)
	{
		this.wall = wall;
		this.type = type;
		this.length = length;
		
		if (sprite != null)
		{
			this.setSprite(sprite);
			setHasSprite(true);
		}
		
		_initCollision();
	}
	
	private void _initCollision()
	{
		fdef = new FixtureDef();
		
		if (type == TYPE.DOOR || type == TYPE.WINDOW)
		{
			fdef.filter.categoryBits = Map.BIT_WALL_TRANS;
			fdef.filter.maskBits     = Map.MASK_WALL_TRANS;
		}
		else if (type == TYPE.WALL)
		{
			fdef.filter.categoryBits = Map.BIT_WALL_SOLID;
			fdef.filter.maskBits     = Map.MASK_WALL_SOLID;
		}
		
	}
	
	public void render(SpriteBatch batch)
	{
		if (hasSprite()) {
			sprite.draw(batch);
		}
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
	
	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getPixelWidth() {
		return pixelWidth;
	}

	public void setPixelWidth(float pixelWidth) {
		this.pixelWidth = pixelWidth;
	}

	public float getPixelLength() {
		return pixelLength;
	}

	public void setPixelLength(float pixelLength) {
		this.pixelLength = pixelLength;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public boolean hasSprite() {
		return hasSprite;
	}

	public void setHasSprite(boolean hasSprite) {
		this.hasSprite = hasSprite;
	}

}