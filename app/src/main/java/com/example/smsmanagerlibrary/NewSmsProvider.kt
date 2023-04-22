package com.example.smsmanagerlibrary

import android.content.Context
import android.provider.Telephony
import kotlin.collections.Map

class NewSmsProvider(private val context: Context) {

    private var newSmsMmsConversations = emptyMap<Int, SmsModel>()
    private var smsMapProvider: SmsMapProvider = SmsMapProvider


    init {
        newSmsMmsConversations = smsMapProvider
            .provide(
                context = context,
                uri = Telephony.MmsSms.CONTENT_CONVERSATIONS_URI,
                keyName = Telephony.Mms.THREAD_ID,
                projection = arrayOf("thread_id", "COUNT(thread_id)", "_id", "address", "body", "date", "type", "read"),
                selection = null
            )
    }

    fun getNewSmsMmsConversations(): Map<Int, SmsModel> {
        //allSmsMmsConversations : < thread id, SmsModel >
        val currentSmsMmsConversation = smsMapProvider
            .provide(
                context = context,
                uri = Telephony.MmsSms.CONTENT_CONVERSATIONS_URI,
                keyName = Telephony.Sms.THREAD_ID,
                projection =  arrayOf("thread_id","COUNT(thread_id)", "_id", "address", "body", "date", "type", "read"),
                selection = null
            )
        //Filter new sms.
        val changedNewSmsMmsConversations = currentSmsMmsConversation.filter {
            (newSmsMmsConversations[it.key]?.id != it.value.id )
        }
        newSmsMmsConversations = currentSmsMmsConversation
        return changedNewSmsMmsConversations
    }

}