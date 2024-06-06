package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import modelo.tbTicket
import rodrigo.cordova.aplicacioncrud.R

class Adaptador(var Datos: List<tbTicket>):RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //contenido de la card
        val item = Datos[position]
        holder.txtNombreCard.text = item.tituloTicket
        holder.txtDescripcionCard.text = item.descripcion
        holder.txtEstadoCard.text = item.estadoTicket
        holder.txtFechaCreacionCard.text = item.fechaCreacion
        holder.txtFechaFinalizacionCard.text = item.fechaFinalizacion
        holder.txtUsuarioCard.text = item.usuario
    }

}