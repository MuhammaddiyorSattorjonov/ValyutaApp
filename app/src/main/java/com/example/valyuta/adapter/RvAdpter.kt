package com.example.valyuta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valyuta.databinding.ItemRvBinding
import com.example.valyuta.models.MyMoney

class RvAdpter(val list:List<MyMoney>): RecyclerView.Adapter<RvAdpter.Vh>() {

    inner class Vh(val itemRvBinding:ItemRvBinding): RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(user: MyMoney,position: Int){
            itemRvBinding.name.text = user.Ccy
            itemRvBinding.narxi.text = user.Nominal+user.Ccy
            itemRvBinding.uzbNarxi.text = user.Rate+"so'm"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}