package com.lectoescritura.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lectoescritura.game.Screens.Play;

public class MainGame extends Game {
	@Override
	public void create() {
		//setScreen(new Play());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
//		if(Gdx.input.isTouched())
//			try {
//				setScreen(getScreen().getClass().newInstance());
//			} catch(InstantiationException e) {
//				e.printStackTrace();
//			} catch(IllegalAccessException e) {
//				e.printStackTrace();
//			}
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