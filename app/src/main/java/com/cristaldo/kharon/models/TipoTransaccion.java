package com.cristaldo.kharon.models;

public class TipoTransaccion {
    private int idTipoTransaccion;
    private String descripcion;

    public TipoTransaccion() {
    }

    // SIN ID
    public TipoTransaccion(String descripcion) {
        this.descripcion = descripcion;
    }

    // CON ID
    public TipoTransaccion(int idTipoTransaccion, String descripcion) {
        this.idTipoTransaccion = idTipoTransaccion;
        this.descripcion = descripcion;
    }

    public int getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TipoTransaccion{" +
                "idTipoTransaccion=" + idTipoTransaccion +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
