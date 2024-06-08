package rodrigo.cordova.aplicacioncrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //todo login////////////////////////////////////////////////////////////////////////////////////
        //mando a llamar
        val txtCorreo = findViewById<TextView>(R.id.txtCorreo)
        val txtContrasena = findViewById<TextView>(R.id.txtContrasena)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)

        //programar botones
        btnIngresar.setOnClickListener{
            //intent para cambiar a la pantalla principal
            val pantallaPrincipal = Intent(this, MainActivity::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                //prepareStatement
                val comprobarUsuario = objConexion?.prepareStatement("select * from tbUsuario where correo = ? AND contrasena = ?")!!
                comprobarUsuario.setString(1, txtCorreo.text.toString())
                comprobarUsuario.setString(2, txtContrasena.text.toString())

                val resultado = comprobarUsuario.executeQuery()

                //si encuentra el resultado
                if(resultado.next()){
                    startActivity(pantallaPrincipal)
                }
                else{
                    println("Usuario no encontrado")
                }
            }
        }
    }
}