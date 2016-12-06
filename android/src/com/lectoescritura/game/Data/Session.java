package com.lectoescritura.game.Data;

import java.util.ArrayList;

/**
 * Created by Javier on 02/12/2016.
 */
public class Session {
    private String id, date, mingameId;
    private ArrayList<Intento> intentos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMingameId() {
        return mingameId;
    }

    public void setMingameId(String mingameId) {
        this.mingameId = mingameId;
    }

    public ArrayList<Intento> getIntentos() {
        return intentos;
    }

    public void setIntentos(ArrayList<Intento> intentos) {
        this.intentos = intentos;
    }
}
