package com.example.sms_library

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.provider.Telephony
import android.util.Log

class DeleteSmsContentObserver(private val context: Context, handler: Handler) : ContentObserver(handler) {

    companion object {
        const val TAG = "DeleteMessageContentObserver"
        const val THREAD_NAME = "DeleteListenerThread"
    }

    interface DeleteSmsCallback {
        fun onDeleteSmsEvent(deletedConversations: Map<Int, com.example.sms_library.SmsModel>)
    }

    private var onDeleteSmsCallback: DeleteSmsCallback? = null
    private var deleteSmsListenerThread: HandlerThread? = null
    private var deleteSmsHandler: Handler? = null

    private val deletedConversationProvider: DeletedSmsProvider = DeletedSmsProvider(context)
    private val taskCheckForDeleteMessage = Runnable {
        val deletedConversations: Map<Int, com.example.sms_library.SmsModel> = deletedConversationProvider.getDeletedConversations()
        if (deletedConversations.isNotEmpty()) {
            Log.d(TAG, deletedConversations.toString())
            onDeleteSmsCallback?.onDeleteSmsEvent(deletedConversations)
        }
    }

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        Log.d(TAG, "onChange: delete")
        deleteSmsHandler?.let { deleteMessageHandler ->
            deleteMessageHandler.removeCallbacks(taskCheckForDeleteMessage)
            deleteMessageHandler.post(taskCheckForDeleteMessage)
        }
    }

    fun start() {
        context.contentResolver
            .registerContentObserver(Telephony.Sms.CONTENT_URI, true, this)
        context.contentResolver
            .registerContentObserver(Telephony.MmsSms.CONTENT_URI, true, this)
        context.contentResolver
            .registerContentObserver(Telephony.Mms.CONTENT_URI, true, this)
        deleteSmsListenerThread = HandlerThread(THREAD_NAME)
        deleteSmsListenerThread?.let { deleteMessageListenerThread ->
            deleteMessageListenerThread.start()
            deleteSmsHandler = Handler(deleteMessageListenerThread.looper)
        }
    }

    fun stop() {
        context.contentResolver.unregisterContentObserver(this)
        deleteSmsListenerThread?.let { deleteMessageListenerThread ->
            deleteMessageListenerThread.quit()
        }
        deleteSmsListenerThread = null
        deleteSmsHandler = null
    }

    fun setOnDeleteSmsCallBack(deleteSmsCallback: DeleteSmsCallback) {
        onDeleteSmsCallback = deleteSmsCallback
    }
}


