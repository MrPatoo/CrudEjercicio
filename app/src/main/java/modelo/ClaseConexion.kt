package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection?{
        try {
            val url = "jdbc:oracle:thin:@10.10.0.79:1521:xe" // cambiar ip si se usa en otra pc
            val usuario = "system" //cambiar si se usa otro usuario
            val contrasena = "desarrollo" //cambiar si se usa otro usuario

            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection
        }catch (e: Exception){
            println("error: $e")
            return null
        }
    }
}