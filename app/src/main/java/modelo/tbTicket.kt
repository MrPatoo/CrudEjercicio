package modelo

import java.util.UUID

data class tbTicket(
    val uuid: String,
    val tituloTicket: String,
    val descripcion: String,
    val fechaCreacion: String,
    val estadoTicket: String,
    val fechaFinalizacion: String,
    val usuario: String
)
