package com.elsealabs.ghostr;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Holds the instance of a wall on a map.
 * 
 * @author Connor Elsea
 */
public class MapWall
{
	
	/** Information about the wall */
	private Vector2 position;
	private Vector2 size;
	private int thickness;
	
	private World world;
	
	public enum ORIENTATION { VERTICAL, HORIZONTAL }
	private ORIENTATION orientation;
	
	/** Wall section information */
	private ArrayList<MapWallSection> sections;
	
	/**
	 * Constructor taking in all necessary values for the
	 * creation of a wall.
	 * 
	 * @param position The position of the wall's south-west corner
	 * @param size The width and height of the wall, in that order
	 */
	public MapWall(
		World world,
		Vector2 position,
		Vector2 size,
		int thickness,
		ORIENTATION orientation,
		MapWallSection[] sections
	) {
		
		this.world = world;
		this.position = position;
		this.size = size;
		this.thickness = thickness;
		this.orientation = orientation;
		
		this.sections = new ArrayList<MapWallSection>();
		for (MapWallSection m : sections) this.sections.add(m);
		
		createWall();
	}
	
	public void render()
	{
		// TODO render each section's sprite
	}
	
	public void createWall()
	{
		int length = 0;
		
		if (orientation == ORIENTATION.HORIZONTAL)
		{
			for (MapWallSection m : sections)
			{
				m.setBdef(new BodyDef());
				m.getBdef().type = BodyType.DynamicBody;
				m.getBdef().position.set(new Vector2(length, position.y));
				
				m.setShape(new PolygonShape());
				m.getShape().setAsBox(m.getLength() / 2.0f, thickness);
				
				m.createBody();
			}
		}
		else
		{
			for (MapWallSection m : sections)
			{
				m.setBdef(new BodyDef());
				m.getBdef().type = BodyType.DynamicBody;
				m.getBdef().position.set(new Vector2(position.x, length));
				
				m.setShape(new PolygonShape());
				m.getShape().setAsBox(thickness, m.getLength() / 2.0f);
				
				m.createBody();
			}
		}
		
	}
	
	/** Getters and setters */

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public ArrayList<MapWallSection> getSections() {
		return sections;
	}

	public void setSections(ArrayList<MapWallSection> sections) {
		this.sections = sections;
	}

}