package fr.fonkio.gspt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.fonkio.gspt.R
import fr.fonkio.gspt.entity.Piece

class PieceAdapter (private val pieceList: List<Piece>, private val onClickListener: IRecyclerViewItemClickListener) : RecyclerView.Adapter<PieceAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_piece, parent,false)
        return ViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(pieceList[position])

    override fun getItemCount() = pieceList.size

    class ViewHolder(itemView: View, onClickListener: IRecyclerViewItemClickListener) :RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { onClickListener.onClickItem(adapterPosition) }
        }

        private val tvLibellePiece: TextView = itemView.findViewById(R.id.tvModelTractor)
        private val tvCase: TextView = itemView.findViewById(R.id.tvBrand)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)

        fun bind(piece: Piece) {
            tvLibellePiece.text = piece.libelle
            tvCase.text = piece.cases
            tvPrice.text = piece.price.toString()
            tvAmount.text = piece.amount.toString()
        }
    }
}