package com.example.dokon.Adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.dokon.Activity.BarCodeActivity
import com.example.dokon.Activity.CategoryAddActivity
import com.example.dokon.Model.CategoryName
import com.example.dokon.R


class CategoryAdapter constructor(
    var context:Context,
    var arrayList: ArrayList<CategoryName>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.categorylayout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.apply {
            textView1.setText(arrayList.get(position).categoryName)
            relativeLayout.setOnClickListener {
                val intent = Intent(context, CategoryAddActivity::class.java)
                intent.putExtra("categoryname", arrayList.get(position).categoryName)
                context.startActivity(intent)
                return@setOnClickListener
            }
            relativeLayout.setOnLongClickListener {
                val intent = Intent(context, BarCodeActivity::class.java)
                intent.putExtra("categoryname", arrayList.get(position).categoryName)
                context.startActivity(intent)
                return@setOnLongClickListener true
            }



        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    class CategoryViewHolder(itemView: View) : ViewHolder(itemView){
        val textView1=itemView.findViewById<TextView>(R.id.category_name)
        val delete=itemView.findViewById<ImageView>(R.id.category_delete)
        val relativeLayout=itemView.findViewById<RelativeLayout>(R.id.relativelayoutCategory)


    }
}