package rodrigo.cordova.aplicacioncrud

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //mando a llamar
        val txtTitulo = findViewById<TextView>(R.id.txtTitulo)
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcion)
        val txtFechaCreacion = findViewById<TextView>(R.id.txtFechaCreacion)
        val txtEstado = findViewById<TextView>(R.id.txtEstado)
        val txtFechaFinalizacion = findViewById<TextView>(R.id.txtFechaFinalizacion)
        val txtUsuario = findViewById<TextView>(R.id.txtUsuario)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        //programo boton
        btnAgregar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //crear un obj conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //preparestatement
                val addTicket = objConexion?.prepareStatement("insert into tbTicket(NumeroTicket, tituloTicket, descripcion, fechaCreacion, estadoTicket, fechaFinalizacion, usuario) values(?, ?, ?, ?, ?, ?, ?)")!!

                addTicket.setString(1, UUID.randomUUID().toString())
                addTicket.setString(2, txtTitulo.text.toString())
                addTicket.setString(3, txtDescripcion.text.toString())
                addTicket.setString(4, txtFechaCreacion.text.toString())
                addTicket.setString(5, txtEstado.text.toString())
                addTicket.setString(6, txtFechaFinalizacion.text.toString())
                addTicket.setString(7, txtUsuario.text.toString())
                addTicket.executeUpdate()
            }
        }
    }
}