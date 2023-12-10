package com.mhmc.feriaiot.Modelos;

import java.util.Date;

public class Eventos {

    private String idEvento;
    private String timestamp;
    private String sensor;
    private String ubicacionN;
    private String ubicacionS;

    public Eventos(String idEvento, String timestamp, String sensor, String ubicacionN, String ubicacionS) {
        this.idEvento = idEvento;
        this.timestamp = timestamp;
        this.sensor = sensor;
        this.ubicacionN = ubicacionN;
        this.ubicacionS = ubicacionS;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getUbicacionN() {
        return ubicacionN;
    }

    public void setUbicacionN(String ubicacionN) {
        this.ubicacionN = ubicacionN;
    }

    public String getUbicacionS() {
        return ubicacionS;
    }

    public void setUbicacionS(String ubicacionS) {
        this.ubicacionS = ubicacionS;
    }
}
