package com.example.dokon.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.dokon.Model.Mahsulot
import com.example.dokon.R


class AllProductAdapter constructor(
    var context:Context,
    var arrayList: ArrayList<Mahsulot>
) : RecyclerView.Adapter<AllProductAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.productlayout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.apply {
            textView1.setText(arrayList.get(position).mahsulotnomi)
            textView2.setText(arrayList.get(position).mahsulotsoni)
            textView3.setText(arrayList.get(position).mahsulotnarxi)




        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    class CategoryViewHolder(itemView: View) : ViewHolder(itemView){
        val textView1=itemView.findViewById<TextView>(R.id.product_name)
        val textView2=itemView.findViewById<TextView>(R.id.product_soni)
        val textView3=itemView.findViewById<TextView>(R.id.product_narxi)



    }
}