package com.cristaldo.kharon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declaracion de variables
    Button btn_ingresar;
    EditText inputUsuario;
    EditText inputContra;

    String userName, userID, password;

    DBHelper dbHelper;

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

        // Inicializacion de variables vacias
        userName = "";
        password = "";
        // int o string? para probar mas adelante.
        userID = "";

        // IDs de XMLS
        btn_ingresar = findViewById(R.id.btnLogin);
        inputUsuario = findViewById(R.id.inputUsuario);
        inputContra = findViewById(R.id.inputContra);

        // Instancia de DBHelper (CRUD)
        dbHelper = new DBHelper(MainActivity.this);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = inputUsuario.getText().toString().trim();
                password = inputContra.getText().toString().trim();

                if (dbHelper.verificarUsuario(userName, password)) {
                    // Toast de bienvenida
                    Toast.makeText(MainActivity.this,
                            "¡Bienvenido " + userName + "!",
                            Toast.LENGTH_SHORT).show();

                    Intent navegacion = new Intent(MainActivity.this, Inicio.class);
                    navegacion.putExtra("NOMBRE_USUARIO", userName);
                    startActivity(navegacion);

                }

                // Toast.makeText(MainActivity.this, "Hola desde login", Toast.LENGTH_SHORT).show();
                // intent es una "intension" de una accion
                // Intent recibe la pantalla actual donde estamos parados + la pantalla donde queremos ir
                // Intent navegacion = new Intent(MainActivity.this, Inicio.class);
                // startActivity(navegacion);
            }
        });
    }

}