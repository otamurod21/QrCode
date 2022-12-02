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
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.dokon.MainActivity
import com.example.dokon.Model.Mahsulot
import com.example.dokon.R
import com.example.dokon.databinding.ActivityChiqimBinding
import com.google.firebase.database.FirebaseDatabase

class ChiqimActivity : AppCompatActivity() {
    lateinit var binding: ActivityChiqimBinding
    lateinit var databaseReference: FirebaseDatabase
    private  val CAMERA_REQUEST_CODE=101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChiqimBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.back3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        

    }



    

}