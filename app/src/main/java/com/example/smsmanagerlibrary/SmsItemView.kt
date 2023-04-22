package com.example.smsmanagerlibrary

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SmsItemView(
    smsModel: SmsModel,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        NotificationTopDetails(smsModel = smsModel)

        NotificationBottomDetails(smsModel = smsModel)

    }
}


@Composable
fun NotificationTopDetails(smsModel: SmsModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {

        smsModel.type?.let {
            Text(
                text = it,
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp)
                    .wrapContentWidth(),
                style = MaterialTheme.typography.body1.copy(color = Color.Gray, fontWeight = FontWeight.Bold),
                color = Color.Gray,
                maxLines = 2
            )
        }

        smsModel.threadId?.let {
            Text(
                text = it.toString(),
                modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                style = MaterialTheme.typography.body1.copy(color = Color.Gray),
                color = Color.Gray,
                maxLines = 2
            )
        }
    }
}

@Composable
fun NotificationBottomDetails(smsModel: SmsModel) {

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {


        NotificationContent(smsModel = smsModel)

    }
}

@Composable
fun NotificationContent(smsModel: SmsModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
    ) {
        smsModel.address?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.body1.copy(color = Color.Gray),
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        smsModel.body?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.body1.copy(color = Color.Gray),
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        smsModel.id?.let {
            Text(
                text = it.toString(),
                style = MaterialTheme.typography.body1.copy(color = Color.Gray, fontSize = 9.sp),
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NotificationItemViewPreview() {

    val context = LocalContext.current

    SmsItemView(
        SmsModel(
            threadId = 1234,
            id = 123456789,
            date = "30.12.95",
            address = "972",
            body = "hi",
            type = "inbox",
            count = 0,
            readState = "0"
        ))
}