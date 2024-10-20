package com.churickoid.characterchat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.churickoid.characterchat.data.ChatApiService
import com.churickoid.characterchat.data.ChatRepository
import com.churickoid.characterchat.domain.entity.Message
import com.churickoid.characterchat.domain.entity.MessageOwner
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val chatRepository: ChatRepository) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state : StateFlow<ChatState>
        get() = _state

    fun sendMessage(text: String){
        viewModelScope.launch {
            val chat : MutableList<Message> = _state.value.messageList.toMutableList()
             Message(text, MessageOwner.USER).also { chat.add(it) }
            _state.value = _state.value.copy(messageList = chat, countMessage = _state.value.countMessage + 1)
            chatRepository.getAnswerFromBot(text).also { chat.add(it)}
            _state.value = _state.value.copy(messageList = chat, countMessage = _state.value.countMessage + 1)

        }
    }

    data class ChatState(
        val messageList: List<Message> = listOf(),
        val countMessage: Int = 0
    )
}