package com.example.smsmanagerlibrary

import android.content.Context
import android.provider.Telephony
import kotlin.collections.Map

class DeletedSmsProvider(private val context: Context) {

    private var smsConversations = emptyMap<Int, SmsModel>()
    private var mmsConversations = emptyMap<Int, SmsModel>()
    private var smsMapProvider: SmsMapProvider = SmsMapProvider


    init {
        smsConversations = smsMapProvider
            .provide(
                context = context,
                uri = Telephony.Sms.CONTENT_URI,
                keyName = Telephony.Sms.THREAD_ID,
                projection =  arrayOf("thread_id", "COUNT(thread_id)", "_id", "address", "body", "date", "type", "read"),
                selection = "thread_id IS NOT NULL) GROUP BY (thread_id"
            )
        mmsConversations = smsMapProvider
            .provide(
                context = context,
                uri = Telephony.Mms.CONTENT_URI,
                keyName = Telephony.Mms.THREAD_ID,
                projection =  arrayOf("thread_id", "COUNT(thread_id)", "_id", "creator", "retr_txt", "date", "msg_box", "read"),
                selection = "thread_id IS NOT NULL) GROUP BY (thread_id"
            )
    }

    fun getDeletedConversations(): Map<Int, SmsModel> {
        return getDeleteSmsConversations() + getDeleteMmsConversations()
    }

    private fun getDeleteSmsConversations(): Map<Int, SmsModel> {
        //allSmsConversations : < thread id, SmsModel >
        val currentSmsConversation = smsMapProvider
            .provide(
                context = context,
                uri = Telephony.Sms.CONTENT_URI,
                keyName = Telephony.Sms.THREAD_ID,
                projection =  arrayOf("thread_id", "COUNT(thread_id)", "_id", "address", "body", "date", "type", "read"),
                selection = "thread_id IS NOT NULL) GROUP BY (thread_id"
            )
        //Filter conversations that don't exist in the previous list and where messages count is smaller.
        val deletedSmsConversations = smsConversations.filter {
            (!currentSmsConversation.containsKey(it.key)) || ((currentSmsConversation[it.key]?.count ?: 0) < it.value.count)
        }
        smsConversations = currentSmsConversation
        return deletedSmsConversations
    }

    private fun getDeleteMmsConversations(): Map<Int, SmsModel> {
        //allMmsConversations : < thread id, SmsModel >
        val currentMmsConversation = smsMapProvider
            .provide(
                context = context,
                uri = Telephony.Mms.CONTENT_URI,
                keyName = Telephony.Mms.THREAD_ID,
                projection =  arrayOf("thread_id", "COUNT(thread_id)", "_id", "creator", "retr_txt", "date", "msg_box", "read"),
                selection = "thread_id IS NOT NULL) GROUP BY (thread_id"
            )
        //Filter conversations that don't exist in the previous list and where messages count is smaller.
        val deletedMmsConversations = mmsConversations.filter {
            (!currentMmsConversation.containsKey(it.key)) || ((currentMmsConversation[it.key]?.count ?: 0) < it.value.count)
        }
        mmsConversations = currentMmsConversation
        return deletedMmsConversations
    }
}