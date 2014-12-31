package com.elsealabs.ghostr;

import com.badlogic.gdx.Screen;

public abstract class ScreenObject implements Screen
{
	private GameObject game;
	private String name;
	
	public ScreenObject(GameObject game, String name) {
		this.game = game;
		this.name = name;
	}
	
	public void done()
	{
		game.nextScreen();
	}
	
	public GameObject getGame() {
		return this.game;
	}
	
	public void setScreen(ScreenObject screen) {
		this.game.addScreen(screen);
		this.game.setScreen(screen.getName());
	}
	
	public String getName() {
		return this.name;
	}
	
}