package com.miempresa.ripter

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar("TELU NOVA")
        setupNavigationDrawer()

        imgEmpresa.setOnClickListener {
            val intent = Intent(this, Empresas::class.java)
            startActivity(intent)
        }

        imgZona.setOnClickListener {
            val intent = Intent(this, Zona::class.java)
            startActivity(intent)
        }

        imgContactos.setOnClickListener {
            val intent = Intent(this, Contactanos::class.java)
            startActivity(intent)
        }
        imgSismos.setOnClickListener {
            val intent = Intent(this, Sismo::class.java)
            startActivity(intent)
        }
        imgSalir.setOnClickListener {
            finishAffinity()
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupNavigationDrawer() {
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
