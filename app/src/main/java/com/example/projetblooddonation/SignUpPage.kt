package com.example.projetblooddonation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.ImageView
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class SignUpPage : AppCompatActivity() {
    var Id: String? = null
    var City: String? = null
    var Password: String? = null
    var Mobile: String? = null
    var Category: String? = null
    private var name: EditText? = null
    private var Name: String? = null
    private val PICK_IMAGE_REQUEST = 1
    private var userImageView: ImageView? = null
    private var btnChooseImage: Button? = null
    private var imageBase64: String? = null


    private var id: EditText? = null
    private var city: EditText? = null
    private var pwd: EditText? = null
    private var phone: EditText? = null
    var bloodgrp: Spinner? = null
    private var submitbtn: Button? = null
    private var signin: TextView? = null
    private var auth: FirebaseAuth? = null
    private var dbref: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)
        id = findViewById(R.id.Email)
        city = findViewById(R.id.city)
        name = findViewById(R.id.name)

        bloodgrp = findViewById(R.id.blood_group)
        pwd = findViewById(R.id.bld)
        phone = findViewById(R.id.number)
        submitbtn = findViewById(R.id.modify)
        signin = findViewById(R.id.register)
        auth = FirebaseAuth.getInstance()
        val items = arrayOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
        userImageView = findViewById(R.id.userImage)
        btnChooseImage = findViewById(R.id.btnChooseImage)
        btnChooseImage?.setOnClickListener {
            openGallery()
        }

        bloodgrp?.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
            )
        )
        bloodgrp?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                Category = bloodgrp?.getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
        submitbtn?.setOnClickListener(View.OnClickListener {
            Id = id?.getText().toString()
            City = city?.getText().toString()
            Password = pwd?.getText().toString()
            Mobile = phone?.getText().toString()
            Name = name?.text.toString()
            if (Name!!.isEmpty()) {
                name?.error = "Make sure you enter your name!"
                name?.requestFocus()
            }

            if (Id!!.isEmpty()) {
                id?.setError("Make sure you enter your email!")
                id?.requestFocus()
            } else if (City!!.isEmpty()) {
                city?.setError("Make sure you enter your city!")
                city?.requestFocus()
            } else if (bloodgrp?.getSelectedItem() == null) {
                Toast.makeText(
                    this@SignUpPage,
                    "Make sure to choose your blood group",
                    Toast.LENGTH_SHORT
                ).show()
                bloodgrp?.requestFocus()
            } else if (Password!!.isEmpty()) {
                pwd?.setError("Make sure you enter your password!")
                pwd?.requestFocus()
            } else if (Mobile!!.isEmpty()) {
                phone?.setError("Make sure you enter your phone number!")
                phone?.requestFocus()
            } else {
                auth!!.createUserWithEmailAndPassword(Id!!, Password!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUserId = task.result.user!!.uid
                            dbref = FirebaseDatabase.getInstance().getReference("user")
                                .child(currentUserId)
                            insertData()
                        } else {
                            Toast.makeText(
                                this@SignUpPage,
                                "Error while creating the donor: " + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            id?.setText("")
                            city?.setText("")
                            pwd?.setText("")
                            phone?.setText("")
                        }
                    }
            }
        })
        signin?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@SignUpPage,
                    LoginPage::class.java
                )
            )
        })
    }

    private fun insertData() {
        // Assure-toi que Name est une variable de classe initialisée avant cet appel
        val user = User(Id, Mobile, Password, City, Category, Name, imageBase64)
        dbref!!.setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@SignUpPage, "Donor added with success!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignUpPage, MainActivity2::class.java))
                finish()
            } else {
                val exception = task.exception
                exception?.printStackTrace()
                Toast.makeText(this@SignUpPage, "Error adding donor: ${exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Choisir une image"), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                userImageView?.setImageBitmap(bitmap) // montre l’image choisie
                imageBase64 = encodeImageToBase64(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    private fun encodeImageToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}