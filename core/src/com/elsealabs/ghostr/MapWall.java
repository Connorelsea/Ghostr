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
	private float thickness;
	
	private Map map;
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
		Map map,
		World world,
		Vector2 position,
		float thickness,
		ORIENTATION orientation,
		MapWallSection[] sections
	) {
		this.map = map;
		this.world = world;
		this.position = position;
		this.thickness = thickness;
		this.orientation = orientation;
		
		this.sections = new ArrayList<MapWallSection>();
		
		for (MapWallSection m : sections) {
			m.setWall(this);
			this.sections.add(m);
		}
		
		createWall();
	}
	
	public void render()
	{
		map.getBatch().setProjectionMatrix(map.getCamera().combined);
		map.getBatch().begin();
		for (MapWallSection s : sections) s.render(map.getBatch());
		map.getBatch().end();
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
				m.getBdef().type = BodyType.StaticBody;
				m.getBdef().position.set(position.x + (m.getLength() / 2) + length, position.y + (thickness / 2));
				length += m.getLength();
				 
				m.setShape(new PolygonShape());
				m.getShape().setAsBox(m.getLength() / 2, thickness / 2);
				
				m.setBody(getWorld().createBody(m.getBdef()));
				
				m.getFdef().shape = m.getShape();
				m.getBody().createFixture(m.getFdef());
				
				m.setPixelWidth(m.getLength() * 32f);
				m.setPixelLength(thickness * 32f);
				
				/** Set sprite attributes */
				
				if (m.hasSprite())
				{
					m.getSprite().setPosition(m.getBdef().position.x, m.getBdef().position.y);
					
					switch (m.getRenderMode()) {
					
						default:
						case EXACT:
							m.getSprite().setSize(m.getSprite().getWidth() / 32f, m.getSprite().getHeight() / 32f);
							break;
							
						case SCALE:
							m.getSprite().setSize(m.getLength(), thickness);
							break;
							
						case STRETCH_HORIZONTAL:
							m.getSprite().setSize(m.getLength(), m.getSprite().getHeight() / 32f);
							break;
						
						case STRETCH_VERTICAL:
							m.getSprite().setSize(m.getSprite().getWidth() / 32f, thickness);
							break;
					}
					
					m.getSprite().setCenter(m.getBody().getPosition().x, m.getBody().getPosition().y);
				}
				
				/** Print debug information */
				
				System.out.printf("HORIZONTAL::%s\n", m.getType().toString());
				System.out.printf("\tCalculated Position: (%f, %f)\n", m.getBdef().position.x, m.getBdef().position.y);
				System.out.printf("\tShape: (width:%f, length:%f)\n", m.getLength(), thickness);
				System.out.printf("\tPixel Width: %f\n", m.getPixelWidth());
				System.out.printf("\tPixel Length: %f\n", m.getPixelLength());
				System.out.printf("\tHas Sprite: %b\n", m.hasSprite());
			}
		}
		else
		{
			float length = 0f;
			
			for (MapWallSection m : sections)
			{
				/** Create body and body related objects */
				m.setBdef(new BodyDef());
				m.getBdef().type = BodyType.StaticBody;
				m.getBdef().position.set(new Vector2(position.x + (thickness / 2), position.y + (m.getLength() / 2) + length));
				length += m.getLength();
				 
				m.setShape(new PolygonShape());
				m.getShape().setAsBox(thickness / 2, m.getLength() / 2);
				
				m.setBody(getWorld().createBody(m.getBdef()));
				
				m.getFdef().shape = m.getShape();
				m.getBody().createFixture(m.getFdef());
				
				m.setPixelWidth(thickness * 32f);
				m.setPixelLength(m.getLength() * 32f);
				
				System.out.printf("VERTICAL::%s\n", m.getType().toString());
				System.out.printf("\tCalculated Position: (%f, %f)\n", m.getBdef().position.x, m.getBdef().position.y);
				System.out.printf("\tShape: (width:%f, length:%f)\n", m.getLength(), thickness);
				System.out.printf("\tPixel Width: %f\n", m.getPixelWidth());
				System.out.printf("\tPixel Length: %f\n", m.getPixelLength());
				System.out.printf("\tHas Sprite: %b\n", m.hasSprite());
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