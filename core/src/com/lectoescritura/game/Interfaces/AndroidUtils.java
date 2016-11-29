package com.lectoescritura.game.Interfaces;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Javier on 20/11/2016.
 */
public interface AndroidUtils extends Serializable{

    void toast(final String t);
    void pruebaEducativa(String minigame_id, int pos_x, int pos_y);

}