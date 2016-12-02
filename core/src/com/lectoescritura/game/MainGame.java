package com.lectoescritura.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lectoescritura.game.Interfaces.AndroidUtils;
import com.lectoescritura.game.Screens.MainMenu;
import com.lectoescritura.game.Screens.Play;

public class MainGame extends Game {

	AndroidUtils utils;
	private String map;
	private String minigame_id;
	private int pos_x;
	private int pos_y;

	public MainGame(AndroidUtils androidUtils, String map, int pos_x, int pos_y, String minigame_id) {
		this.map = map;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		utils = androidUtils;
		this.minigame_id = minigame_id;
	}

	@Override
	public void create() {
		setScreen(new MainMenu(utils, map, pos_x, pos_y, minigame_id));
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
