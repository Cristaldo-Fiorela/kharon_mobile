package com.cristaldo.kharon.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cristaldo.kharon.models.EstadoOperacion;

import java.util.ArrayList;
import java.util.List;

public class EstadoOperacionDAO {

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public EstadoOperacionDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // abrir y cerrar DB
    public void abrir() {
        database = dbHelper.getWritableDatabase();
    }

    public void cerrar() {
        database.close();
    }
    // ========= READ ALL =========

    public List<EstadoOperacion> getEstadosOperacion() {
        List<EstadoOperacion> estados = new ArrayList<>();

        Cursor cursor = database.query(
                "estado_operacion",
                null, null, null, null, null,
                "idEstado ASC"
        );

        while (cursor.moveToNext()) {
            EstadoOperacion tipo = new EstadoOperacion(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idEstado")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            );
            estados.add(tipo);
        }

        cursor.close();
        return estados;
    }

    // ======= RED POR ID ======
    public EstadoOperacion getUnEstadoOperacion(int id) {
        EstadoOperacion estado = new EstadoOperacion();

        Cursor cursor = database.query(
                "estado_operacion",
                null,
                "idEstado = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor.moveToNext()) {
            estado = new EstadoOperacion(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idEstado")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            );
        }

        cursor.close();
        return estado;
    }



}
