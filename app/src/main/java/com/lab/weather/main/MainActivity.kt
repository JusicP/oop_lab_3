package com.lab.weather.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.lab.weather.settings.SettingsActivity
import com.lab.weather.shared.AppPreferencesImpl
import com.lab.weather.viewmodel.WeatherViewModel
import com.lab.weather.viewmodel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fabAddWeather.setOnClickListener { view ->
            Snackbar.make(view, "TODO: add weather fragment................", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fabAddWeather).show()
        }

        weatherViewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(AppPreferencesImpl)
        ).get(WeatherViewModel::class.java)

        weatherViewModel.errorMessage.observe(this,{
            Snackbar.make(binding.root, "Got error message: %s".format(it), Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fabAddWeather).show()
        })

        binding.appBarMain.fabRefresh.setOnClickListener { _ ->
            weatherViewModel.getWeather()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // setup preferences
        AppPreferencesImpl.setup(getSharedPreferences("com.lab.weather_preferences", Context.MODE_PRIVATE))
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
}