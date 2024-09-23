package com.example.mysql

import android.app.Application
import com.example.mysql.data.AppContainer
import com.example.mysql.data.AppDataContainer

class MercadinhoApplication: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
