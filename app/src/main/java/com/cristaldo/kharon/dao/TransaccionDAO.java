package com.cristaldo.kharon.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cristaldo.kharon.models.Transaccion;
import com.cristaldo.kharon.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class TransaccionDAO {
    // gestiona la DB
    private DBHelper dbHelper;
    // nativo de android, mi conexion activa de db
    private SQLiteDatabase database;

    public TransaccionDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void abrir() {
        database = dbHelper.getWritableDatabase();
    }

    public void cerrar() {
        dbHelper.close();
    }

    // ===== CREATE
    public long nuevaTransaccion(Transaccion transaccion) {
        ContentValues valores = new ContentValues();
        valores.put("idEstado", transaccion.getIdEstado());
        valores.put("idTipoTransaccion", transaccion.getIdTransaccion());
        valores.put("idUsuario", transaccion.getIdUsuario());
        valores.put("monto", transaccion.getMonto());
        valores.put("descripcion", transaccion.getDescripcion());
        valores.put("fecha", transaccion.getFecha());
        valores.put("hora", transaccion.getHora());
        valores.put("numeroOperacion", transaccion.getNumeroOperacion());
        valores.put("cvuDestino", transaccion.getCvuDestino());

        return database.insert("transaccion", null, valores);
    }

    // ===== READ
    public List<Transaccion> getTransaccionesUsuario(int idUsuario) {
        List<Transaccion> transacciones = new ArrayList<>();

        String sql = "SELECT * FROM transaccion " +
                "WHERE idUsuario = ? " +
                "ORDER BY fecha DESC, hora DESC";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idUsuario)});

        while (cursor.moveToNext()) {
            Transaccion transaccion = new Transaccion(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idTransaccion")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idTipoTransaccion")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idEstado")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("monto")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("fecha")),
                    cursor.getString(cursor.getColumnIndexOrThrow("hora")),
                    cursor.getString(cursor.getColumnIndexOrThrow("numero_operacion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cvu_destino"))
            );
            transacciones.add(transaccion);
        }

        cursor.close();
        return  transacciones;
    };

    public Transaccion obtenerTransaccionPorId(int idTransaccion) {
        Transaccion transaccion = null;

        String sql = "SELECT * FROM transaccion " +
                "WHERE idTransaccion = ? ";


        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idTransaccion)});

        if (cursor.moveToFirst()) {
            transaccion = new Transaccion(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idTransaccion")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idTipoTransaccion")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idEstado")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("monto")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("fecha")),
                    cursor.getString(cursor.getColumnIndexOrThrow("hora")),
                    cursor.getString(cursor.getColumnIndexOrThrow("numero_operacion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cvu_destino"))
            );
        }

        cursor.close();
        return transaccion;
    }

    public List<Transaccion> obtenerUltimos3Movimientos(int idUsuario) {
        List<Transaccion> transacciones = new ArrayList<>();

        String sql = "SELECT * FROM Transaccion " +
                "WHERE idUsuario = ? " +
                "ORDER BY fecha DESC, hora DESC " +
                "LIMIT 3";

        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(idUsuario)});

        while (cursor.moveToNext()) {
            Transaccion transaccion = new Transaccion(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idTransaccion")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idTipoTransaccion")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idEstado")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("monto")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("fecha")),
                    cursor.getString(cursor.getColumnIndexOrThrow("hora")),
                    cursor.getString(cursor.getColumnIndexOrThrow("numero_operacion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cvu_destino"))
            );
            transacciones.add(transaccion);
        }

        cursor.close();
        return transacciones;
    }

    // ========= UTILS
    public boolean existeNumeroOperacion(String numeroOperacion) {
        Cursor cursor = database.query(
                "transaccion",
                new String[]{"idTransaccion"},
                "numeroOperacion = ?",
                new String[]{numeroOperacion},
                null, null, null
        );

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public String generarNumeroOperacion() {
        String numero;
        do {
            long timestamp = System.currentTimeMillis();
            int random = (int) (Math.random() * 1000);
            numero = "#AO" + timestamp + random;
        } while (existeNumeroOperacion(numero));

        return numero;
    }
}
