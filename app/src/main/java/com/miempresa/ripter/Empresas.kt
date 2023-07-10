package com.miempresa.ripter

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_empresas.*
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import org.json.JSONArray
import org.json.JSONException

class Empresas : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var EmpresaAdaptador: EmpresaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresas)


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
        val url = "$urlAPI/empresa/"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val empresaItems = parseEmpresaItems(response)
                EmpresaAdaptador = EmpresaAdapter(empresaItems)
                recyclerView.adapter = EmpresaAdaptador
            },
            Response.ErrorListener { error ->
                // Manejar errores de la solicitud
            })

        requestQueue.add(jsonArrayRequest)
    }

    private fun parseEmpresaItems(response: JSONArray): List<EmpresaItem> {
        val empresaItems = mutableListOf<EmpresaItem>()

        for (i in 0 until response.length()) {
            try {
                val jsonObject = response.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val nombre = jsonObject.getString("nombre")
                val telefono = jsonObject.getString("telefono")

                val empresaItem = EmpresaItem(id, nombre, telefono)
                empresaItems.add(empresaItem)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return empresaItems
    }
}
