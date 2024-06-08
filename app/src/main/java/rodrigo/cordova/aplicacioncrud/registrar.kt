package rodrigo.cordova.aplicacioncrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class registrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //todo Registrar/////////////////////////////////////////////////////////////////////////////////////////////
        //mando a llamar
        val txtCorreoRegister = findViewById<TextView>(R.id.txtCorreoRegister)
        val txtContrasenaRegister = findViewById<TextView>(R.id.txtContrasenaRegister)
        val btnCrearUsuario = findViewById<Button>(R.id.btnCrearUsuario)
        val btnIrlogin = findViewById<Button>(R.id.btnIrLogin)

        //programo botones***************************************************
        btnCrearUsuario.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                //prepareStatement
                val crearUsuario = objConexion?.prepareStatement("INSERT INTO tbUsuario(UUID_Usuario, correo, contrasena) VALUES(?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtCorreoRegister.text.toString())
                crearUsuario.setString(3, txtCorreoRegister.text.toString())

                crearUsuario.executeUpdate()

                withContext(Dispatchers.Main){
                    Toast.makeText(this@registrar, "Usuario Creado", Toast.LENGTH_SHORT).show()
                    txtCorreoRegister.setText("")
                    txtContrasenaRegister.setText("")

                }

            }
        }

        btnIrlogin.setOnClickListener{
            //para volver a la pantalla login
            val pantallaLogin = Intent(this, login::class.java)
            startActivity(pantallaLogin)
        }


    }
}