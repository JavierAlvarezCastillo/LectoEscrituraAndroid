package com.lectoescritura.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.lectoescritura.game.Data.Game;
import com.lectoescritura.game.Data.Intento;
import com.lectoescritura.game.Data.Minigame;
import com.lectoescritura.game.Data.Player;
import com.lectoescritura.game.Data.Prueba;
import com.lectoescritura.game.Data.Session;
import com.lectoescritura.game.Interfaces.AndroidUtils;
import com.lectoescritura.game.Pruebas.PruebaCofre;
import com.lectoescritura.game.Pruebas.PruebaEducativa;
import com.lectoescritura.game.Register.Register;
import com.lectoescritura.game.Screens.MainMenu;
import com.lectoescritura.game.Screens.ResumeGame;
import com.lectoescritura.game.XML_Parser.XML_Parser;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class AndroidLauncher extends AndroidApplication implements AndroidUtils {

	XML_Parser xmlParser;
    Session session;
    ArrayList<Intento> intentos;

    String minigame_id, map;
    int pos_x, pos_y, ultima_x, ultima_y, energia = 10, puntos, estrellas, prueba_final = 0;
    float camposx, camposy;

    com.badlogic.gdx.Game maingame;
    AndroidUtils utils;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        utils = this;
        SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        int id = prefs.getInt("id", 0);

        readData();

        SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();
        editor.putInt("id", id);
        editor.apply();

        intentos = new ArrayList<>();
		if (xmlParser.getPlayer().getName().equals("-")) {
            Intent intent = new Intent(getContext(), Register.class);
            editor.clear().apply();
            startActivityForResult(intent, 1);
        }
        seguirJugando();
        long ultima = prefs.getLong("timestamp", (long) -1.0);

        if (ultima != (long)-1.0) {
            long dif = System.currentTimeMillis() - ultima;
            int dif_horas = (int) (dif / 3600000);
            dif_horas = dif_horas % 15;
            energia += dif_horas;
        }

        session = new Session();
        session.setDate(new SimpleDateFormat("yyyyMMddhhmmss").format(new java.util.Date()));
        session.setId(String.valueOf(id));
        session.setMingameId(minigame_id);

		//AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        maingame = new MainGame(this, map, pos_x, pos_y, minigame_id, puntos, energia, estrellas);
		initialize(maingame); //new MainGame(this, map, pos_x, pos_y, minigame_id));
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
	public void pruebaEducativa(String minigame_id, String map, int pos_x, int pos_y, float camposx, float camposy, int ultimax, int ultimay, int puntos, int energia, int estrellas) {
		Prueba prueba = null;
		final String tipo, correcto;
		final ArrayList<String> valores;
        this.camposx = camposx;
        this.camposy = camposy;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ultima_x = ultimax;
        this.ultima_y = ultimay;
        this.puntos = puntos;
        this.energia = energia;
        this.estrellas = estrellas;
        this.minigame_id = minigame_id;
        this.map = map;

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

				intent.putExtras(bundle);
				startActivityForResult(intent, 2);
			}
		});
	}

    @Override
    public void pruebaFinal(String minigame_id, String map, int pos_x, int pos_y, float camposx, float camposy, int ultimax, int ultimay, int puntos, int energia, int estrellas) {
        Prueba prueba = null;
        final String tipo, correcto;
        final ArrayList<String> valores;
        this.minigame_id = minigame_id;
        this.map = map;
        this.camposx = camposx;
        this.camposy = camposy;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ultima_x = ultimax;
        this.ultima_y = ultimay;
        this.puntos = puntos;
        this.energia = energia;
        this.estrellas = estrellas;

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
                Intent intent = new Intent(getContext(), PruebaCofre.class);
                Bundle bundle = new Bundle();
                bundle.putString("tipo", tipo);
                bundle.putStringArrayList("valores", valores);
                bundle.putString("correcto", correcto);

                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    public void pruebaAleatoria(String minigame_id, String map, int pos_x, int pos_y, float camposx, float camposy, int ultimax, int ultimay, int puntos, int energia, int estrellas) {
        this.camposx = camposx;
        this.camposy = camposy;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ultima_x = ultimax;
        this.ultima_y = ultimay;
        this.puntos = puntos;
        this.energia = energia;
        this.estrellas = estrellas;
        this.minigame_id = minigame_id;
        this.map = map;

        handler.post(new Runnable() {
            @Override
            public void run() {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Prueba prueba = null;
                                final String tipo, correcto;
                                final ArrayList<String> valores;

                                while (prueba==null) {
                                    Random r = new Random();
                                    int aleatorio_minigame = r.nextInt(xmlParser.getMinigames().size());

                                    for (int i = 0; i < xmlParser.getMinigames().size();i++) {
                                        if (i == aleatorio_minigame) {
                                            int aleatorio_prueba = r.nextInt(xmlParser.getMinigames().get(i).getPruebas().size());
                                            for (int j = 0; j < xmlParser.getMinigames().get(i).getPruebas().size(); j++) {
                                                if (j == aleatorio_prueba && !xmlParser.getMinigames().get(i).getPruebas().get(j).getTipo().equals("3")) {
                                                    prueba = xmlParser.getMinigames().get(i).getPruebas().get(j);
                                                }
                                            }
                                        }
                                    }
                                }


                                tipo = prueba.getTipo();
                                correcto = prueba.getCorrecto();
                                valores = prueba.getDistractores();


                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getContext(), PruebaEducativa.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("tipo", tipo);
                                        bundle.putStringArrayList("valores", valores);
                                        bundle.putString("correcto", correcto);

                                        intent.putExtras(bundle);
                                        startActivityForResult(intent, 2);
                                    }
                                });
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Si quieres conseguir más resuelve este reto").setPositiveButton("Sí", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    @Override
    public void salir(final int pos_x,final int pos_y, float camposx, float camposy, int ultimax, int ultimay, final int puntos, final int energia, final int estrellas) {
        this.camposx = camposx;
        this.camposy = camposy;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ultima_x = ultimax;
        this.ultima_y = ultimay;
        this.puntos = puntos;
        this.energia = energia;
        this.estrellas = estrellas;

        handler.post(new Runnable() {
            @Override
            public void run() {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                update_all();
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                                pref.edit().putLong("timestamp", System.currentTimeMillis()).apply();

                                Gdx.app.exit();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("¿Deseas salir?").setPositiveButton("Sí", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    void readData() {
		xmlParser = new XML_Parser(getBaseContext().getAssets(), getApplicationContext());
	}

    void seguirJugando() {
		Player player = xmlParser.getPlayer();
        boolean encontrado = false;
		for (String mingam_id: player.getGameIDs()) {
			for (Game game: xmlParser.getGames()) {
				if (mingam_id.equals(game.getId())) {
					if (game.getEstado().equals("1") && !encontrado) {
						minigame_id = mingam_id;
						pos_x = game.getPos_x();
						pos_y = game.getPos_y();
                        puntos = Integer.parseInt(game.getMax_score());
                        estrellas = Integer.parseInt(game.getStars());
                        energia = player.getEnergia();
                        encontrado = true;
					}
				}
			}
		}

        for (Minigame ming: xmlParser.getMinigames()) {
            if (ming.getId().equals(minigame_id))
                map = ming.getMap();
        }
	}

    void update_all() {
        xmlParser.getPlayer().setEnergia(energia);
        int total_estrellas = Integer.parseInt(xmlParser.getPlayer().getEstrellas());
        total_estrellas += estrellas;

        xmlParser.getPlayer().setEstrellas(String.valueOf(total_estrellas));

        int total_puntos = Integer.parseInt(xmlParser.getPlayer().getPuntuacion());
        total_puntos += puntos;
        xmlParser.getPlayer().setPuntuacion(String.valueOf(total_puntos));

        xmlParser.update_player();

        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setPos_x(pos_x);
        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setPos_y(pos_y);
        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setStars(String.valueOf(estrellas));
        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setMax_score(String.valueOf(puntos));
        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setEstado("1");
        xmlParser.update_games();

        session.setIntentos(intentos);
        session.setMingameId(minigame_id);
        xmlParser.setSession(session);
        xmlParser.print_session();

    }

    void update_all_end_level() {
        xmlParser.getPlayer().setEnergia(energia);
        int total_estrellas = Integer.parseInt(xmlParser.getPlayer().getEstrellas());
        total_estrellas += estrellas;

        xmlParser.getPlayer().setEstrellas(String.valueOf(total_estrellas));

        int total_puntos = Integer.parseInt(xmlParser.getPlayer().getPuntuacion());
        total_puntos += puntos;
        xmlParser.getPlayer().setPuntuacion(String.valueOf(total_puntos));

        xmlParser.update_player();

        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setPos_x(pos_x);
        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setPos_y(pos_y);
        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setStars(String.valueOf(estrellas));
        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setMax_score(String.valueOf(puntos));
        xmlParser.getGames().get(Integer.parseInt(minigame_id)-1).setEstado("2");
        xmlParser.update_games();

        session.setIntentos(intentos);
        session.setMingameId(minigame_id);
        xmlParser.setSession(session);
        xmlParser.print_session();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            //super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1  && resultCode  == RESULT_OK) {
                if (data.getStringExtra("avatar").equals("2131099663"))
                    xmlParser.getPlayer().setAvatar("2");
                else
                    xmlParser.getPlayer().setAvatar("1");
                xmlParser.getPlayer().setName(data.getStringExtra("nombre"));
                xmlParser.update_player();
            }else if (requestCode == 2  && resultCode  == RESULT_OK) {
                Intento intento =  new Intento();
                intento.setIntentos(data.getStringArrayListExtra("intentos"));
                intento.setPrueba_id(String.valueOf(intentos.size()));
                intento.setTiempo_primera_respuesta(data.getStringExtra("tiempo"));
                intento.setRespuesta_correcta(data.getStringExtra("respuesta_correcta"));
                if (data.getStringExtra("respuesta_correcta").equals("No")) {
                    pos_x = ultima_x;
                    pos_y = ultima_y;
                    if (puntos-20 < 0)
                        puntos = 0;
                    else
                        puntos-=20;
                }else {
                    energia+=3;
                    puntos+=50;
                }

                intentos.add(intento);

                initialize(new ResumeGame(this, map, pos_x, pos_y, minigame_id, camposx, camposy, puntos, energia, estrellas));
            }
            else if (requestCode == 3  && resultCode  == RESULT_OK) {
                Intento intento =  new Intento();
                intento.setIntentos(data.getStringArrayListExtra("intentos"));
                intento.setPrueba_id(String.valueOf(intentos.size()));
                intento.setTiempo_primera_respuesta(data.getStringExtra("tiempo"));
                intento.setRespuesta_correcta(data.getStringExtra("respuesta_correcta"));
                intentos.add(intento);
                if (data.getStringExtra("respuesta_correcta").equals("No")) {
                    pos_x = ultima_x;
                    pos_y = ultima_y;
                    if (puntos-20 < 0)
                        puntos = 0;
                    else
                        puntos-=20;
                    prueba_final = 0;
                    initialize(new ResumeGame(this, map, pos_x, pos_y, minigame_id, camposx, camposy, puntos, energia, estrellas));
                }else {
                    energia+=3;
                    puntos+=50;

                    toast("¡Has superado este nivel!");

                    intentos = new ArrayList<>();
                    boolean encontrado = false;
                    prueba_final = 0;
                    update_all_end_level();

                    for (String mingam_id: xmlParser.getPlayer().getGameIDs()) {
                        for (Game game: xmlParser.getGames()) {
                            if (mingam_id.equals(game.getId())) {
                                if (game.getEstado().equals("1") && !encontrado) {
                                    minigame_id = mingam_id;
                                    pos_x = game.getPos_x();
                                    pos_y = game.getPos_y();
                                    puntos = Integer.parseInt(game.getMax_score());
                                    estrellas = Integer.parseInt(game.getStars());
                                    energia = xmlParser.getPlayer().getEnergia() + 10;
                                    encontrado = true;
                                }
                            }
                        }
                    }

                    for (Minigame ming: xmlParser.getMinigames()) {
                        if (ming.getId().equals(minigame_id))
                            map = ming.getMap();
                    }

                    SharedPreferences prefs = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
                    int id = prefs.getInt("id", 0);

                    session = new Session();
                    session.setDate(new SimpleDateFormat("yyyyMMddhhmmss").format(new java.util.Date()));
                    session.setId(String.valueOf(id));
                    session.setMingameId(minigame_id);
                    id++;

                    SharedPreferences.Editor editor = getSharedPreferences("SharedPreferences", MODE_PRIVATE).edit();
                    editor.putInt("id", id);
                    editor.apply();

                    //AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

                    if (encontrado) {
                        initialize(new ResumeGame(this, map, pos_x, pos_y, minigame_id, 0.0f, 0.0f, puntos, energia, estrellas));
                    }
                    else {
                        toast("Has superado el juego!");
                        initialize(new MainGame(this, map, pos_x, pos_y, minigame_id, puntos, energia, estrellas));
                    }
                }




            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
