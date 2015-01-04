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
	 * @param position The position of the wall's south-west corner
	 * @param size The width and height of the wall, in that order
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
		int length = 0;
		
		if (orientation == ORIENTATION.HORIZONTAL)
		{
			for (MapWallSection m : sections)
			{	
				System.out.printf("MapWallSection::HORIZONTAL::%s\n\tLength: %f\n",  m.getType().toString(), m.getLength());
				
				/** Create body and body related objects */
				m.setBdef(new BodyDef());
				m.getBdef().type = BodyType.DynamicBody;
				m.getBdef().position.set(new Vector2(position.x, length + position.y));
				
				m.setShape(new PolygonShape());
				m.getShape().setAsBox(thickness, m.getLength() / 2.0f);
				length += m.getLength();
				
				m.setBody(getWorld().createBody(m.getBdef()));
				
				m.setFdef(new FixtureDef());
				m.getFdef().shape = m.getShape();
				
				m.getBody().createFixture(m.getFdef());
				
				/** Set category and mask bits for collision filtering */
				
				if (m.getType() == MapWallSection.TYPE.DOOR)
				{
					m.getFdef().filter.categoryBits = Map.BIT_DOOR;
					m.getFdef().filter.maskBits     = Map.BIT_LIGHT & Map.BIT_ENTITY;
				}
				else if (m.getType() == MapWallSection.TYPE.WINDOW)
				{
					m.getFdef().filter.categoryBits = Map.BIT_DOOR;
					m.getFdef().filter.maskBits     = Map.BIT_ENTITY;
				}
				else if (m.getType() == MapWallSection.TYPE.WALL)
				{
					m.getFdef().filter.categoryBits = Map.BIT_WALL;
					m.getFdef().filter.maskBits     = Map.BIT_LIGHT & Map.BIT_ENTITY;
				}
				
			}
		}
		else
		{
			for (MapWallSection m : sections)
			{
				
				System.out.printf("MapWallSection::VERTICAL::%s\n\tLength: %f\n",  m.getType().toString(), m.getLength());
				
				/** Create body and body related objects */
				m.setBdef(new BodyDef());
				m.getBdef().type = BodyType.DynamicBody;
				m.getBdef().position.set(new Vector2(length + position.x, position.y));
				
				m.setShape(new PolygonShape());
				//System.out.printf("setAsBox(%f, %f)", thickness, m.getLength() / 2.0f);
				m.getShape().setAsBox(m.getLength() / 2.0f, thickness);
				length += m.getLength();
				
				m.setBody(getWorld().createBody(m.getBdef()));
				
				m.setFdef(new FixtureDef());
				m.getFdef().shape = m.getShape();
				
				m.getBody().createFixture(m.getFdef());
				
				/** Set category and mask bits for collision filtering */
				
				if (m.getType() == MapWallSection.TYPE.DOOR)
				{
					m.getFdef().filter.categoryBits = Map.BIT_DOOR;
					m.getFdef().filter.maskBits     = Map.BIT_ENTITY;
				}
				else if (m.getType() == MapWallSection.TYPE.WINDOW)
				{
					m.getFdef().filter.categoryBits = Map.BIT_WINDOW;
					m.getFdef().filter.maskBits     = Map.BIT_ENTITY;
				}
				else if (m.getType() == MapWallSection.TYPE.WALL)
				{
					m.getFdef().filter.categoryBits = Map.BIT_WALL;
					m.getFdef().filter.maskBits     = Map.BIT_ENTITY | Map.BIT_LIGHT;
				}
				
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