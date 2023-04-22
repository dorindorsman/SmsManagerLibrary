package com.example.sms_library

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class SmsViewModel(private val smsManager: SmsManager) : ViewModel() {

    var deletedConversations by mutableStateOf(listOf<SmsModel>())
        private set

    var readConversations  by mutableStateOf(listOf<SmsModel>())
        private set

    var newSmsConversations by mutableStateOf(listOf<SmsModel>())
        private set

    private var onSmsListener: OnSmsListener =
        object : OnSmsListener {
            override fun onSmsReplace() {
                getSms()
            }
        }

    init {
        smsManager.setOnSmsListener(onSmsListener)
        smsManager.start()
        getSms()
    }


    fun getSms() {
        deletedConversations = smsManager.deletedConversations?.toMutableStateList() ?: mutableStateListOf()
        readConversations = smsManager.readConversations?.toMutableStateList() ?: mutableStateListOf()
        newSmsConversations = smsManager.newSmsConversations?.toMutableStateList() ?: mutableStateListOf()
    }

}