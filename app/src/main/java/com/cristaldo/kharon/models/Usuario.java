package com.cristaldo.kharon.models;

public class Usuario {
    private int idUsuario;
    private String password;
    private String username;
    private double saldoDisponible;

    public Usuario() {
    }

    // el id se genera en la DB con autoincrement
    public Usuario(String username, String password, double saldoDisponible) {
        this.password = password;
        this.username = username;
        this.saldoDisponible = saldoDisponible;
    }

    //  constructor con ID para objetos que se seleccionan de la DB (ya existen)
    public Usuario(int idUsuario, String username, String password,  double saldoDisponible) {
        this.idUsuario = idUsuario;
        this.password = password;
        this.username = username;
        this.saldoDisponible = saldoDisponible;
    }

    public int getIdUsuario() {
        return idUsuario;
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
                "idUsuario=" + idUsuario +
                ", username='" + username + '\'' +
                ", saldoDisponible=" + saldoDisponible +
                '}';
    }
}
