package com.dusk.ws_dollar

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dusk.ws_dollar.client.DollarClient
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            val page = getString(R.string.dollarito)
            val dollars = DollarClient.service.getDollars(page)
            val body = dollars.execute().body()
            if (body != null) { // todo remove later
                Log.d("MainActivity", "dollars: ${body.data}")
            }
        }
    }
}