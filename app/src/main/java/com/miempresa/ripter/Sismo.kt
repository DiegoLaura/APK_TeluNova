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
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException

class Sismo : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var SismoAdaptador: SismoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sismo)

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
        val url = "$urlAPI/intensidad/"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val sismoItems = parseSismoItems(response)
                SismoAdaptador = SismoAdapter(sismoItems)
                recyclerView.adapter = SismoAdaptador
            },
            Response.ErrorListener { error ->
                // Manejar errores de la solicitud
            })

        requestQueue.add(jsonArrayRequest)
    }

    private fun parseSismoItems(response: JSONArray): List<SismoItem> {
        val sismoItems = mutableListOf<SismoItem>()

        for (i in 0 until response.length()) {
            try {
                val jsonObject = response.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val tiempo = jsonObject.getString("tiempo")
                val movimiento = jsonObject.getString("movimiento")

                val sismoItem = SismoItem(id, tiempo, movimiento)
                sismoItems.add(sismoItem)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return sismoItems
    }
}