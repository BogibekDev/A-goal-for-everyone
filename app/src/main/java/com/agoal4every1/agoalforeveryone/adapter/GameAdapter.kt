package com.agoal4every1.agoalforeveryone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agoal4every1.agoalforeveryone.databinding.ItemGameBinding
import com.agoal4every1.agoalforeveryone.model.Game

class GameAdapter : RecyclerView.Adapter<GameAdapter.VH>() {

    private var list: ArrayList<Game>? = null

    var onItemClick: ((Int) -> Unit)? = null


    inner class VH(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val arrow = list?.get(adapterPosition)!!
            binding.apply {
                ivArrow.setImageResource(arrow.image)
                ivArrow.layoutParams.width = arrow.width
                root.setOnClickListener {
                    onItemClick?.invoke(adapterPosition)
                }

            }
        }
    }

    fun mySubmitList(list: ArrayList<Game>) {
        this.list = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

}