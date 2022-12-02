package com.example.dokon

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dokon.Activity.ChiqimActivity
import com.example.dokon.Activity.KirimActivity
import com.example.dokon.Adapter.AllProductAdapter
import com.example.dokon.Model.Mahsulot
import com.example.dokon.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var databaseReference: DatabaseReference
    lateinit var allProductAdapter: AllProductAdapter
    val mahsulotarraylist=ArrayList<Mahsulot>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseReference= FirebaseDatabase.getInstance().reference.child("AllMahsulot")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mahsulotarraylist.clear()
                for (datasnapshot in snapshot.children){
                    var mahsulot=datasnapshot.getValue(Mahsulot::class.java)
                    mahsulotarraylist.add(mahsulot!!)
                }
                allProductAdapter= AllProductAdapter(this@MainActivity, mahsulotarraylist)
                binding.recycler2.layoutManager= LinearLayoutManager(this@MainActivity)
                binding.recycler2.adapter=allProductAdapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.title1) {

                startActivity(Intent(this@MainActivity, KirimActivity::class.java))
                finish()
                Toast.makeText(this@MainActivity, "Mahsulot kirimi", Toast.LENGTH_SHORT).show()
            }
            if (item.itemId == R.id.title2) {
                startActivity(Intent(this@MainActivity, ChiqimActivity::class.java))
                finish()
                Toast.makeText(this@MainActivity, "Mahsulot chiqimi", Toast.LENGTH_SHORT).show()
            }
            false
        }



    }


}