package com.example.smsmanagerlibrary

data class SmsModel(
    val threadId : Int,
    val id : Int,
    val address : String,
    val body : String,
    val date : String,
    val type : String, // "1" for inbox "2" for outbox
    val readState : String,  //"0" for have not read sms and "1" for have read sms
    val count:Int
)


