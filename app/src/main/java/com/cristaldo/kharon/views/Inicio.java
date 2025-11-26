package com.cristaldo.kharon.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import java.util.List;
import java.util.Locale;

public class Inicio extends AppCompatActivity {

    private View btnTransferirView;
    private int idUsuario;
    private TextView txtTituloToolbar, txtSaldo;
    private LinearLayout movimientosLista;
    private UsuarioDAO usuarioDAO;
    private TransaccionDAO transaccionDAO;

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

        // Inicializar DAOs
        usuarioDAO = new UsuarioDAO(this);
        transaccionDAO = new TransaccionDAO(this);

        // Vincular vistas
        btnTransferirView = findViewById(R.id.btnTransferirV);
        txtTituloToolbar = findViewById(R.id.tituloToolbarMenu);
        txtSaldo = findViewById(R.id.saldo);
        movimientosLista = findViewById(R.id.movimientosLista);

        // Cargar datos del usuario
        cargarDatosUsuario();

        // Cargar últimas 3 transacciones
        cargarTransacciones();

        // Botón transferir
        btnTransferirView.setOnClickListener(v -> {
            Intent navegacion = new Intent(Inicio.this, TransferenciaAlias.class);
            navegacion.putExtra("idUsuario", idUsuario);
            startActivity(navegacion);
        });
    }

    private void cargarDatosUsuario() {
        usuarioDAO.abrir();
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario);
        usuarioDAO.cerrar();

        if (usuario != null) {
            txtTituloToolbar.setText("Hola, " + usuario.getUsername());
            txtSaldo.setText("$" + String.format(Locale.forLanguageTag("es-AR"), "%.2f", usuario.getSaldoDisponible()));
        }
    }

    private void cargarTransacciones() {
        transaccionDAO.abrir();
        List<Transaccion> transacciones = transaccionDAO.obtenerUltimos3Movimientos(idUsuario);
        transaccionDAO.cerrar();

        // Limpiar la lista antes de agregar
        movimientosLista.removeAllViews();

        if (transacciones.isEmpty()) {
            // Mostrar mensaje "Sin movimientos"
            mostrarSinMovimientos();
        } else {
            // Mostrar las 3 transacciones
            for (Transaccion transaccion : transacciones) {
                agregarMovimientoALista(transaccion);
            }
        }
    }

    private void mostrarSinMovimientos() {
        TextView txtSinMovimientos = new TextView(this);
        txtSinMovimientos.setText("Sin movimientos recientes");
        txtSinMovimientos.setTextColor(getResources().getColor(R.color.grisIntermedio));
        txtSinMovimientos.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        txtSinMovimientos.setPadding(0, 40, 0, 40);

        movimientosLista.addView(txtSinMovimientos);
    }

    private void agregarMovimientoALista(Transaccion transaccion) {
        // Inflar el layout del item de movimiento
        View itemView = getLayoutInflater().inflate(R.layout.item_movimiento, movimientosLista, false);

        // Vincular vistas del item
        TextView txtTipoOperacion = itemView.findViewById(R.id.tipoOperacion);
        TextView txtEstadoOperacion = itemView.findViewById(R.id.estadoOperacion);
        TextView txtOperacionMonto = itemView.findViewById(R.id.operacionMonto);
        TextView txtOperacionFecha = itemView.findViewById(R.id.operacionFecha);
        ImageView ivMovimientoIcono = itemView.findViewById(R.id.operacionIcono);


        // Obtener tipo de transacción
        String tipoTexto = obtenerTipoTransaccion(transaccion.getIdTipoTransaccion());
        String estadoTexto = obtenerEstadoOperacion(transaccion.getIdEstado());

        // Formatear monto (negativo si es transferencia enviada)
        String montoTexto;
        if (transaccion.getIdTipoTransaccion() == 1) {
            montoTexto = "-$" + String.format(Locale.forLanguageTag("es-AR"), "%.2f", transaccion.getMonto());
        } else {
            montoTexto = "+$" + String.format(Locale.forLanguageTag("es-AR"), "%.2f", transaccion.getMonto());
            txtOperacionMonto.setTextColor(getResources().getColor(R.color.verde));
            ivMovimientoIcono.setImageResource(R.drawable.recibido);
        }

        // Formatear fecha (de yyyy-MM-dd a dd/MM)
        String fechaFormateada = formatearFecha(transaccion.getFecha());

        // Setear textos
        txtTipoOperacion.setText(tipoTexto);
        txtEstadoOperacion.setText(estadoTexto);
        txtOperacionMonto.setText(montoTexto);
        txtOperacionFecha.setText(fechaFormateada);

        // Agregar el item a la lista
        movimientosLista.addView(itemView);
    }

    private String obtenerTipoTransaccion(int idTipo) {
        switch (idTipo) {
            case 1: return "Transferencia enviada";
            case 2: return "Transferencia recibida";
            case 3: return "Ingreso";
            case 4: return "Pago de servicios";
            default: return "Operación";
        }
    }

    private String obtenerEstadoOperacion(int idEstado) {
        switch (idEstado) {
            case 1: return "Exitosa";
            case 2: return "Pendiente";
            case 3: return "Fallida";
            default: return "Desconocido";
        }
    }

    private String formatearFecha(String fecha) {
        try {
            String[] partes = fecha.split("-");
            if (partes.length == 3) {
                return partes[2] + "/" + partes[1]; // dd/MM
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fecha;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar datos cuando vuelva a esta pantalla
        cargarDatosUsuario();
        cargarTransacciones();
    }
}