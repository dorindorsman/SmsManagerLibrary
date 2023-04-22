package com.example.smsmanagerlibrary

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class SmsViewModel(private val smsManager: SmsManager) : ViewModel() {

    var deletedConversations = mutableStateListOf<SmsModel>()
        private set

    var readConversations = mutableStateListOf<SmsModel>()
        private set

    var newSmsConversations = mutableStateListOf<SmsModel>()
        private set

    private var onSmsListener: OnSmsListener =
        object : OnSmsListener {
            override fun onSmsReplace() {
                getSms()
            }
        }

    init {
        smsManager.setOnSmsListener(onSmsListener)
        getSms()
    }


    fun getSms() {
        deletedConversations = smsManager.deletedConversations?.toMutableStateList() ?: mutableStateListOf()
        readConversations = smsManager.readConversations?.toMutableStateList() ?: mutableStateListOf()
        newSmsConversations = smsManager.newSmsConversations?.toMutableStateList() ?: mutableStateListOf()
    }

}