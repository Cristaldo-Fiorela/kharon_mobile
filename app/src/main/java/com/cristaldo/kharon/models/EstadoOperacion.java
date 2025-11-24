package com.cristaldo.kharon.models;

public class EstadoOperacion {
    private int idEstado;
    private String descripcion;

    public EstadoOperacion() {
    }

    // SIN ID
    public EstadoOperacion(String descripcion) {
        this.descripcion = descripcion;
    }

    // CON ID
    public EstadoOperacion(int idEstado, String descripcion) {
        this.idEstado = idEstado;
        this.descripcion = descripcion;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "EstadoOperacion{" +
                "idEstado=" + idEstado +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
