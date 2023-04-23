package com.example.smsmanagerlibrary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sms_library.SmsViewModel

@Composable
fun SmsScreen(
    smsViewModel: SmsViewModel,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "Delete Sms: \nSize:${smsViewModel.deletedConversations.size}")
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(smsViewModel.deletedConversations) { smsModel ->
                    SmsItemView(smsModel = smsModel)
                }
            }

            Text(text = "Read Status Changed Sms: \nSize:${smsViewModel.readConversations.size}")
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(smsViewModel.readConversations) { smsModel ->
                    SmsItemView(smsModel = smsModel)
                }
            }

            Text(text = "New Sms: \nSize:${smsViewModel.newSmsConversations.size}")
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(smsViewModel.newSmsConversations) { smsModel ->
                    SmsItemView(smsModel = smsModel)
                }
            }
        }
    }
}