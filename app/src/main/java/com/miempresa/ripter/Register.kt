package com.miempresa.ripter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)
        btnRegister.setOnClickListener {
            val username = txtUsername.text.toString()
            val correo = txtCorreo.text.toString()
            val clave = txtPassword.text.toString()
            val queue = Volley.newRequestQueue(this)
            val urlAPI = getString(R.string.urlapi)

            val url = "$urlAPI/usuario/"
            val request = object : JsonObjectRequest(
                Method.POST, url, null,
                Response.Listener { response ->
                    // Registro exitoso, puedes realizar acciones adicionales si es necesario
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener { error ->
                    // Error en la conexión o registro fallido
                    Toast.makeText(
                        this,
                        "Error en el registro: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getBody(): ByteArray {
                    val jsonObject = JSONObject()
                    jsonObject.put("usuario", username)
                    jsonObject.put("correo", correo)
                    jsonObject.put("clave", clave)
                    return jsonObject.toString().toByteArray()
                }
            }
            queue.add(request)
        }
    }
}