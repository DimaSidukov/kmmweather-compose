package com.example.data.local

import com.example.domain.sources.local.Settings
import com.russhwolf.settings.Settings as RusshWolfSettings

class AppSettings : Settings {

    companion object {
        private const val FIRST_LAUNCH_KEY = "first_launch"
    }

    private val settings: RusshWolfSettings = RusshWolfSettings()

    override fun getIsFirstLaunch(): Boolean = settings.getBoolean(FIRST_LAUNCH_KEY, false)
    override fun setIsFirstLaunch(isFirstLaunch: Boolean) =
        settings.putBoolean(FIRST_LAUNCH_KEY, isFirstLaunch)

}