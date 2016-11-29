package com.lectoescritura.game.Data;

/**
 * Created by Javier on 28/11/2016.
 */
public class Game {

    // stars: (grado de finalización de 1 a 5)
    // estado: 0 (no iniciado), 1 (iniciado), 2(finalizado), 3 (pendiente de realizar otra vez)

    private String id, minigame_id, max_score, stars, estado;
    private int pos_x, pos_y;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMax_score() {
        return max_score;
    }

    public void setMax_score(String max_score) {
        this.max_score = max_score;
    }

    public String getMinigame_id() {
        return minigame_id;
    }

    public void setMinigame_id(String minigame_id) {
        this.minigame_id = minigame_id;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
