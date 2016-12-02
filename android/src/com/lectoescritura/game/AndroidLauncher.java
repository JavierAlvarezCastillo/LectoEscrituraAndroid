package com.lectoescritura.game;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.lectoescritura.game.Data.Game;
import com.lectoescritura.game.Data.Minigame;
import com.lectoescritura.game.Data.Player;
import com.lectoescritura.game.Data.Prueba;
import com.lectoescritura.game.Interfaces.AndroidUtils;
import com.lectoescritura.game.Interfaces.Conector;
import com.lectoescritura.game.Pruebas.PruebaEducativa;
import com.lectoescritura.game.Screens.MainMenu;
import com.lectoescritura.game.Screens.Play;
import com.lectoescritura.game.XML_Parser.XML_Parser;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication implements AndroidUtils {

	XML_Parser xmlParser;
    String minigame_id = "1", map = "1.tmx";
    int pos_x = 0, pos_y = 0;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		readData();
        seguirJugando();
		//AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGame(this, map, pos_x, pos_y, minigame_id));
	}

	@Override
	public void toast(final String text) {
		handler.post(new Runnable()
		{
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void pruebaEducativa(String minigame_id, int pos_x, int pos_y) {
		Prueba prueba = null;
		final String tipo, correcto;
		final ArrayList<String> valores;

		for (Minigame minigame: xmlParser.getMinigames()) {
			if (minigame.getId().equals(minigame_id)) {
				for (Prueba p: minigame.getPruebas()) {
					if (p.getPos_x() == pos_x && p.getPos_y() == pos_y) {
						prueba = p;
					}
				}
			}
		}

		if (prueba != null) {
			tipo = prueba.getTipo();
			correcto = prueba.getCorrecto();
			valores = prueba.getDistractores();
		}else {
			tipo = null;
			correcto = null;
			valores = null;
		}

		handler.post(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(getContext(), PruebaEducativa.class);
				Bundle bundle = new Bundle();
				bundle.putString("tipo", tipo);
				bundle.putStringArrayList("valores", valores);
				bundle.putString("correcto", correcto);
                //intent.putExtra("interface", conector);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	void readData() {
		xmlParser = new XML_Parser(getBaseContext().getAssets(), getApplicationContext());
	}

    void seguirJugando() {
		Player player = xmlParser.getPlayer();
		for (String mingam_id: player.getGameIDs()) {
			for (Game game: xmlParser.getGames()) {
				if (mingam_id.equals(game.getId())) {
					if (game.getEstado().equals("1")) {
						minigame_id = mingam_id;
						pos_x = game.getPos_x();
						pos_y = game.getPos_y();
					}
				}
			}
		}

        for (Minigame ming: xmlParser.getMinigames()) {
            if (ming.getId().equals(minigame_id))
                map = ming.getMap();
        }
	}
}
