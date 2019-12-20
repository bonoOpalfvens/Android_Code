package com.example.fluxcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fluxcode.network.persistence.getDatabase
import com.example.fluxcode.network.persistence.repositories.TokenRepository
import com.example.fluxcode.utils.UserService
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup navigation
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_boards, R.id.nav_login, R.id.nav_register,
                R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // handle saved login tokens
        val database = getDatabase(this)
        val tokenRepository = TokenRepository(database)
        UserService.token = tokenRepository.token

        // Responsive layout for logged in users
        UserService.token.observe(this, Observer {
            if(UserService.loggedIn){
                navView.menu.findItem(R.id.nav_logout).isVisible = true
                navView.menu.findItem(R.id.nav_login).isVisible = false
                navView.menu.findItem(R.id.nav_register).isVisible = false

                navController.navigate(R.id.nav_home)
            }else {
                navView.menu.findItem(R.id.nav_logout).isVisible = false

                navView.menu.findItem(R.id.nav_login).isVisible = true
                navView.menu.findItem(R.id.nav_register).isVisible = true
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
