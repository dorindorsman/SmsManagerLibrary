package com.example.sms_library

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.provider.Telephony
import android.util.Log

class ReadSmsContentObserver(private val context: Context, handler: Handler) : ContentObserver(handler) {

    companion object {
        const val TAG = "ReadSmsContentObserver"
        const val THREAD_NAME = "ReadSmsThread"
    }

    interface ReadSmsCallback {
        fun onReadSmsEvent(readConversations: Map<Int, SmsModel>)
    }

    private var onReadSmsCallback: ReadSmsCallback? = null
    private var readSmsListenerThread: HandlerThread? = null
    private var readSmsHandler: Handler? = null
    private val readConversationProvider: ReadSmsProvider = ReadSmsProvider(context)
    private val taskCheckForReadMessage = Runnable {
        val readConversations: Map<Int, SmsModel> = readConversationProvider.getReadChangedSmsMmsConversations()
        if (readConversations.isNotEmpty()) {
            Log.d(TAG, readConversations.toString())
            onReadSmsCallback?.onReadSmsEvent(readConversations)
        }
    }

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        Log.d(TAG, "onChange: read")
        readSmsHandler?.let { readSmsHandler ->
            readSmsHandler.removeCallbacks(taskCheckForReadMessage)
            readSmsHandler.post(taskCheckForReadMessage)
        }
    }

    fun start() {
        context.contentResolver
            .registerContentObserver(Telephony.Sms.CONTENT_URI, true, this)
        context.contentResolver
            .registerContentObserver(Telephony.MmsSms.CONTENT_URI, true, this)
        context.contentResolver
            .registerContentObserver(Telephony.Mms.CONTENT_URI, true, this)
        readSmsListenerThread = HandlerThread(THREAD_NAME)
        readSmsListenerThread?.let { readSmsListenerThread ->
            readSmsListenerThread.start()
            readSmsHandler = Handler(readSmsListenerThread.looper)
        }
    }

    fun stop() {
        context.contentResolver.unregisterContentObserver(this)
        readSmsListenerThread?.let { readSmsListenerThread ->
            readSmsListenerThread.quit()
        }
        readSmsListenerThread = null
        readSmsHandler = null
    }

    fun setOnReadSmsCallback(readSmsCallback: ReadSmsCallback) {
        onReadSmsCallback = readSmsCallback
    }

}
