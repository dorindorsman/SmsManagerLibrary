package com.example.smsmanagerlibrary

import android.content.Context
import android.provider.Telephony
import kotlin.collections.Map

class ReadSmsProvider(private val context: Context) {

    private var smsMmsConversations = emptyMap<Int, SmsModel>()
    private var smsMapProvider: SmsMapProvider = SmsMapProvider


    init {
        smsMmsConversations = smsMapProvider
            .provide(
                context = context,
                uri = Telephony.MmsSms.CONTENT_CONVERSATIONS_URI,
                keyName = Telephony.Mms.THREAD_ID,
                projection = arrayOf("thread_id", "COUNT(thread_id)", "_id", "address", "body", "date", "type", "read"),
                selection = null
            )
    }

    fun getReadChangedSmsMmsConversations(): Map<Int, SmsModel> {
        //allSmsMmsConversations : < thread id, SmsModel >
        val currentSmsMmsConversation = smsMapProvider
            .provide(
                context = context,
                uri = Telephony.MmsSms.CONTENT_CONVERSATIONS_URI,
                keyName = Telephony.Sms.THREAD_ID,
                projection =  arrayOf("thread_id","COUNT(thread_id)", "_id", "address", "body", "date", "type", "read"),
                selection = null
            )
        //Filter conversations that there status changed.
        val changedStatusMmsConversations = smsMmsConversations.filter {
            (!currentSmsMmsConversation.containsKey(it.key)) || (currentSmsMmsConversation[it.key]?.readState != it.value.readState)
        }
        smsMmsConversations = currentSmsMmsConversation
        return changedStatusMmsConversations
    }

}