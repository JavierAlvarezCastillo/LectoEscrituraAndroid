package com.lectoescritura.game.Data;

import java.util.ArrayList;

/**
 * Created by Javier on 28/11/2016.
 */
public class Player {
    private String id, name, puntuacion, estrellas, avatar;
    private int energia;
    private ArrayList<String> gameIDs;

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(String estrellas) {
        this.estrellas = estrellas;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getGameIDs() {
        return gameIDs;
    }

    public void setGameIDs(ArrayList<String> gameIDs) {
        this.gameIDs = gameIDs;
    }
}
