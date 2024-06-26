package com.aglia.doctorchatbot.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aglia.doctorchatbot.databinding.ActivityMainBinding
import com.aglia.doctorchatbot.data.Message
import com.aglia.doctorchatbot.utils.Constants.RECEIVE_ID
import com.aglia.doctorchatbot.utils.Constants.SEND_ID
import com.aglia.doctorchatbot.utils.BotResponse
import com.aglia.doctorchatbot.utils.Constants.OPEN_MAPS
import com.aglia.doctorchatbot.utils.Time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MessagingAdapter
    private var messagesList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView()
        configureBotResponseCallbacks()
        customBotMessage("Buongiorno! Sono Dott. ChatBot. Come posso aiutarti oggi? Scrivi 'Nuovo Test' per iniziare un testo o scrivi 'Cerca ospedale' e ti mostrerò l'ospedale più vicino.")
        clickEvents()
    }

    private fun clickEvents() {
        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        binding.etMessage.setOnEditorActionListener { _, id, event ->
            if (id == EditorInfo.IME_ACTION_SEND || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                sendMessage()
                true
            } else {
                false
            }
        }

        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSi.setOnClickListener {
            handleTestResponse("Si")
        }

        binding.buttonNo.setOnClickListener {
            handleTestResponse("No")
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = binding.etMessage.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            messagesList.add(Message(message, SEND_ID, timeStamp))
            binding.etMessage.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun handleTestResponse(response: String) {
        val timeStamp = Time.timeStamp()
        messagesList.add(Message(response, SEND_ID, timeStamp))
        adapter.insertMessage(Message(response, SEND_ID, timeStamp))
        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
        botResponse(response)
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                if (response == OPEN_MAPS) {
                    val site = Intent(Intent.ACTION_VIEW)
                    site.data = Uri.parse("https://www.google.it/maps/search/ospedale/")
                    startActivity(site)
                }
            }
        }
    }

    private fun configureBotResponseCallbacks() {
        BotResponse.showButtonsCallback = {
            binding.buttonSi.visibility = View.VISIBLE
            binding.buttonNo.visibility = View.VISIBLE
        }

        BotResponse.hideButtonsCallback = {
            binding.buttonSi.visibility = View.GONE
            binding.buttonNo.visibility = View.GONE
        }
    }

    private fun customBotMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}