package com.elsealabs.ghostr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Program extends GameObject {
	
	public ScreenLogo SCREEN_LOGO;
	public ScreenMenu SCREEN_MENU;
	public ScreenGame2 SCREEN_GAME2;
	
	@Override
	public void create() {
		
		Music music = Gdx.audio.newMusic(Gdx.files.internal("des.mp3"));
		music.play();
		
		getScreenOrder().add("SCREEN_LOGO");
		//getScreenOrder().add("SCREEN_MENU");
		getScreenOrder().add("SCREEN_GAME2");

		SCREEN_LOGO = new ScreenLogo(this, "SCREEN_LOGO");
		SCREEN_MENU = new ScreenMenu(this, "SCREEN_MENU");
		SCREEN_GAME2 = new ScreenGame2(this, "SCREEN_GAME2");
		
		this.addScreen(SCREEN_LOGO);
		this.addScreen(SCREEN_MENU);
		this.addScreen(SCREEN_GAME2);
		
		this.nextScreen();
		
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