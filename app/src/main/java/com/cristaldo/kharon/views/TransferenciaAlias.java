package com.cristaldo.kharon.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cristaldo.kharon.R;
import com.cristaldo.kharon.dao.AliasDAO;

public class TransferenciaAlias extends AppCompatActivity {
    private int idUsuario;
    private Button btnAliasSgt;
    private EditText etAlias;
    private String alias;
    private ImageView backBtnToolbarAlias;

    private AliasDAO aliasDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transferencia_alias);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.transferenciaAlias), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        aliasDAO = new AliasDAO(this);
        alias = "";

        Intent intent = getIntent();
        idUsuario = intent.getIntExtra("idUsuario", -1);

        btnAliasSgt = findViewById(R.id.btnAliasSgt);
        backBtnToolbarAlias = findViewById(R.id.backBtnToolbarAlias);
        btnAliasSgt.setEnabled(false);
        etAlias = findViewById(R.id.inputAlias);

        backBtnToolbarAlias.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        etAlias.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Activar el botón solo si hay texto
                btnAliasSgt.setEnabled(s.length() > 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnAliasSgt.setOnClickListener(v -> {
            alias = etAlias.getText().toString().trim();

            // NUEVO: Validar que el alias exista
            aliasDAO.abrir();
            boolean aliasExiste = aliasDAO.existeAlias(alias);
            aliasDAO.cerrar();

            if (!aliasExiste) {
                Toast.makeText(this, "El alias ingresado no existe", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent navegacion = new Intent(TransferenciaAlias.this, TransferenciaMonto.class);
            navegacion.putExtra("idUsuario", idUsuario);
            navegacion.putExtra("alias", alias);
            startActivity(navegacion);
        });
    }
}