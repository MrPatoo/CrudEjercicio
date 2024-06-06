package RecyclerViewHelpers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rodrigo.cordova.aplicacioncrud.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    //contenido de la card
    val txtNombreCard = view.findViewById<TextView>(R.id.txtTituloCard)
    val txtDescripcionCard = view.findViewById<TextView>(R.id.txtDescripcionCard)
    val txtEstadoCard = view.findViewById<TextView>(R.id.txtEstadoCard)
    val txtFechaCreacionCard = view.findViewById<TextView>(R.id.txtFechaCreacionCard)
    val txtFechaFinalizacionCard = view.findViewById<TextView>(R.id.txtFechaFinalizacionCard)
    val txtUsuarioCard = view.findViewById<TextView>(R.id.txtUsuarioCard)

}