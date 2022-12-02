package com.example.dokon.Activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dokon.Adapter.CategoryAdapter
import com.example.dokon.MainActivity
import com.example.dokon.Model.CategoryName
import com.example.dokon.R
import com.example.dokon.databinding.ActivityKirimBinding
import com.google.firebase.database.*

class KirimActivity : AppCompatActivity() {
    lateinit var binding:ActivityKirimBinding
    lateinit var databaseReference: DatabaseReference
    val categoryarraylist=ArrayList<CategoryName>()
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityKirimBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Category")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryarraylist.clear()
                for (datasnapshot in snapshot.children){
                    var category=datasnapshot.getValue(CategoryName::class.java)
                    categoryarraylist.add(category!!)
                }
                categoryAdapter= this@KirimActivity.let { CategoryAdapter(it, categoryarraylist) }!!
                binding.recyclerviewCategory.layoutManager= LinearLayoutManager(this@KirimActivity)
                binding.recyclerviewCategory.adapter=categoryAdapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.categoryAdd.setOnClickListener {
            dialogcategory()
        }

    }
    private fun dialogcategory() {
        val dialog= AlertDialog.Builder(this@KirimActivity)
        dialog.setTitle("Kategoriya Qo'shish")
        val view= LayoutInflater.from(this@KirimActivity).inflate(R.layout.mahsulotadd, null)
        val category_nomi=view.findViewById<EditText>(R.id.category_nomi)

        dialog.setPositiveButton("Ha"){_,_->
            var categoryId=databaseReference.push().key.toString()
            val categoryName=CategoryName(
                category_nomi.text.toString(),
                categoryId)
            databaseReference.child(categoryId).setValue(categoryName)
        }.setNegativeButton("Yo'q"){_,_->

        }
        dialog.setView(view)
        dialog.create()
        dialog.show()
    }
}