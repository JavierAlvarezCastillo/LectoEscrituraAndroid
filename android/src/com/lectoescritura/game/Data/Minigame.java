package com.lectoescritura.game.Data;

import java.util.ArrayList;

/**
 * Created by Javier on 27/11/2016.
 */
public class Minigame {
    private String id, type, level, map;
    private ArrayList<Prueba> pruebas;

    // Getters

    public String getId() {
        return id;
    }

    public ArrayList<Prueba> getPruebas() {
        return pruebas;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public String getMap() {
        return map;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public void setPruebas(ArrayList<Prueba> pruebas) {
        this.pruebas = pruebas;
    }

    public void setType(String type) {
        this.type = type;
    }
}
