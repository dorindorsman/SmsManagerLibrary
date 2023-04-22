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

@Composable
fun SmsScreen(
    smsViewModel: SmsViewModel,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Delete:")
            LazyColumn() {
                items(smsViewModel.deletedConversations) { smsModel ->
                    SmsItemView(smsModel = smsModel)
                }
            }

            Text(text = "Read:")
            LazyColumn() {
                items(smsViewModel.readConversations) { smsModel ->
                    SmsItemView(smsModel = smsModel)
                }
            }

            Text(text = "New:")
            LazyColumn() {
                items(smsViewModel.newSmsConversations) { smsModel ->
                    SmsItemView(smsModel = smsModel)
                }
            }
        }
    }
}