package com.dusk.ws_dollar


import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dusk.ws_dollar.adapter.DollarsAdapter
import com.dusk.ws_dollar.client.DollarClient
import com.dusk.ws_dollar.databinding.ActivityMainBinding
import com.dusk.ws_dollar.dto.Page
import com.dusk.ws_dollar.utils.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var spinner: Spinner
    private var pages: List<Page> = emptyList()
    private var namePages: List<String> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.updatedTextViewDate()
        this.findData()
        this.refreshButtonAction()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }

    private fun findData() {
        GlobalScope.launch(Dispatchers.IO) {
            val pagesCall = DollarClient.service.getPages()
            val response = pagesCall.execute().body()

            if (response != null && HttpURLConnection.HTTP_OK == response.code) {
                withContext(Dispatchers.Main) {
                    pages = response.data
                    namePages = pages.map { page -> page.name }

                    spinner = binding.spinnerPages
                    val spinnerAdapter =
                        ArrayAdapter(
                            this@MainActivity,
                            R.layout.simple_spinner_item,
                            namePages
                        )
                    spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = spinnerAdapter

                    onSelected()
                    binding.progressBar.visibility = View.INVISIBLE;
                }
            } else {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE;
                    binding.progressBar2.visibility = View.GONE;
                    binding.errorImageView.visibility = View.VISIBLE
                    binding.refreshImageButton.isEnabled = false
                }
            }
        }
    }

    private fun refreshButtonAction() {
        binding.refreshImageButton.setOnClickListener {
            binding.progressBar2.visibility = View.VISIBLE;
            val itemSelected = spinner.selectedItem.toString()
            val pageSelected = pages.find { it.name == itemSelected }
            if (pageSelected != null) {
                pageSelected(pageSelected)
                delayProgressBar()
            }
        }
    }

    private fun delayProgressBar() {
        val handlerThread = HandlerThread("ProgressBarHandlerThread")
        handlerThread.start()

        val handler = Handler(handlerThread.looper)
        handler.postDelayed({
            runOnUiThread {
                binding.progressBar2.visibility = View.GONE
            }
            handlerThread.quit()
        }, 1000)
    }

    private fun onSelected() {
        binding.spinnerPages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.progressBar2.visibility = View.VISIBLE;

                val selected = namePages[position]
                val pageSelected = pages.find { it.name == selected }
                if (pageSelected != null) {
                    pageSelected(pageSelected)

                    Glide.with(binding.root.context)
                        .load(pageSelected.image)
                        .into(binding.imageViewDollar)

                    binding.imageViewDollar.setOnClickListener {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(pageSelected.route)
                        )
                        ContextCompat.startActivity(binding.root.context, intent, null)
                    }
                }
                delayProgressBar()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }

    fun pageSelected(pageSelected: Page) {
        updatedTextViewDate()
        CoroutineScope(Dispatchers.Main).launch {
            run {
                val pageId = pageSelected.id
                val dollars = DollarClient.service.getDollars(pageId)
                if (dollars.isSuccessful) {
                    binding.recyclerDollar.adapter =
                        dollars.body()?.let { DollarsAdapter(it.data) }
                }
            }
        }
    }

    private fun updatedTextViewDate() {
        binding.updatedTw.text = Util.dateFormatted()
    }
}