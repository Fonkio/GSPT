package fr.fonkio.gspt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.fonkio.gspt.R
import fr.fonkio.gspt.entity.Tractor

class TractorAdapter (private val tractorList: List<Tractor>, private val onClickListener: IRecyclerViewItemClickListener?, private val onButtonRemoveButtonClickListener: IRecyclerViewItemRemoveButtonClickListener?) : RecyclerView.Adapter<TractorAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.item_tractor, parent,false)
        return ViewHolder(view, onClickListener, onButtonRemoveButtonClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tractorList[position])

    override fun getItemCount() = tractorList.size

    class ViewHolder(
        itemView: View,
        onClickListener: IRecyclerViewItemClickListener?,
        private val onButtonRemoveButtonClickListener: IRecyclerViewItemRemoveButtonClickListener?
    ) :RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener { onClickListener?.onClickItem(adapterPosition) }
        }

        private val tvBrand: TextView = itemView.findViewById(R.id.tvBrand)
        private val tvModel: TextView = itemView.findViewById(R.id.tvModelTractor)
        private val ibtRemove: ImageButton = itemView.findViewById(R.id.ibtRemove)

        fun bind(tractor: Tractor) {
            tvBrand.text = tractor.brand
            tvModel.text = tractor.model
            ibtRemove.visibility = if(onButtonRemoveButtonClickListener == null) View.GONE else View.VISIBLE
            ibtRemove.setOnClickListener { onButtonRemoveButtonClickListener?.onClickItemRemove(adapterPosition) }
        }
    }
}