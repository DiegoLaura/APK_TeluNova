package com.miempresa.ripter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_welcome.*
import org.json.JSONArray


class welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        btnLogin.setOnClickListener {
            val email = txtUsername.text.toString()
            val clave = txtPassword.text.toString()
            val queue = Volley.newRequestQueue(this)
            val urlAPI = getString(R.string.urlapi)

            val url = "$urlAPI/usuario"
            val request = object : StringRequest(
                Request.Method.GET, url,
                Response.Listener { response ->
                    val jsonArray = JSONArray(response)

                    // Verificar las credenciales de inicio de sesión
                    var loggedIn = false
                    for (i in 0 until jsonArray.length()) {
                        val cuenta = jsonArray.getJSONObject(i)
                        val username = cuenta.getString("correo")
                        val password = cuenta.getString("clave")
                        if (email == username && clave == password) {
                            loggedIn = true
                            break
                        }
                    }

                    // Mostrar el mensaje según el resultado del inicio de sesión
                    if (loggedIn) {
                        Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                        // Redirigir a la actividad correspondiente
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Error en email o contraseña", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error en la conexión: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            ) {}

            // Agregar la solicitud a la cola de solicitudes de Volley
            queue.add(request)
        }
        btnRegister.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}

