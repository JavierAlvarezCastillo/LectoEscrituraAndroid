package com.lectoescritura.game.Data;

import java.util.ArrayList;

/**
 * Created by Javier on 02/12/2016.
 */
public class Intento {
    private String prueba_id, respuesta_correcta, tiempo_primera_respuesta;
    private ArrayList<String> intentos;

    public String getPrueba_id() {
        return prueba_id;
    }

    public void setPrueba_id(String prueba_id) {
        this.prueba_id = prueba_id;
    }

    public String getRespuesta_correcta() {
        return respuesta_correcta;
    }

    public void setRespuesta_correcta(String respuesta_correcta) {
        this.respuesta_correcta = respuesta_correcta;
    }

    public String getTiempo_primera_respuesta() {
        return tiempo_primera_respuesta;
    }

    public void setTiempo_primera_respuesta(String tiempo_primera_respuesta) {
        this.tiempo_primera_respuesta = tiempo_primera_respuesta;
    }

    public ArrayList<String> getIntentos() {
        return intentos;
    }

    public void setIntentos(ArrayList<String> intentos) {
        this.intentos = intentos;
    }
}
