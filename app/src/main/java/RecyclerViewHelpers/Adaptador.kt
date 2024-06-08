package RecyclerViewHelpers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        fun actualizarLista(nuevaLista: List<tbTicket>){
            Datos = nuevaLista
            notifyDataSetChanged() //notificamos al adaptador que los datos cambiaron
        }

        fun actualicePantalla(uuid: String, newtituloTicket: String, newDescripcion: String, newFechaCreacion: String, newEstadoTicket: String, newFechaFinalizacion: String, newUsuario: String){
            val index = Datos.indexOfFirst { it.uuid == uuid }
            Datos[index].tituloTicket = newtituloTicket
            Datos[index].descripcion = newDescripcion
            Datos[index].fechaCreacion = newFechaCreacion
            Datos[index].estadoTicket = newEstadoTicket
            Datos[index].fechaFinalizacion = newFechaFinalizacion
            Datos[index].usuario = newUsuario

            notifyDataSetChanged()
        }

        fun actualizarDatos(newTituloTicket: String, newDescripcion: String, newFechaCreacion: String, newEstadoTicket: String, newFechaFinalizacion: String, newUsuario: String, uuid: String){
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val updateTicket = objConexion?.prepareStatement("update tbTicket set tituloTicket = ?, descripcion = ?, fechaCreacion = ?, estadoTicket = ?, fechaFinalizacion = ?, usuario = ? where numeroTicket = ?")!!
                updateTicket.setString(1, newTituloTicket)
                updateTicket.setString(2, newDescripcion)
                updateTicket.setString(3, newFechaCreacion)
                updateTicket.setString(4, newEstadoTicket)
                updateTicket.setString(5, newFechaFinalizacion)
                updateTicket.setString(6, newUsuario)
                updateTicket.setString(7, uuid)

                updateTicket.executeUpdate()
                withContext(Dispatchers.Main){
                    actualicePantalla(uuid, newTituloTicket, newDescripcion, newFechaCreacion, newEstadoTicket, newFechaFinalizacion, newUsuario)

                }
            }
        }

        holder.btnEditar.setOnClickListener{
            //alert
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar")
            builder.setMessage("¿Desea actualizar los datos?")

            val cuadroTexto = EditText(context)

            val txtNewTituloTicket = EditText(context).apply {
                setText(item.tituloTicket)
            }

            val txtNewDescripcion = EditText(context).apply {
                setText(item.descripcion)
            }

            val txtNewFechaCreacion = EditText(context).apply {
                setText(item.fechaCreacion)
            }

            val txtNewEstadoTicket = EditText(context).apply {
                setText(item.estadoTicket)
            }

            val txtNewFechaFinalizacion = EditText(context).apply {
                setText(item.fechaFinalizacion)
            }

            val txtNewUsuario = EditText(context).apply {
                setText(item.usuario)
            }

            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(txtNewTituloTicket)
                addView(txtNewDescripcion)
                addView(txtNewFechaCreacion)
                addView(txtNewEstadoTicket)
                addView(txtNewFechaFinalizacion)
                addView(txtNewUsuario)

            }
            builder.setView(layout)


            //botones
            builder.setPositiveButton("Actualizar"){dialog, switch -> actualizarDatos(cuadroTexto.text.toString(),ticket.tituloTicket, ticket.descripcion, ticket.fechaCreacion, ticket.estadoTicket, ticket.fechaFinalizacion, ticket.usuario)}

            builder.setNegativeButton("Cancelar"){dialog, switch -> dialog.dismiss()}

            val dialog = builder.create()
            dialog.show()
        }






        //, ticket.tituloTicket, ticket.descripcion, ticket.fechaCreacion, ticket.estadoTicket, ticket.fechaFinalizacion, ticket.usuario



    }

}