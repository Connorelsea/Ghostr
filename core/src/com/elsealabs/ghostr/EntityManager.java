package com.elsealabs.ghostr;

import java.util.ArrayList;

public class EntityManager {
	
	private ArrayList<Entity> entites;
	
	public EntityManager()
	{
		entites = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity e)
	{
		e.show();
		entites.add(e);
	}
	
	public Entity getEntity(String userData)
	{
		for (Entity e : entites) if (e.equals(userData)) return e;
		return null;
	}
	
	public void removeEntity(Entity e)
	{
		entites.remove(e);
	}
	
	public void removeEntity(String userData)
	{
		for (Entity e : entites) 
			if (e.equals(userData)) 
			{
				removeEntity(e);
				return;
			}
	}
	
	/** Fire all entity's show methods */
	public void show()
	{
		for (Entity e : entites) e.show();
	}
	
	/** Fire all entity's render methods, conditionally */
	public void render()
	{
		for (Entity e : entites)
		{
			if (e.isVisible() == true) e.render();
		}
	}
	
	/** Fire all entity's dispose methods */
	public void dispose()
	{
		for (Entity e : entites) e.dispose();
	}

}