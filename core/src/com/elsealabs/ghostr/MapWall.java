package com.elsealabs.ghostr;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
	private float thickness;
	
	private World world;
	
	public enum ORIENTATION { VERTICAL, HORIZONTAL }
	private ORIENTATION orientation;
	
	/** Wall section information */
	private ArrayList<MapWallSection> sections;
	
	/**
	 * Constructor taking in all necessary values for the
	 * creation of a wall.
	 * 
	 * @param world The Box2D world
	 * @param position the x, y position for the wall to start at
	 * @param thickness How thick the wall should be
	 */
	public MapWall(
		World world,
		Vector2 position,
		float thickness,
		ORIENTATION orientation,
		MapWallSection[] sections
	) {
		
		this.world = world;
		this.position = position;
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
		
		if (orientation == ORIENTATION.HORIZONTAL)
		{
			
			float length = 0f;
			
			for (MapWallSection m : sections)
			{	
				/** Create body and body related objects */
				m.setBdef(new BodyDef());
				m.getBdef().type = BodyType.DynamicBody;
				m.getBdef().position.set(position.x + m.getLength() + length, position.y + (thickness / 2));
				length += m.getLength();
				 
				m.setShape(new PolygonShape());
				m.getShape().setAsBox(m.getLength(), thickness / 2);
				length += m.getLength();
				
				m.setBody(getWorld().createBody(m.getBdef()));
				
				m.getFdef().shape = m.getShape();
				m.getBody().createFixture(m.getFdef());
			}
		}
		else
		{
			float length = 0f;
			
			for (MapWallSection m : sections)
			{
				/** Create body and body related objects */
				m.setBdef(new BodyDef());
				m.getBdef().type = BodyType.DynamicBody;
				m.getBdef().position.set(new Vector2(position.x + (thickness / 2), position.y + m.getLength() + length));
				length += m.getLength();
				 
				m.setShape(new PolygonShape());
				m.getShape().setAsBox(thickness / 2, m.getLength());
				length += m.getLength();
				
				m.setBody(getWorld().createBody(m.getBdef()));
				
				m.getFdef().shape = m.getShape();
				m.getBody().createFixture(m.getFdef());
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
	
	public float getThickness() {
		return thickness;
	}

	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	public ORIENTATION getOrientation() {
		return orientation;
	}

	public void setOrientation(ORIENTATION orientation) {
		this.orientation = orientation;
	}

}