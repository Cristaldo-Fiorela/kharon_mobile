package com.cristaldo.kharon.models;

public class Alias {
    private int idAlias;
    private int idUsuario;
    private String nombreAlias;
    private String cvu;

    public Alias() {
    }

    // SIN ID
    public Alias(int idUsuario, String nombreAlias, String cvu) {
        this.idUsuario = idUsuario;
        this.nombreAlias = nombreAlias;
        this.cvu = cvu;
    }

    // CON ID
    public Alias(int idAlias, int idUsuario, String nombreAlias, String cvu) {
        this.idAlias = idAlias;
        this.idUsuario = idUsuario;
        this.nombreAlias = nombreAlias;
        this.cvu = cvu;
    }

    public int getIdAlias() {
        return idAlias;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreAlias() {
        return nombreAlias;
    }

    public void setNombreAlias(String nombreAlias) {
        this.nombreAlias = nombreAlias;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    @Override
    public String toString() {
        return "Alias{" +
                "idAlias=" + idAlias +
                ", idUsuario=" + idUsuario +
                ", nombreAlias='" + nombreAlias + '\'' +
                ", cvu='" + cvu + '\'' +
                '}';
    }
}
