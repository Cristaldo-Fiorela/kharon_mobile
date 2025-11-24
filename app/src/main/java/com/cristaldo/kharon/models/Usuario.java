package com.cristaldo.kharon.models;

public class Usuario {
    private int id_usuario;
    private String password;
    private String username;
    private double saldoDisponible;

    public Usuario() {
    }

    // el id se genera en la DB con autoincrement
    public Usuario(String password, String username, double saldoDisponible) {
        this.password = password;
        this.username = username;
        this.saldoDisponible = saldoDisponible;
    }

    //  constructor con ID para objetos que se seleccionan de la DB (ya existen)
    public Usuario(int id_usuario, String password, String username, double saldoDisponible) {
        this.id_usuario = id_usuario;
        this.password = password;
        this.username = username;
        this.saldoDisponible = saldoDisponible;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", username='" + username + '\'' +
                ", saldoDisponible=" + saldoDisponible +
                '}';
    }
}
