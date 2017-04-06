package com.calintat.sensors.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.calintat.sensors.R
import com.calintat.sensors.ui.SettingsUI
import com.calintat.sensors.ui.SettingsUI.toolbar
import com.github.calintat.populateWithPreferences
import org.jetbrains.anko.setContentView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        SettingsUI.setContentView(this)

        toolbar.setTitle(R.string.navigation_settings)

        toolbar.setNavigationIcon(R.drawable.ic_action_back)

        toolbar.setNavigationOnClickListener { finish() }

        populateWithPreferences(R.id.fragment, R.xml.preferences)
    }
}