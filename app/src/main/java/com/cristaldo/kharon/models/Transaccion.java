package com.cristaldo.kharon.models;

public class Transaccion {
    private int idTransaccion;
    private int idUsuario;
    private int idTipoTransaccion;
    private int idEstado;
    private double monto;
    private String descripcion;
    private String fecha;
    private String hora;
    private String numeroOperacion;
    private String cvuDestino;

    public Transaccion() {
    }

    // SIN ID
    public Transaccion(int idUsuario, int idTipoTransaccion, int idEstado, double monto, String descripcion, String fecha, String hora, String numeroOperacion, String cvuDestino) {
        this.idUsuario = idUsuario;
        this.idTipoTransaccion = idTipoTransaccion;
        this.idEstado = idEstado;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.numeroOperacion = numeroOperacion;
        this.cvuDestino = cvuDestino;
    }

    // CON ID
    public Transaccion(int idTransaccion, int idUsuario, int idTipoTransaccion, int idEstado, double monto, String descripcion, String fecha, String hora, String numeroOperacion, String cvuDestino) {
        this.idTransaccion = idTransaccion;
        this.idUsuario = idUsuario;
        this.idTipoTransaccion = idTipoTransaccion;
        this.idEstado = idEstado;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.numeroOperacion = numeroOperacion;
        this.cvuDestino = cvuDestino;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    public void setIdTipoTransaccion(int idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public String getCvuDestino() {
        return cvuDestino;
    }

    public void setCvuDestino(String cvuDestino) {
        this.cvuDestino = cvuDestino;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "idTransaccion=" + idTransaccion +
                ", idUsuario=" + idUsuario +
                ", idTipoTransaccion=" + idTipoTransaccion +
                ", idEstado=" + idEstado +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", numeroOperacion='" + numeroOperacion + '\'' +
                ", cvuDestino='" + cvuDestino + '\'' +
                '}';
    }
}
