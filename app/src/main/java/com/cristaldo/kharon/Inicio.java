package com.cristaldo.kharon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class Inicio extends AppCompatActivity {

    // Declaración de variables
    private EditText inputMonto;
    private Button btnGuardar;
    private TextView txtSaldo;
    private DBHelper dbHelper;
    private String nombreUsuario;
    private DecimalFormat formatoMoneda;

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

        // Inicializar DBHelper y formato de moneda segun stackoverflow ಥ_ಥ
        dbHelper = new DBHelper(this);
        formatoMoneda = new DecimalFormat("#,##0.00");

        // Obtener el nombre de usuario del Intent
        nombreUsuario = getIntent().getStringExtra("NOMBRE_USUARIO");

        // Elementos de pantalla para acciones mas adelante
        inputMonto = findViewById(R.id.inputMonto);
        btnGuardar = findViewById(R.id.btnDB);
        txtSaldo = findViewById(R.id.txtSaldo);

        // llamado a funcion para mostrar el saldo inicial. en db la columna balance esta en de default en $0,0
        actualizarSaldoEnPantalla();

        // addeventlistener de el boton de guardar el monto ingresado
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarMonto();
            }
        });
    }

    //Guarda el monto ingresado y actualiza el saldo
    private void guardarMonto() {
        // obtenemos el valor que se puso en el inputMonto
        String montoStr = inputMonto.getText().toString().trim();

        //  validar que el campo no esté vacío
        if (montoStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un monto", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);

            //  el monto es positivo. (googlear como limitar para el no ingreso de montos negativos)
            if (monto <= 0) {
                Toast.makeText(this, "El monto debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return;
            }

            // actualizando el saldo en la base de datos. se puede obtener el id del usuario para guardar el monto?
            boolean actualizado = dbHelper.actualizarSaldo(nombreUsuario, monto);

            if (actualizado) {
                Toast.makeText(this,
                        "Monto de $" + formatoMoneda.format(monto) + " agregado exitosamente",
                        Toast.LENGTH_SHORT).show();

                // reseteamos el campo de monto si se actualizo con exito
                inputMonto.setText("");

                // Actualizar el saldo mostrado en pantalla
                actualizarSaldoEnPantalla();
            } else {
                Toast.makeText(this, "Error al actualizar el saldo", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor ingrese un número válido", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Actualiza el TextView con el saldo actual del usuario
     */
    private void actualizarSaldoEnPantalla() {
        double saldo = dbHelper.obtenerSaldo(nombreUsuario);
        txtSaldo.setText("Su saldo es: $" + formatoMoneda.format(saldo));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cerrar la conexión a la base de datos
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}