package com.cristaldo.kharon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {
    // Declaración de variables para utilizar DB
    private static final String DATABASE_NAME = "banco.db";
    private static final int DATABASE_VERSION = 1;

    // Declaración de nombres de tablas y columnas
    private static final String TABLE_USUARIO = "user";
    private static final String COL_ID = "id";
    private static final String COL_NOMBRE = "username";
    private static final String COL_CONTRASENA = "password";
    private static final String COL_SALDO = "balance";

    // Constructor de clase DBHelper
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Verifica si el usuario y contraseña existen en la base de datos
     * @param nombre Nombre de usuario
     * @param contrasena Contraseña del usuario
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean verificarUsuario(String nombre, String contrasena) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        boolean existe = false;

        try {
            String query = "SELECT * FROM " + TABLE_USUARIO +
                    " WHERE " + COL_NOMBRE + " = ? AND " +
                    COL_CONTRASENA + " = ?";
            cursor = db.rawQuery(query, new String[]{nombre, contrasena});

            existe = cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return existe;
    }

    /**
     * Obtiene el saldo actual de un usuario
     * @param nombre Nombre de usuario
     * @return Saldo del usuario, o 0.0 si no se encuentra
     */
    public double obtenerSaldo(String nombre) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        // saldo default
        double saldo = 0.0;

        try {
            String query = "SELECT " + COL_SALDO + " FROM " + TABLE_USUARIO +
                    " WHERE " + COL_NOMBRE + " = ?";
            cursor = db.rawQuery(query, new String[]{nombre});

            if (cursor.moveToFirst()) {
                saldo = cursor.getDouble(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return saldo;
    }

    /**
     * Actualiza el saldo del usuario sumando el monto especificado
     * @param nombre Nombre de usuario
     * @param montoAgregar Monto a sumar al saldo existente
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizarSaldo(String nombre, double montoAgregar) {
        SQLiteDatabase db = getWritableDatabase();
        boolean actualizado = false;

        try {
            // Obtener saldo actual
            double saldoActual = obtenerSaldo(nombre);
            double nuevoSaldo = saldoActual + montoAgregar;

            // Actualizar saldo
            ContentValues valores = new ContentValues();
            valores.put(COL_SALDO, nuevoSaldo);

            int filasAfectadas = db.update(
                    TABLE_USUARIO,
                    valores,
                    COL_NOMBRE + " = ?",
                    new String[]{nombre}
            );

            actualizado = filasAfectadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actualizado;
    }
}