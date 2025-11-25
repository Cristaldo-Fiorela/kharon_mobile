package com.cristaldo.kharon.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cristaldo.kharon.R;

public class Inicio extends AppCompatActivity {

    private Button btnTransferirView;
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inicio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        idUsuario = intent.getIntExtra("idUsuario", -1);

        // vinculacion con view
        btnTransferirView = findViewById(R.id.btnTransferirV);

        btnTransferirView.setOnClickListener(v -> {
                Intent navegacion = new Intent(Inicio.this, TransferenciaAlias.class);
                navegacion.putExtra("idUsuario", idUsuario);
                startActivity(navegacion);
            }
        );

    }

}