package com.example.smsmanagerlibrary

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log

class SmsManager(private val context: Context) {

    companion object {
        const val TAG = "SmsManager"
    }

    private var newSmsContentObserver: NewSmsContentObserver? = null
    private var deleteSmsContentObserver: DeleteSmsContentObserver? = null
    private var readSmsContentObserver: ReadSmsContentObserver? = null

    var deletedConversations : List<SmsModel>? = null
    var readConversations : List<SmsModel>? = null
    var newSmsConversations : List<SmsModel>? = null

    private var deleteSmsCallback: DeleteSmsContentObserver.DeleteSmsCallback =
        object : DeleteSmsContentObserver.DeleteSmsCallback {
            override fun onDeleteSmsEvent(conversations: Map<Int, SmsModel>) {
                Log.d(TAG, conversations.toString())
                Log.d("dorin", conversations.toString())
                deletedConversations = conversations.values.toMutableList()
                onSmsListener?.onSmsReplace()
            }

        }

    private var readSmsCallback: ReadSmsContentObserver.ReadSmsCallback =
        object : ReadSmsContentObserver.ReadSmsCallback {
            override fun onReadSmsEvent(conversations: Map<Int, SmsModel>) {
                Log.d(TAG, conversations.toString())
                Log.d("dorin", conversations.toString())
                readConversations = conversations.values.toMutableList()
                onSmsListener?.onSmsReplace()
            }
        }

    private var newSmsCallback: NewSmsContentObserver.NewSmsCallback =
        object : NewSmsContentObserver.NewSmsCallback {
            override fun onNewSmsEvent(conversations: Map<Int, SmsModel>) {
                Log.d(TAG, conversations.toString())
                Log.d("dorin", conversations.toString())
                newSmsConversations = conversations.values.toMutableList()
                onSmsListener?.onSmsReplace()
            }
        }

    private var onSmsListener: OnSmsListener? = null

    fun setOnSmsListener(listener: OnSmsListener) {
        this.onSmsListener = listener
    }

    fun start() {
        deleteSmsContentObserver = DeleteSmsContentObserver(context = context, handler = Handler(Looper.getMainLooper()))
        deleteSmsContentObserver?.setOnDeleteSmsCallBack(deleteSmsCallback)
        deleteSmsContentObserver?.start()

        readSmsContentObserver = ReadSmsContentObserver(context = context, handler = Handler(Looper.getMainLooper()))
        readSmsContentObserver?.setOnReadSmsCallback(readSmsCallback)
        readSmsContentObserver?.start()

        newSmsContentObserver = NewSmsContentObserver(context = context, handler = Handler(Looper.getMainLooper()))
        newSmsContentObserver?.start()
    }

    fun stop() {
        deleteSmsContentObserver?.stop()
        deleteSmsContentObserver = null

        readSmsContentObserver?.stop()
        readSmsContentObserver = null

        newSmsContentObserver?.stop()
        newSmsContentObserver = null
    }

//    fun onDeleteSmsEvent(deletedConversations: Map<Int, SmsModel>) {
//        Log.d(TAG, deletedConversations.toString())
//        Log.d("dorin", deletedConversations.toString())
//        this.deletedConversations = deletedConversations
//        onSmsListener?.onSmsReplace()
//
//    }
//
//    fun onReadSmsEvent(readConversations: Map<Int, SmsModel>) {
//        Log.d(TAG, readConversations.toString())
//        Log.d("dorin", readConversations.toString())
//        this.readConversations = readConversations
//        onSmsListener?.onSmsReplace()
//    }

//    fun onNewSmsEvent(newSmsConversations: Map<Int, SmsModel>) {
//        Log.d(TAG, newSmsConversations.toString())
//        Log.d("dorin", newSmsConversations.toString())
//        this.newSmsConversations = newSmsConversations
//        onSmsListener?.onSmsReplace()
//    }
}