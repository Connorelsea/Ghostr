package com.elsealabs.ghostr;

public class Program extends GameObject {
	
	public ScreenLogo SCREEN_LOGO;
	public ScreenMenu SCREEN_MENU;
	public ScreenGame SCREEN_GAME;
	public ScreenGame2 SCREEN_GAME2;
	
	@Override
	public void create() {
		
		SCREEN_LOGO = new ScreenLogo(this, "SCREEN_LOGO");
		SCREEN_GAME = new ScreenGame(this, "SCREEN_GAME");
		SCREEN_MENU = new ScreenMenu(this, "SCREEN_MENU");
		SCREEN_GAME2 = new ScreenGame2(this, "SCREEN_GAME2");
		
		this.addScreen(SCREEN_LOGO);
		this.addScreen(SCREEN_GAME);
		this.addScreen(SCREEN_MENU);
		this.addScreen(SCREEN_GAME2);
		
		this.setScreen("SCREEN_GAME2");
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}