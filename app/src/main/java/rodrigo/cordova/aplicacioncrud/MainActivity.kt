package rodrigo.cordova.aplicacioncrud

import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tbTicket
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
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcionCard)
        val txtFechaCreacion = findViewById<TextView>(R.id.txtFechaCreacionCard)
        val txtEstado = findViewById<TextView>(R.id.txtEstadoCard)
        val txtFechaFinalizacion = findViewById<TextView>(R.id.txtFechaFinalizacionCard)
        val txtUsuario = findViewById<TextView>(R.id.txtUsuario)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        val rcvTicket = findViewById<RecyclerView>(R.id.rcvTickets)
        rcvTicket.layoutManager= LinearLayoutManager(this)

        fun obtenerTickets(): List<tbTicket>{
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet= statement?.executeQuery("Select * from tbTicket")!!

            val listaTicket = mutableListOf<tbTicket>()
            while (resultSet.next()){
                val uuid = resultSet.getString("NumeroTicket")
                val tituloTicket = resultSet.getString("tituloTicket")
                val descripcion = resultSet.getString("descripcion")
                val fechaCreacion = resultSet.getString("fechaCreacion")
                val estado = resultSet.getString("estadoTicket")
                val fechaFinalizacion = resultSet.getString("fechaFinalizacion")
                val usuario = resultSet.getString("usuario")

                val valoresJuntos = tbTicket(uuid, tituloTicket, descripcion, fechaCreacion, estado, fechaFinalizacion, usuario)
                listaTicket.add(valoresJuntos)
            }
                return listaTicket
        }

        CoroutineScope(Dispatchers.IO).launch {
            val ticketDB = obtenerTickets()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(ticketDB)
                rcvTicket.adapter = adapter
            }
        }

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