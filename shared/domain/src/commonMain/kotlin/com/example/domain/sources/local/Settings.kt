package com.example.domain.sources.local


interface Settings {

    fun getIsFirstLaunch(): Boolean

    fun setIsFirstLaunch(isFirstLaunch: Boolean)

}