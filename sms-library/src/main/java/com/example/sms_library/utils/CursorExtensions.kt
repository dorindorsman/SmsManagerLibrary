package com.example.sms_library.utils

import android.database.Cursor
import androidx.core.database.getBlobOrNull
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getFloatOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getShortOrNull
import androidx.core.database.getStringOrNull

fun Cursor.getBlob(columnName: String): ByteArray? {
    return this
        .getColumnIndex(columnName)
        .takeIf { it >= 0 }
        ?.let { index ->
            this.getBlobOrNull(index)
        }
}

fun Cursor.getDouble(columnName: String): Double? {
    return this
        .getColumnIndex(columnName)
        .takeIf { it >= 0 }
        ?.let { index ->
            this.getDoubleOrNull(index)
        }
}

fun Cursor.getFloat(columnName: String): Float? {
    return this
        .getColumnIndex(columnName)
        .takeIf { it >= 0 }
        ?.let { index ->
            this.getFloatOrNull(index)
        }
}

fun Cursor.getInt(columnName: String): Int? {
    return this
        .getColumnIndex(columnName)
        .takeIf { it >= 0 }
        ?.let { index ->
            this.getInt(index)
        }
}

fun Cursor.getLong(columnName: String): Long? {
    return this
        .getColumnIndex(columnName)
        .takeIf { it >= 0 }
        ?.let { index ->
            this.getLongOrNull(index)
        }
}

fun Cursor.getShort(columnName: String): Short? {
    return this
        .getColumnIndex(columnName)
        .takeIf { it >= 0 }
        ?.let { index ->
            this.getShortOrNull(index)
        }
}

fun Cursor.getString(columnName: String): String? {
    return this
        .getColumnIndex(columnName)
        .takeIf { it >= 0 }
        ?.let { index ->
            this.getStringOrNull(index)
        }
}

fun Cursor.dump(): String {
    val stringBuilder= StringBuilder()

    this.columnNames.forEach { columnName ->
        this
            .getColumnIndex(columnName)
            .takeIf { it >= 0 }
            ?.let { index ->
                stringBuilder.append("$columnName : ${this.getString(index)}\n")
            }
    }

    return stringBuilder.toString()
}