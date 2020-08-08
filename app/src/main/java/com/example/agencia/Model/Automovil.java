package com.example.agencia.Model;

import android.media.Image;

public class Automovil {

    private String Marca;
    private String Modelo;
    private String Capacidad;
    private String Color;
    private Image pctVehicle;

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(String capacidad) {
        Capacidad = capacidad;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Image getPctVehicle() {
        return pctVehicle;
    }

    public void setPctVehicle(Image pctVehicle) {
        this.pctVehicle = pctVehicle;
    }
}
