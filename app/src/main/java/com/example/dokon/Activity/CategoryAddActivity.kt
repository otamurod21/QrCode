package com.example.dokon.Activity


import android.content.Intent

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager


import com.example.dokon.Adapter.ProductAdapter
import com.example.dokon.MainActivity

import com.example.dokon.Model.Mahsulot
import com.example.dokon.databinding.ActivityCategoryAddBinding

import com.google.firebase.database.*



class CategoryAddActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryAddBinding
    lateinit var databaseReference: DatabaseReference
    lateinit var productAdapter: ProductAdapter
    val mahsulotarraylist=ArrayList<Mahsulot>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val categoryname2 = intent.getStringExtra("categoryname")
        binding.nameCategory.text = categoryname2
        databaseReference= FirebaseDatabase.getInstance().reference.child("Mahsulotlar").child(categoryname2!!)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mahsulotarraylist.clear()
                for (datasnapshot in snapshot.children){
                    var mahsulot=datasnapshot.getValue(Mahsulot::class.java)
                    mahsulotarraylist.add(mahsulot!!)
                }
                productAdapter= ProductAdapter(this@CategoryAddActivity, mahsulotarraylist)
                binding.recyclerview2.layoutManager= LinearLayoutManager(this@CategoryAddActivity)
                binding.recyclerview2.adapter=productAdapter
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.back.setOnClickListener {
            val intent = Intent(this, KirimActivity::class.java)
            startActivity(intent)
            finish()
        }





    }









}