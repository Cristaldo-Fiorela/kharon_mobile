package com.cristaldo.kharon.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cristaldo.kharon.models.Alias;

public class AliasDAO {
    // gestiona la DB
    private DBHelper dbHelper;
    // nativo de android, mi conexion activa de db
    private SQLiteDatabase database;

    public AliasDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void abrir() {
        database = dbHelper.getWritableDatabase();
    }

    public void cerrar() {
        dbHelper.close();
    }

    // ========= CREAR ALIAS
    public long crearAliasAutomatico(int idUsuario, String username) {
        ContentValues valores = new ContentValues();
        valores.put("idUsuario", idUsuario);
        valores.put("nombreAlias", generarAlias(username));
        valores.put("cvu", generarCVU());

        return database.insert("alias", null, valores);
    }

    // ========= READ POR ID
    public Alias obtenerAliasPorUsuario(int idUsuario) {
        Alias alias = null;

        Cursor cursor = database.query(
                "alias",
                null,
                "idUsuario = ?",
                new String[]{String.valueOf(idUsuario)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            alias = new Alias(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idAlias")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nombreAlias")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cvu"))
            );
        }

        cursor.close();
        return alias;
    }

    // ========= READ Obtener usuario por nombre de alias
    public int obtenerIdUsuarioPorAlias(String nombreAlias) {
        int idUsuario = -1;

        Cursor cursor = database.query(
                "alias",
                new String[]{"idUsuario"},
                "nombreAlias = ?",
                new String[]{nombreAlias},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"));
        }

        cursor.close();
        return idUsuario;
    }

    // ======= UPDATE ALIAS
    public int actualizarNombreAlias(int idUsuario, String nuevoNombreAlias) {
        ContentValues valores = new ContentValues();
        valores.put("nombreAlias", nuevoNombreAlias);

        return database.update(
                "alias",
                valores,
                "idUsuario = ?",
                new String[]{String.valueOf(idUsuario)}
        );
    }

    // ====== UTILS
    private String generarAlias(String username) {
        // Limpiar el username (quitar espacios, convertir a minúsculas)
        String usernameLimpio = username.trim().toLowerCase();

        // Si es un email, usar solo la parte antes del @
        if (usernameLimpio.contains("@")) {
            usernameLimpio = usernameLimpio.split("@")[0];
        }

        return usernameLimpio + ".KHARON";
    }

    private String generarCVU() {
        // Generar 22 dígitos aleatorios
        StringBuilder cvu = new StringBuilder("00000031");

        for (int i = 0; i < 14; i++) {
            cvu.append((int) (Math.random() * 10));
        }

        return cvu.toString();
    }

    public boolean existeAlias(String nombreAlias) {
        Cursor cursor = database.query(
                "alias",
                new String[]{"idAlias"},
                "nombreAlias = ?",
                new String[]{nombreAlias},
                null, null, null
        );

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public boolean usuarioTieneAlias(int idUsuario) {
        Cursor cursor = database.query(
                "alias",
                new String[]{"idAlias"},
                "idUsuario = ?",
                new String[]{String.valueOf(idUsuario)},
                null, null, null
        );

        boolean tiene = cursor.getCount() > 0;
        cursor.close();
        return tiene;
    }
}