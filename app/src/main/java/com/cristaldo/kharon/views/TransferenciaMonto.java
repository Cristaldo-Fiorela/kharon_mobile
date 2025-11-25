package com.cristaldo.kharon.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cristaldo.kharon.R;
import com.cristaldo.kharon.dao.TransaccionDAO;
import com.cristaldo.kharon.dao.UsuarioDAO;
import com.cristaldo.kharon.models.Transaccion;
import com.cristaldo.kharon.models.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TransferenciaMonto extends AppCompatActivity {
    private ImageView backBtnToolbarMonto;
    private EditText etInputMonto;
    private Button btnTransferirOperacion;
    private TextView txtSaldoDisponible, txtAlias;
    private String alias;
    private int idUsuario;
    private double monto, saldoDisponible;
    private UsuarioDAO usuarioDAO;
    private TransaccionDAO transaccionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transferencia_monto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.transferenciaMonto), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // inicializar DAOs
        usuarioDAO = new UsuarioDAO(this);
        transaccionDAO = new TransaccionDAO(this);

        Intent intent = getIntent();
        idUsuario = intent.getIntExtra("idUsuario", -1);
        alias = intent.getStringExtra("alias");

        backBtnToolbarMonto = findViewById(R.id.backBtnToolbarMonto);
        etInputMonto = findViewById(R.id.inputMonto);
        txtSaldoDisponible = findViewById(R.id.txtSaldoDisponible);
        txtAlias = findViewById(R.id.txtAlias);
        btnTransferirOperacion = findViewById(R.id.btnTransferirOperacion);

        // config para solo datos positivos
        etInputMonto.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        cargarDatosUsuario();
        String aliasTxtFormateado = String.format("Alias a transferir: %s", alias);
        txtAlias.setText(aliasTxtFormateado);

        btnTransferirOperacion.setOnClickListener(v -> validarYConfirmarTransferencia());

        // Listener para habilitar/deshabilitar botón según input y saldo
        etInputMonto.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnTransferirOperacion.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        backBtnToolbarMonto.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }
    private void cargarDatosUsuario() {
        usuarioDAO.abrir();
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario);
        usuarioDAO.cerrar();

        if (usuario != null) {
            saldoDisponible = usuario.getSaldoDisponible();
            txtSaldoDisponible.setText("Saldo disponible: $" + String.format(Locale.getDefault(), "%.2f", saldoDisponible));
        } else {
            Toast.makeText(this, "Error al cargar datos del usuario", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void validarYConfirmarTransferencia() {
        String montoStr = etInputMonto.getText().toString().trim();

        // Validar que no esté vacío
        if (montoStr.isEmpty()) {
            Toast.makeText(this, "Ingrese un monto", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir string a double
        try {
            monto = Double.parseDouble(montoStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que sea positivo
        if (monto <= 0) {
            Toast.makeText(this, "El monto debe ser mayor a 0", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que tenga saldo suficiente
        if (monto > saldoDisponible) {
            Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar diálogo de confirmación
        mostrarDialogoConfirmacion();
    }

    private void mostrarDialogoConfirmacion() {
        String mensaje = "¿Confirmar transferencia?\n\n" +
                "Destino: " + alias + "\n" +
                "Monto: $" + String.format(Locale.getDefault(), "%.2f", monto) + "\n" +
                "Saldo restante: $" + String.format(Locale.getDefault(), "%.2f", (saldoDisponible - monto));

        new AlertDialog.Builder(this)
                .setTitle("Confirmar transferencia")
                .setMessage(mensaje)
                .setPositiveButton("Confirmar", (dialog, which) -> realizarTransferencia())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void realizarTransferencia() {
        // Obtener fecha y hora actual
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date ahora = new Date();
        String fecha = formatoFecha.format(ahora);
        String hora = formatoHora.format(ahora);

        // Generar número de operación
        transaccionDAO.abrir();
        String numeroOperacion = transaccionDAO.generarNumeroOperacion();

        // Crear objeto Transaccion
        Transaccion transaccion = new Transaccion(
                idUsuario,
                1,
                1,
                monto,
                "Transferencia a " + alias,
                fecha, hora,
                numeroOperacion,
                alias
        );

        // Insertar transacción
        long resultado = transaccionDAO.nuevaTransaccion(transaccion);
        transaccionDAO.cerrar();

        if (resultado != -1) {
            // Actualizar saldo del usuario
            double nuevoSaldo = saldoDisponible - monto;
            usuarioDAO.abrir();
            int filasActualizadas = usuarioDAO.actualizarSaldo(idUsuario, nuevoSaldo);
            usuarioDAO.cerrar();

            if (filasActualizadas > 0) {
                // Transferencia exitosa
                Toast.makeText(this, "Transferencia exitosa", Toast.LENGTH_SHORT).show();

                // volver a inicio
                Intent intent = new Intent(TransferenciaMonto.this, Inicio.class);
                intent.putExtra("idUsuario", idUsuario);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar saldo", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error al realizar la transferencia", Toast.LENGTH_SHORT).show();
        }
    }
}