package com.miempresa.ripter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class Contactanos : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactanos)

        // Obtener una referencia al WebView
        webView = findViewById(R.id.webView)

        // Configurar opciones del WebView
        webView.settings.javaScriptEnabled = true

        // Cargar una página web en el WebView
        val url = "https://ultimosismo.igp.gob.pe/inicio"
        webView.loadUrl(url)

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
}