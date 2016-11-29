package com.lectoescritura.game.XML_Parser;

/**
 * Created by Javier on 22/09/2016.
 */

import android.content.res.AssetManager;

import com.lectoescritura.game.Const.Const;
import com.lectoescritura.game.Data.Game;
import com.lectoescritura.game.Data.Minigame;
import com.lectoescritura.game.Data.Player;
import com.lectoescritura.game.Data.Prueba;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

public class XML_Parser {

    private ArrayList<Minigame> minigames;
    private ArrayList<Game> games;
    private ArrayList<Player> players;


    public XML_Parser(AssetManager assetManager) {
        parse(assetManager);
    }

    void parse(AssetManager assetManager) {

        // parse minigames
        minigames = new ArrayList<>();
        Minigame minigame = new Minigame();
        ArrayList<Prueba> pruebas = new ArrayList<>();
        Prueba prueba = new Prueba();
        ArrayList<String> distractores = new ArrayList<>();
        String etiqueta = null;

        // parse game
        games = new ArrayList<>();
        Game game = new Game();

        // parse player
        players = new ArrayList<>();
        Player player = new Player();
        ArrayList<String> player_game_ids = new ArrayList<>();

        // parse session

        final String xmlFile = "data.xml";
        InputStreamReader isr;
        InputStream is;
        char[] inputBuffer;
        String data = null;

        try {
            is = assetManager.open(xmlFile);
            isr = new InputStreamReader(is);
            inputBuffer = new char[is.available()];
            isr.read(inputBuffer);
            data = new String(inputBuffer);
            isr.close();
            is.close();
        }
        catch (FileNotFoundException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
        }
        catch (XmlPullParserException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        factory.setNamespaceAware(true);
        XmlPullParser xpp = null;
        try {
            xpp = factory.newPullParser();
        }
        catch (XmlPullParserException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        try {
            xpp.setInput(new StringReader(data));
        }
        catch (XmlPullParserException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        int eventType = 0;
        try {
            eventType = xpp.getEventType();
        }
        catch (XmlPullParserException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        while (eventType != XmlPullParser.END_DOCUMENT){
            if (eventType == XmlPullParser.START_DOCUMENT) {

            }
            else if (eventType == XmlPullParser.START_TAG) {
                switch (xpp.getName()) {
                    // minigames
                    case Const.MINIGAME: minigame = new Minigame();
                        break;
                    case Const.MINIGAME_ID: etiqueta = Const.MINIGAME_ID;
                        break;
                    case Const.MINIGAME_TYPE: etiqueta = Const.MINIGAME_TYPE;
                        break;
                    case Const.MINIGAME_MAP: etiqueta = Const.MINIGAME_MAP;
                        break;
                    case Const.MINIGAME_LEVEL: etiqueta = Const.MINIGAME_LEVEL;
                        break;
                    case Const.PRUEBAS: pruebas = new ArrayList<>();
                        break;
                    case Const.PRUEBA: prueba = new Prueba();
                        break;
                    case Const.PRUEBA_ID: etiqueta = Const.PRUEBA_ID;
                        break;
                    case Const.PRUEBA_CORRECTO: etiqueta = Const.PRUEBA_CORRECTO;
                        break;
                    case Const.PRUEBA_DISTRACTORES: distractores = new ArrayList<>();
                        break;
                    case Const.PRUEBA_DISTRACTOR: etiqueta = Const.PRUEBA_DISTRACTOR;
                        break;
                    case Const.PRUEBA_TIPO: etiqueta = Const.PRUEBA_TIPO;
                        break;
                    case Const.PRUEBA_POSICION_X: etiqueta = Const.PRUEBA_POSICION_X;
                        break;
                    case Const.PRUEBA_POSICION_Y: etiqueta = Const.PRUEBA_POSICION_Y;
                        break;
                    // games
                    case Const.GAME: game = new Game();
                        break;
                    case Const.GAME_ID: etiqueta = Const.GAME_ID;
                        break;
                    case Const.GAME_MINIGAME_ID: etiqueta = Const.GAME_MINIGAME_ID;
                        break;
                    case Const.GAME_MAX_SCORE: etiqueta = Const.GAME_MAX_SCORE;
                        break;
                    case Const.GAME_STARS: etiqueta = Const.GAME_STARS;
                        break;
                    case Const.GAME_ACTUAL_POSITION_X: etiqueta = Const.GAME_ACTUAL_POSITION_X;
                        break;
                    case Const.GAME_ACTUAL_POSITION_Y: etiqueta = Const.GAME_ACTUAL_POSITION_Y;
                        break;
                    case Const.GAME_ESTADO: etiqueta = Const.GAME_ESTADO;
                        break;
                    // player
                    case Const.PLAYER: player = new Player();
                        break;
                    case Const.PLAYER_ID: etiqueta = Const.PLAYER_ID;
                        break;
                    case Const.PLAYER_NAME: etiqueta = Const.PLAYER_NAME;
                        break;
                    case Const.PLAYER_GAME_IDS: player_game_ids = new ArrayList<>();
                        break;
                    case Const.PLAYER_GAME_ID: etiqueta = Const.PLAYER_GAME_ID;
                        break;
                    case Const.PLAYER_PUNTUACION: etiqueta = Const.PLAYER_PUNTUACION;
                        break;
                    case Const.PLAYER_ESTRELLAS: etiqueta = Const.PLAYER_ESTRELLAS;
                        break;
                    case Const.PLAYER_AVATAR: etiqueta = Const.PLAYER_AVATAR;
                        break;
                }
            }

            else if (eventType == XmlPullParser.END_TAG) {
                switch (xpp.getName()) {
                    // minigames
                    case Const.MINIGAME: minigames.add(minigame);
                        break;
                    // pruebas
                    case Const.PRUEBA: pruebas.add(prueba);
                        break;
                    case Const.PRUEBAS: minigame.setPruebas(pruebas);
                        break;
                    case Const.PRUEBA_DISTRACTORES: prueba.setDistractores(distractores);
                        break;
                    // game
                    case Const.GAME: games.add(game);
                        break;
                    // player
                    case Const.PLAYER: players.add(player);
                        break;
                    case Const.PLAYER_GAME_IDS: player.setGameIDs(player_game_ids);
                        break;
                }
            }
            else if(eventType == XmlPullParser.TEXT) {
                if (etiqueta != null) {
                    switch (etiqueta) {
                        // minigames
                        case Const.MINIGAME_ID: minigame.setId(xpp.getText());
                            break;
                        case Const.MINIGAME_TYPE: minigame.setType(xpp.getText());
                            break;
                        case Const.MINIGAME_MAP: minigame.setMap(xpp.getText());
                            break;
                        case Const.MINIGAME_LEVEL: minigame.setLevel(xpp.getText());
                            break;
                        // pruebas
                        case Const.PRUEBA_ID: prueba.setId(xpp.getText());
                            break;
                        case Const.PRUEBA_CORRECTO: prueba.setCorrecto(xpp.getText());
                            break;
                        case Const.PRUEBA_DISTRACTOR: distractores.add(xpp.getText());
                            break;
                        case Const.PRUEBA_TIPO: prueba.setTipo(xpp.getText());
                            break;
                        case Const.PRUEBA_POSICION_X: prueba.setPos_x(Integer.parseInt(xpp.getText()));
                            break;
                        case Const.PRUEBA_POSICION_Y: prueba.setPos_y(Integer.parseInt(xpp.getText()));
                            break;
                        // game
                        case Const.GAME_ID: game.setId(xpp.getText());
                            break;
                        case Const.GAME_MINIGAME_ID: game.setMinigame_id(xpp.getText());
                            break;
                        case Const.GAME_MAX_SCORE: game.setMax_score(xpp.getText());
                            break;
                        case Const.GAME_STARS: game.setStars(xpp.getText());
                            break;
                        case Const.GAME_ACTUAL_POSITION_X: game.setPos_x(Integer.parseInt(xpp.getText()));
                            break;
                        case Const.GAME_ACTUAL_POSITION_Y: game.setPos_y(Integer.parseInt(xpp.getText()));
                            break;
                        case Const.GAME_ESTADO: game.setEstado(xpp.getText());
                            break;
                        // player
                        case Const.PLAYER_ID: player.setId(xpp.getText());
                            break;
                        case Const.PLAYER_NAME: player.setName(xpp.getText());
                            break;
                        case Const.PLAYER_GAME_ID: player_game_ids.add(xpp.getText());
                            break;
                        case Const.PLAYER_PUNTUACION: player.setPuntuacion(xpp.getText());
                            break;
                        case Const.PLAYER_ESTRELLAS: player.setEstrellas(xpp.getText());
                            break;
                        case Const.PLAYER_AVATAR: player.setAvatar(xpp.getText());
                            break;
                    }
                    etiqueta = null;
                }
            }
            try {
                eventType = xpp.next();
            }
            catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        etiqueta = "d";
    }

    public ArrayList<Minigame> getMinigames() {
        return minigames;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
