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
    private int puntos, energia, estrellas;

	public MainGame(AndroidUtils androidUtils, String map, int pos_x, int pos_y, String minigame_id, int puntos, int energia, int estrellas) {
		this.map = map;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		utils = androidUtils;
		this.minigame_id = minigame_id;
        this.puntos = puntos;
        this.energia = energia;
        this.estrellas = estrellas;
	}

	@Override
	public void create() {
		setScreen(new MainMenu(utils, map, pos_x, pos_y, minigame_id, puntos, energia, estrellas));
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
