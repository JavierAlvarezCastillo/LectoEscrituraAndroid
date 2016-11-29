package com.lectoescritura.game.Data;

import java.util.ArrayList;

/**
 * Created by Javier on 27/11/2016.
 */
public class Prueba {
    private String id, tipo, correcto;
    private ArrayList<String> distractores;
    private int pos_x, pos_y;

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }
    // Getters

    public ArrayList<String> getDistractores() {
        return distractores;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCorrecto() {
        return correcto;
    }

    // Setters

    public void setCorrecto(String correcto) {
        this.correcto = correcto;
    }

    public void setDistractores(ArrayList<String> distractores) {
        this.distractores = distractores;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

