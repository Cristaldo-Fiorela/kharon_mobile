package com.cristaldo.kharon.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cristaldo.kharon.R;
import com.cristaldo.kharon.dao.AliasDAO;
import com.cristaldo.kharon.dao.UsuarioDAO;
import com.cristaldo.kharon.models.Usuario;

public class MainActivity extends AppCompatActivity {

    // Declaracion de variables
    private EditText etUsername, etPassword;
    private Button btnIngresar;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // inicializar DAO
        usuarioDAO = new UsuarioDAO(this);

        // vincular variables con vistas
        etUsername = findViewById(R.id.inputUsuario);
        etPassword = findViewById(R.id.inputContra);
        btnIngresar = findViewById(R.id.btnLogin);

        btnIngresar.setOnClickListener(view -> iniciarSesion());
    }

    private void iniciarSesion() {
        // obtener el txt
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // abre db y hace login
        usuarioDAO.abrir();
        Usuario usuario = usuarioDAO.login(username, password);
        usuarioDAO.cerrar();

        if(usuario != null) {
            AliasDAO aliasDAO = new AliasDAO(this);
            aliasDAO.abrir();

            if (!aliasDAO.usuarioTieneAlias(usuario.getIdUsuario())) {
                // Crear alias automáticamente
                aliasDAO.crearAliasAutomatico(usuario.getIdUsuario(), usuario.getUsername());
                Toast.makeText(this, "Se creó tu alias: " + usuario.getUsername() + ".KHARON", Toast.LENGTH_SHORT).show();
            }

            aliasDAO.cerrar();

            // Navegar a inicio
            Toast.makeText(this, "Bienvenido " + usuario.getUsername(), Toast.LENGTH_SHORT).show();
            Intent navegacion = new Intent(MainActivity.this, Inicio.class);
            navegacion.putExtra("usuarioId", usuario.getIdUsuario());
            startActivity(navegacion);

        } else {
            Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
    }
}