package com.cristaldo.kharon.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cristaldo.kharon.models.Usuario;

public class UsuarioDAO {

    // gestiona la DB
    private DBHelper dbHelper;
    // nativo de android, mi conexion activa de db
    private SQLiteDatabase database;

    public UsuarioDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void abrir() {
        database = dbHelper.getWritableDatabase();
    }

    public void cerrar() {
        dbHelper.close();
    }

    // ========== CREATE ==========

    public long insertarUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("username", usuario.getUsername());
        valores.put("password", usuario.getPassword());
        valores.put("saldoDisponible", usuario.getSaldoDisponible());

        return database.insert("user", null, valores);
    }

    // ========== READ ==========

    public Usuario login(String username, String password) {
        Usuario usuario = null;

        Cursor cursor = database.query(
                "user",
                null,  // todas las columnas
                "username = ? AND password = ?", // WHERE
                new String[]{username, password}, // valores para el where
                null,null, null
        );

        // primer usuario encontrado (devuelve true si se encontro).
        // toma los datos y construye el objeto para devolver.
        if (cursor.moveToFirst()) {
            usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("saldoDisponible"))
            );
        }

        cursor.close();
        return usuario;
    }

    public Usuario obtenerUsuarioPorId(int id) {
        Usuario usuario = null;

        Cursor cursor = database.query(
                "user",
                null,
                "idUsuario = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("saldoDisponible"))
            );
        }

        cursor.close();
        return usuario;
    }

    // ========== UPDATE ==========

    public int actualizarSaldo(int idUsuario, double nuevoSaldo) {
        ContentValues valores = new ContentValues();
        valores.put("saldoDisponible", nuevoSaldo);

        return database.update(
                "user",
                valores,                                // nuevos valores
                "idUsuario = ?",
                new String[]{String.valueOf(idUsuario)} // valor del WHERE
        );
    }

    public int actualizarUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("username", usuario.getUsername());
        valores.put("password", usuario.getPassword());
        valores.put("saldoDisponible", usuario.getSaldoDisponible());

        return database.update(
                "user",
                valores,
                "idUsuario = ?",
                new String[]{String.valueOf(usuario.getIdUsuario())}
        );
    }

    // ========== MÉTODOS AUXILIARES ==========

    public boolean existeUsername(String username) {
        Cursor cursor = database.query(
                "usuario",
                new String[]{"ID"},
                "username = ?",
                new String[]{username},
                null, null, null
        );

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }
}