package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.tbTicket
import rodrigo.cordova.aplicacioncrud.R

class Adaptador(private var Datos: List<tbTicket>):RecyclerView.Adapter<ViewHolder>() {

    //todo Actualiza el RCV con los nuevos datos
    fun actualizarLista(nuevaLista:List<tbTicket>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    //todo 1.Eliminar/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun eliminarDatos(tituloTicket: String, position: Int){
        //actualizar lista de datos
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {
            //crear objConexion
            val objConexion = ClaseConexion().cadenaConexion()

            //variable con el PrepareStatement
            val deleteTicket = objConexion?.prepareStatement("delete from tbTicket where tituloTicket = ?")!!
            deleteTicket.setString(1, tituloTicket)
            deleteTicket.executeUpdate()

            //commit dentro de la db
            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        //notifica al adaptador
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }


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

        //todo 2.Eliminar/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        val ticket = Datos[position]
        holder.txtNombreCard.text = ticket.tituloTicket
        holder.txtDescripcionCard.text = ticket.descripcion
        holder.txtUsuarioCard.text = ticket.usuario
        holder.txtEstadoCard.text = ticket.estadoTicket
        holder.txtFechaCreacionCard.text = ticket.fechaCreacion
        holder.txtFechaFinalizacionCard.text = ticket.fechaFinalizacion

        //click al icono para eliminar
        holder.btnEliminar.setOnClickListener{
            //alert
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setTitle("¿Desea eliminar el ticket?")
            //botones
            builder.setPositiveButton("Si"){dialog, switch -> eliminarDatos(ticket. tituloTicket, position)

            }

            builder.setNegativeButton("No"){dialog, switch -> dialog.dismiss()

            }
            val dialog = builder.create()
            dialog.show()

        }

       //todo Editar datos //////////////////////////////////////////////////////////////////////////////////////////////////////////



    }

}