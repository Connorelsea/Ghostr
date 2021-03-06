package com.elsealabs.ghostr;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public abstract class GameObject implements ApplicationListener {
	
	private ScreenObject SCREEN;
	private ArrayList<ScreenObject> SCREENS;
	private ArrayList<String> screenOrder;
	private int position = 0;
	
	public GameObject() {
		SCREENS = new ArrayList<ScreenObject>();
		screenOrder = new ArrayList<String>();
	}
	
	public void dispose () {
		if (SCREEN != null) SCREEN.hide();
	}

	public void pause () {
		if (SCREEN != null) SCREEN.pause();
	}

	public void resume () {
		if (SCREEN != null) SCREEN.resume();
	}

	public void render () {
		if (SCREEN != null) SCREEN.render(Gdx.graphics.getDeltaTime());
	}

	public void resize (int width, int height) {
		if (SCREEN != null) SCREEN.resize(width, height);
	}
	
	public void setScreen(String name) {
		
		for (ScreenObject so : SCREENS) {
			
			if (so.getName().equals(name)) {
				if (this.SCREEN != null) this.SCREEN.hide();
				System.out.println(name);
				SCREEN = so;
				so.show();
			}
		}
	}
	
	public void nextScreen()
	{
		setScreen(screenOrder.get(position));
		position++;
	}
	
	public void addScreen(ScreenObject so) {
		SCREENS.add(so);
	}
	
	public void removeScreen(String name) {
		for (ScreenObject so : SCREENS) if (so.getName().equals(name)) SCREENS.remove(so);
	}

	public Screen getScreen () {
		return SCREEN;
	}

	public ArrayList<String> getScreenOrder() {
		return screenOrder;
	}

	public void setScreenOrder(ArrayList<String> screenOrder) {
		this.screenOrder = screenOrder;
	}

}