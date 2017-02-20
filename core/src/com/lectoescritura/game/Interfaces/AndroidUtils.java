package com.lectoescritura.game.Interfaces;

import java.io.Serializable;

/**
 * Created by Javier on 20/11/2016.
 */
public interface AndroidUtils extends Serializable{

    void toast(final String t);
    void pruebaEducativa(String minigame_id, String map, int pos_x, int pos_y, float camposx, float camposy, int ultimax, int ultimay, int puntos, int energia, int estrellas);
    void pruebaFinal(String minigame_id, String map, int pos_x, int pos_y, float camposx, float camposy, int ultimax, int ultimay, int puntos, int energia, int estrellas);
    void pruebaAleatoria(String minigame_id, String map, int pos_x, int pos_y, float camposx, float camposy, int ultimax, int ultimay, int puntos, int energia, int estrellas);
    void salir(int pos_x, int pos_y, float camposx, float camposy, int ultimax, int ultimay, int puntos, int energia, int estrellas);
}