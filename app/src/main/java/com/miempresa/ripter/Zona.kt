package com.miempresa.ripter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException

class Zona : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var ZonaAdaptador: ZonaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zona)

        toolbar("TELU NOVA")
        val navView: NavigationView = findViewById(R.id.navView)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            findViewById(R.id.toolbar),
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchDataFromApi()
    }

    fun toolbar(title:String) {
        setSupportActionBar(findViewById(R.id.toolbar))
        var ab = supportActionBar
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu)
            ab.setDisplayHomeAsUpEnabled(true)
            ab.title = title
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.op -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.op1 -> {
                val intent = Intent(this, Empresas::class.java)
                startActivity(intent)
            }
            R.id.op2 -> {
                val intent = Intent(this, Zona::class.java)
                startActivity(intent)
            }
            R.id.op3 -> {
                val intent = Intent(this, Sismo::class.java)
                startActivity(intent)
            }
            R.id.op4 -> {
                val intent = Intent(this, Contactanos::class.java)
                startActivity(intent)
            }
            R.id.op5 -> {
                finishAffinity()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun fetchDataFromApi() {
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlapi)
        val zonaUrl = "$urlAPI/zona/"
        val empresaUrl = "$urlAPI/empresa/" // URL para obtener los detalles de la empresa

        val zonaItems = mutableListOf<ZonaItem>() // Lista para almacenar los elementos de zona

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, zonaUrl, null,
            Response.Listener { response ->
                for (i in 0 until response.length()) {
                    try {
                        val jsonObject = response.getJSONObject(i)
                        val id = jsonObject.getInt("id")
                        val nombre = jsonObject.getString("nombre")
                        val empresaId = jsonObject.getInt("empresa")

                        // Hacer una solicitud adicional para obtener los detalles de la empresa
                        val empresaJsonObjectRequest = JsonObjectRequest(
                            Request.Method.GET, empresaUrl + empresaId, null,
                            Response.Listener { empresaResponse ->
                                val empresaNombre = empresaResponse.getString("nombre")

                                val zonaItem = ZonaItem(id, nombre, empresaNombre)
                                zonaItems.add(zonaItem)

                                // Verificar si se han recuperado todos los elementos de zona
                                if (zonaItems.size == response.length()) {
                                    ZonaAdaptador = ZonaAdapter(zonaItems)
                                    recyclerView.adapter = ZonaAdaptador
                                }
                            },
                            Response.ErrorListener { error ->
                                // Manejar errores de la solicitud de empresa
                            }
                        )

                        requestQueue.add(empresaJsonObjectRequest)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            },
            Response.ErrorListener { error ->
                // Manejar errores de la solicitud de zona
            }
        )

        requestQueue.add(jsonArrayRequest)
    }


    private fun parseZonaItems(response: JSONArray): List<ZonaItem> {
        val zonaItems = mutableListOf<ZonaItem>()

        for (i in 0 until response.length()) {
            try {
                val jsonObject = response.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val nombre = jsonObject.getString("nombre")
                val empresa = jsonObject.getString("empresa")

                val zonaItem = ZonaItem(id, nombre, empresa)
                zonaItems.add(zonaItem)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return zonaItems
    }
}