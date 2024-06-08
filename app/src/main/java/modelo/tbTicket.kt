package modelo

import java.util.UUID

data class tbTicket(
    val uuid: String,
    var tituloTicket: String,
    var descripcion: String,
    var fechaCreacion: String,
    var estadoTicket: String,
    var fechaFinalizacion: String,
    var usuario: String
)
