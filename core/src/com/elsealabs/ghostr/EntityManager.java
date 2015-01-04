package com.elsealabs.ghostr;

import java.util.ArrayList;

public class EntityManager {
	
	private ArrayList<Entity> entities;
	
	public EntityManager()
	{
		entities = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity e)
	{
		e.show();
		entities.add(e);
	}
	
	public Entity getEntity(String userData)
	{
		for (Entity e : entities) if (e.equals(userData)) return e;
		return null;
	}
	
	public void removeEntity(Entity e)
	{
		entities.remove(e);
	}
	
	public void removeEntity(String userData)
	{
		for (Entity e : entities) 
			if (e.equals(userData)) 
			{
				removeEntity(e);
				return;
			}
	}
	
	/** Fire all entity's show methods */
	public void show()
	{
		for (Entity e : entities) e.show();
	}
	
	public void update()
	{
		for (Entity e : entities)
		{
			if (e.isVisible() == true) e.update();
		}
	}
	
	/** Fire all entity's render methods, conditionally */
	public void render()
	{
		for (Entity e : entities)
		{
			if (e.isVisible() == true) e.render();
		}
	}
	
	/** Fire all entity's dispose methods */
	public void dispose()
	{
		for (Entity e : entities) e.dispose();
	}

}