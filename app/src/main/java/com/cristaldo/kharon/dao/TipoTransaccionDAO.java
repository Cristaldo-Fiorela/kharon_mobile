package com.cristaldo.kharon.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cristaldo.kharon.models.TipoTransaccion;

import java.util.ArrayList;
import java.util.List;

public class TipoTransaccionDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public TipoTransaccionDAO(Context context) {
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

    public List<TipoTransaccion> getTiposTransacciones() {
        List<TipoTransaccion> tipos = new ArrayList<>();

        Cursor cursor = database.query(
                "tipo_transaccion",
                null, null, null, null, null,
                "idTipoTransaccion ASC"
                );

        while (cursor.moveToNext()) {
            TipoTransaccion tipo = new TipoTransaccion(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idTipoTransaccion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            );
            tipos.add(tipo);
        }

        cursor.close();
        return tipos;
    }

    // ======= RED POR ID ======
    public TipoTransaccion getUnTipoTransaccion(int id) {
        TipoTransaccion tipo = new TipoTransaccion();

        Cursor cursor = database.query(
                "tipo_transaccion",
                null,
                "idTipoTransaccion = ?",
                new String[]{String.valueOf(id)},
                null, null, null
                );

        if (cursor.moveToNext()) {
           tipo = new TipoTransaccion(
                   cursor.getInt(cursor.getColumnIndexOrThrow("idTipoTransaccion")),
                   cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
           );
        }

        cursor.close();
        return tipo;
    }


}
