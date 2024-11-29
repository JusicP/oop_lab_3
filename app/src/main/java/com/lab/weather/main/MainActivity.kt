package com.lab.weather.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lab.weather.R
import com.lab.weather.databinding.ActivityMainBinding
import com.lab.weather.main.settings.SettingsActivity
import com.lab.weather.shared.AppPreferencesImpl
import com.lab.weather.main.ui.WeatherViewModel
import com.lab.weather.main.ui.WeatherViewModelFactory
import com.lab.weather.service.WeatherService

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // setup preferences
        AppPreferencesImpl.setup(getSharedPreferences("com.lab.weather_preferences", Context.MODE_PRIVATE))

        weatherViewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(WeatherService(AppPreferencesImpl)),
        ).get(WeatherViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fabRefresh.setOnClickListener { view ->
            onRefresh(view)
        }

        binding.appBarMain.fabFavourite.setOnClickListener { view ->
            onMakeCityFavourite(view)
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_cities
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_about -> {
                //showAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onRefresh(view: View) {
        weatherViewModel.getFavouriteCity()
    }

    fun onMakeCityFavourite(view: View) {
        val cityTextView = findViewById<TextView>(R.id.cityName)
        weatherViewModel.setFavouriteCity(cityTextView.text.toString())

        Snackbar.make(view, "Added", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .setAnchorView(R.id.fab_favourite).show()


    }

    fun setRefreshBtnVisible(visible: Boolean) {
        binding.appBarMain.fabRefresh.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setFavouriteBtnVisible(visible: Boolean) {
        binding.appBarMain.fabFavourite.visibility = if (visible) View.VISIBLE else View.GONE
    }
}