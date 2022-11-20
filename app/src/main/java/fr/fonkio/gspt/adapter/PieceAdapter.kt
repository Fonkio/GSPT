package fr.fonkio.gspt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.fonkio.gspt.R
import fr.fonkio.gspt.activity.PieceActivity
import fr.fonkio.gspt.entity.Piece

class PieceAdapter (private val pieceList: List<Piece>, private val sid : String, private val pieceActivity: PieceActivity) : RecyclerView.Adapter<PieceAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_piece, parent,false)
        return ViewHolder(view, sid, pieceActivity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(pieceList[position])

    override fun getItemCount() = pieceList.size

    class ViewHolder(itemView: View, private val sid : String, val pieceActivity: PieceActivity) :RecyclerView.ViewHolder(itemView) {
        private val tvLibellePiece: TextView = itemView.findViewById(R.id.tvLibellePiece)
        fun bind(piece: Piece) {
            tvLibellePiece.text = piece.libelle
        }
    }
}