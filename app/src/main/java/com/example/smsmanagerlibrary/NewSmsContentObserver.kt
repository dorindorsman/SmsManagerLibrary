package com.example.smsmanagerlibrary

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.provider.Telephony
import android.util.Log

class NewSmsContentObserver(private val context: Context, handler: Handler) : ContentObserver(handler) {

    companion object {
        const val TAG = "NewSmsContentObserver"
        const val THREAD_NAME = "NewSmsThread"
    }

    interface NewSmsCallback {
        fun onNewSmsEvent(newSmsConversations: Map<Int, SmsModel>)
    }

    private var onNewSmsCallback: NewSmsCallback? = null
    private var newSmsListenerThread: HandlerThread? = null
    private var newSmsHandler: Handler? = null
    private val newSmsConversationProvider: NewSmsProvider = NewSmsProvider(context)
    private val taskCheckForNewMessage = Runnable {
        val newSmsConversations: Map<Int, SmsModel> = newSmsConversationProvider.getNewSmsMmsConversations()
        Log.d("dorin", newSmsConversations.toString())
        if (newSmsConversations.isNotEmpty()){
            Log.d(TAG, newSmsConversations.toString())
            onNewSmsCallback?.onNewSmsEvent(newSmsConversations)
        }
    }

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        Log.d(TAG, "onChange: new sms")
        newSmsHandler?.let { newSmsHandler ->
            newSmsHandler.removeCallbacks(taskCheckForNewMessage)
            newSmsHandler.post(taskCheckForNewMessage)
        }
    }

    fun start() {
        context.contentResolver
            .registerContentObserver(Telephony.Sms.CONTENT_URI, true, this)
        context.contentResolver
            .registerContentObserver(Telephony.MmsSms.CONTENT_URI, true, this)
        context.contentResolver
            .registerContentObserver(Telephony.Mms.CONTENT_URI, true, this)
        newSmsListenerThread = HandlerThread(THREAD_NAME)
        newSmsListenerThread?.let { newSmsListenerThread ->
            newSmsListenerThread.start()
            newSmsHandler = Handler(newSmsListenerThread.looper)
        }
    }

    fun stop() {
        context.contentResolver.unregisterContentObserver(this)
        newSmsListenerThread?.let { newSmsListenerThread ->
            newSmsListenerThread.quit()
        }
        newSmsListenerThread = null
        newSmsHandler = null
    }

}
