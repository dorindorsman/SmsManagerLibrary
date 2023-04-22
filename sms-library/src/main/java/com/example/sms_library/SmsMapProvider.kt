package com.example.sms_library

import android.content.Context
import android.net.Uri
import java.util.*

object SmsMapProvider{

    private val calendar: Calendar = Calendar.getInstance()

    fun provide(context:Context, uri: Uri, keyName: String?, projection:Array<String>?, selection:String?): MutableMap<Int, SmsModel> {
        val conversations = mutableMapOf<Int, SmsModel>()
        context.contentResolver.query(
            uri,
            projection,
            selection,
            null,
            null
        ).use { cursor ->
            cursor
                ?.takeIf { it.moveToFirst() }
                ?.let { cursor ->
                    do {
                        val key = cursor.getInt(cursor.getColumnIndexOrThrow(cursor.columnNames[0]))
                        val date = cursor.getLong(cursor.getColumnIndexOrThrow(cursor.columnNames[5]))
                        calendar.timeInMillis = date
                        val formatted: String = calendar.time.toString()
                        val sms = SmsModel(
                            threadId = key,
                            count = cursor.getInt(cursor.getColumnIndexOrThrow( cursor.columnNames[1])),
                            id = cursor.getInt(cursor.getColumnIndexOrThrow(cursor.columnNames[2])),
                            address = cursor.getString(cursor.getColumnIndexOrThrow(cursor.columnNames[3])) ?: "",
                            body = cursor.getString(cursor.getColumnIndexOrThrow(cursor.columnNames[4])) ?: "",
                            date = formatted,
                            type = cursor.getString(cursor.getColumnIndexOrThrow(cursor.columnNames[6])) ?: "",
                            readState = cursor.getString(cursor.getColumnIndexOrThrow(cursor.columnNames[7])) ?: ""
                        )
                       conversations[key] =  sms
                    } while (cursor.moveToNext())
                }
        }
        return conversations
    }
}