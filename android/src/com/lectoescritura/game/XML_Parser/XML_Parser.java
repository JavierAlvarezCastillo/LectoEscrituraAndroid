package com.lectoescritura.game.XML_Parser;

/**
 * Created by Javier on 22/09/2016.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Xml;

import com.lectoescritura.game.Const.Const;
import com.lectoescritura.game.Data.Game;
import com.lectoescritura.game.Data.Minigame;
import com.lectoescritura.game.Data.Player;
import com.lectoescritura.game.Data.Prueba;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XML_Parser {

    private ArrayList<Minigame> minigames;
    private ArrayList<Game> games;
    private Player player;
    private Context context;
    private AssetManager assetManager;

    public XML_Parser(AssetManager assetManager, Context context) {
        this.assetManager = assetManager;
        this.context = context;

        parse_minigames();

        if (createDirIfNotExists("Lecto")) {
            copyXML("Player.xml");
            copyXML("Games.xml");
        }
        parse_games();
        parse_player();
//        player.setAvatar("1");
//        player.setName("Javilon");
//        update_player(p);
//        games.get(0).setPos_x(5);
//        update_games(games);
    }

    public void update_player() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc = null;
        try {
            builder = factory.newDocumentBuilder();
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Lecto/Data/Player.xml");
            doc = builder.parse(file);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        NodeList nodeslist;
        if (doc != null) {
            nodeslist = doc.getElementsByTagName("player_name");
            nodeslist.item(0).getFirstChild().setNodeValue(player.getName());

            nodeslist = doc.getElementsByTagName("player_puntuacion");
            nodeslist.item(0).getFirstChild().setNodeValue(player.getPuntuacion());

            nodeslist = doc.getElementsByTagName("player_estrellas");
            nodeslist.item(0).getFirstChild().setNodeValue(player.getEstrellas());

            nodeslist = doc.getElementsByTagName("player_avatar");
            nodeslist.item(0).getFirstChild().setNodeValue(player.getAvatar());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
            DOMSource dSource = new DOMSource(doc);

            File f2 = new File(Environment.getExternalStorageDirectory().toString() + "/Lecto/Data/Player.xml");
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(f2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            StreamResult result = new StreamResult(outputStream);
            try {
                if (transformer != null) {
                    transformer.transform(dSource, result);
                }
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }


    }

    public void update_games() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc = null;
        try {
            builder = factory.newDocumentBuilder();
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Lecto/Data/Games.xml");
            doc = builder.parse(file);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        NodeList nodeslist;
        if (doc != null) {
            for (int i = 0; i < games.size(); i++) {
                Game game = games.get(i);
                nodeslist = doc.getElementsByTagName("game_max_score");
                nodeslist.item(i).getFirstChild().setNodeValue(game.getMax_score());

                nodeslist = doc.getElementsByTagName("game_stars");
                nodeslist.item(i).getFirstChild().setNodeValue(game.getStars());

                nodeslist = doc.getElementsByTagName("game_actual_position_x");
                nodeslist.item(i).getFirstChild().setNodeValue(String.valueOf(game.getPos_x()));

                nodeslist = doc.getElementsByTagName("game_actual_position_y");
                nodeslist.item(i).getFirstChild().setNodeValue(String.valueOf(game.getPos_y()));

                nodeslist = doc.getElementsByTagName("game_estado");
                nodeslist.item(i).getFirstChild().setNodeValue(game.getEstado());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
            DOMSource dSource = new DOMSource(doc);

            File f2 = new File(Environment.getExternalStorageDirectory().toString() + "/Lecto/Data/Games.xml");
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(f2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            StreamResult result = new StreamResult(outputStream);
            try {
                if (transformer != null) {
                    transformer.transform(dSource, result);
                }
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    public void write_XML() {
        final String xmlFile = "userData";
        String userNAme = "username";
        String password = "password";
        try {
            createDirIfNotExists("Lecto/Logs");

            FileOutputStream fileos = new FileOutputStream (new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Logs/" + "userData.xml"));

            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "userData");
            xmlSerializer.startTag(null, "userName");
            xmlSerializer.text("Hola");
            xmlSerializer.endTag(null, "userName");
            xmlSerializer.startTag(null,"password");
            xmlSerializer.text("Adios");
            xmlSerializer.endTag(null, "password");
            xmlSerializer.endTag(null, "userData");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        }
        catch (IllegalArgumentException | IOException | IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                ret = false;
            }
        }
        return ret;
    }

    void copyXML(String file) {
        createDirIfNotExists("/Lecto/Data");
        String destFile = Environment.getExternalStorageDirectory().toString() + "/Lecto/Data/" + file;
        try {

            File f2 = new File(destFile);
            InputStream in = assetManager.open("data/" + file);
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
            System.out
                    .println(ex.getMessage() + " in the specified directory.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void parse_games() {

        String etiqueta = null;

        // parse game
        games = new ArrayList<>();
        Game game = new Game();

        final String xmlFile = "/Lecto/Data/Games.xml";
        InputStreamReader isr;
        InputStream is;
        char[] inputBuffer;
        String data = null;

        try {
            File file = new File(Environment.getExternalStorageDirectory().toString() + xmlFile);
            is = new FileInputStream(file);

            isr = new InputStreamReader(is);
            inputBuffer = new char[is.available()];
            isr.read(inputBuffer);
            data = new String(inputBuffer);
            isr.close();
            is.close();
        } catch (IOException e) {
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
                }
            }

            else if (eventType == XmlPullParser.END_TAG) {
                switch (xpp.getName()) {
                    // game
                    case Const.GAME: games.add(game);
                        break;
                }
            }

            else if(eventType == XmlPullParser.TEXT) {
                if (etiqueta != null) {
                    switch (etiqueta) {
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
                    }
                    etiqueta = null;
                }
            }
            try {
                eventType = xpp.next();
            }
            catch (XmlPullParserException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    void parse_minigames() {

        // parse minigames
        minigames = new ArrayList<>();
        Minigame minigame = new Minigame();
        ArrayList<Prueba> pruebas = new ArrayList<>();
        Prueba prueba = new Prueba();
        ArrayList<String> distractores = new ArrayList<>();
        String etiqueta = null;

        final String xmlFile = "data/Minigames.xml";
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
    }

    void parse_player() {

        String etiqueta = null;

        // parse player
        player = new Player();
        ArrayList<String> player_game_ids = new ArrayList<>();

        final String xmlFile = "/Lecto/Data/Player.xml";
        InputStreamReader isr;
        InputStream is;
        char[] inputBuffer;
        String data = null;

        try {
            File file = new File(Environment.getExternalStorageDirectory().toString() + xmlFile);
            is = new FileInputStream(file);
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
                    // player
                    case Const.PLAYER_GAME_IDS: player.setGameIDs(player_game_ids);
                        break;
                }
            }
            else if(eventType == XmlPullParser.TEXT) {
                if (etiqueta != null) {
                    switch (etiqueta) {
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
    }

    public ArrayList<Minigame> getMinigames() {
        return minigames;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public Player getPlayer() {
        return player;
    }

    public class Nodo {

    }
}
