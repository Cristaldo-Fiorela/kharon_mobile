package com.cristaldo.kharon.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cristaldo.kharon.R;

public class TransferenciaAlias extends AppCompatActivity {
    private int usuarioId;
    private Button btnAliasSgt;
    private EditText etAlias;
    private String alias;
    private ImageView backBtnToolbarAlias;

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
        alias = "";

        Intent intent = getIntent();
        usuarioId = intent.getIntExtra("usuarioId", -1);

        btnAliasSgt = findViewById(R.id.btnAliasSgt);
        backBtnToolbarAlias = findViewById(R.id.backBtnToolbarAlias);
        btnAliasSgt.setEnabled(false);
        etAlias = findViewById(R.id.inputAlias);

        alias = etAlias.getText().toString().trim();

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

        if(btnAliasSgt.isEnabled()) {
            btnAliasSgt.setOnClickListener(v -> {
                Intent navegacion = new Intent(TransferenciaAlias.this, TransferenciaMonto.class);
                navegacion.putExtra("usuarioId", usuarioId);
                navegacion.putExtra("alias", alias);
                startActivity(navegacion);
            });
        }
    }
}