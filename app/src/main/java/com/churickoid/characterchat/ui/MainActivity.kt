package com.churickoid.characterchat.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.churickoid.characterchat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel by viewModels<MainViewModel>()
    private val binding : ActivityMainBinding by lazy {
         ActivityMainBinding.inflate(layoutInflater)
    }

    private val chatAdapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChat.layoutManager = layoutManager
        binding.recyclerViewChat.adapter = chatAdapter

        lifecycleScope.launch {
            viewModel.state.collect { newState ->
                chatAdapter.dataSet = newState.messageList
                chatAdapter.notifyDataSetChanged()
            }
        }


        binding.editTextChat.setOnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.sendMessage(binding.editTextChat.text.toString())
                binding.editTextChat.text.clear()
                true
            } else {
                false
            }
        }
        binding.imageButtonSend.setOnClickListener {
            viewModel.sendMessage(binding.editTextChat.text.toString())
            binding.editTextChat.text.clear()
        }
    }
}