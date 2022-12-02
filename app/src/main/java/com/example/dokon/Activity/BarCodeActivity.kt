package com.example.dokon.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.dokon.Model.Mahsulot
import com.example.dokon.R
import com.example.dokon.databinding.ActivityBarCodeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class BarCodeActivity : AppCompatActivity() {
    lateinit var binding: ActivityBarCodeBinding
    lateinit var codeScanner: CodeScanner
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference2: DatabaseReference
    private  val CAMERA_REQUEST_CODE=101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBarCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back2.setOnClickListener {
            startActivity(Intent(this, CategoryAddActivity::class.java))
            finish()
        }
        setupPermission()
        codeScanner()

    }


    @SuppressLint("MissingInflatedId")
    private fun codeScanner(){
        codeScanner= CodeScanner(this@BarCodeActivity, binding.codeScanner)

        codeScanner.apply {
            camera= CodeScanner.CAMERA_BACK
            formats= CodeScanner.ALL_FORMATS

            autoFocusMode= AutoFocusMode.SAFE
            scanMode= ScanMode.CONTINUOUS
            isAutoFocusEnabled=true
            isFlashEnabled=false

            decodeCallback= DecodeCallback {
                runOnUiThread {
                    val intent = intent
                    val categoryname = intent.getStringExtra("categoryname")
                    databaseReference2= FirebaseDatabase.getInstance().reference.child("AllMahsulot")
                    databaseReference= FirebaseDatabase.getInstance().reference.child("Mahsulotlar").child(categoryname!!)


                    val mahsulotId = databaseReference.push().key

                    var dialog= AlertDialog.Builder(this@BarCodeActivity)
                    dialog.setTitle(categoryname+" Kategoriya Qo'shish")
                    val view= LayoutInflater.from(this@BarCodeActivity).inflate(R.layout.qrlayout, null)
                    val mahsulot_nomi=view.findViewById<TextView>(R.id.qr_name)
                    val mahsulot_soni=view.findViewById<EditText>(R.id.mahsulot_soni)
                    val mahsulot_narx=view.findViewById<EditText>(R.id.mahsulot_narxi)
                    mahsulot_nomi.text=it.text
                    dialog.setPositiveButton("Ha"){_,_->
                        var mahsulot= Mahsulot(
                            mahsulotnomi =it.text.toString(),
                            mahsulotsoni = mahsulot_soni.text.toString(),
                            mahsulotnarxi = mahsulot_narx.text.toString(),
                            mahsulotId = mahsulotId.toString()

                        )
                        var mahsulot2= Mahsulot(
                            mahsulotnomi =it.text.toString(),
                            mahsulotsoni = mahsulot_soni.text.toString(),
                            mahsulotnarxi = mahsulot_narx.text.toString(),
                            mahsulotId = mahsulotId.toString()

                        )

                        databaseReference.child(mahsulotId!!).setValue(mahsulot)
                        databaseReference2.child(mahsulotId!!).setValue(mahsulot2)
                        finish()


                    }.setNegativeButton("Yo'q"){_,_->

                    }
                    dialog.setView(view)
                    dialog.create()
                    dialog.show()

                }
            }
            errorCallback= ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera intialization error: ${it.message}")
                }
            }
        }
        binding.codeScanner.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermission(){
        val permission= ContextCompat.checkSelfPermission(this@BarCodeActivity,
            android.Manifest.permission.CAMERA)


        if (permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }
    private fun makeRequest(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_REQUEST_CODE ->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "You need the camera permission to be able  to use this app!", Toast.LENGTH_SHORT).show()
                }else{

                }
            }
        }
    }

}