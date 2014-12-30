package com.elsealabs.ghostr;

import java.util.ArrayList;

public class ScreenManager
{
	private ArrayList<ScreenObject> screens;
	
	public void addScreen(ScreenObject o)
	{
		screens.add(o);
	}
	
	public void removeScreen(ScreenObject o)
	{
		screens.remove(o);
	}
	
	public void removeScreen(String name)
	{
		for (ScreenObject o : screens)
		{
			if (o.getName().equals(name)) removeScreen(o);
			return;
		}
	}

}