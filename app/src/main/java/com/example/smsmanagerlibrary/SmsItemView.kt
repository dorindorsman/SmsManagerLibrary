package com.example.smsmanagerlibrary

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sms_library.SmsModel

@Composable
fun SmsItemView(
    smsModel: SmsModel,
) {
    Column(
        modifier = Modifier
            .border(width = 1.dp, color = MaterialTheme.colors.primary)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {


        smsModel.type?.let {
            val text = if (it == "1") "Inbox" else if (it == "2") "OutBox" else ""
            Text(
                text = "Type: $text",
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp)
                    .wrapContentWidth(),
                style = MaterialTheme.typography.body1.copy(color = Color.Gray),
                color = Color.Gray,
                maxLines = 1
            )
        }

        smsModel.date?.let {
            Text(
                text = "Date: $it",
                modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                style = MaterialTheme.typography.body1.copy(color = Color.Gray),
                color = Color.Gray,
                maxLines = 1,
            )
        }

        smsModel.address?.let {
            Text(
                text = "Phone: $it",
                modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                style = MaterialTheme.typography.body1.copy(color = Color.Gray, fontWeight = FontWeight.Bold),
                color = Color.Gray,
                maxLines = 1,
            )
        }

        smsModel.body?.let {
            Text(
                text = "MSG: $it",
                modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                style = MaterialTheme.typography.body1.copy(color = Color.Gray, fontWeight = FontWeight.Bold),
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            smsModel.threadId?.let {
                Text(
                    text = "ThreadId: $it",
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    style = MaterialTheme.typography.body1.copy(color = Color.Gray, fontSize = 12.sp),
                    color = Color.Gray,
                    maxLines = 1
                )
            }

            smsModel.id?.let {
                Text(
                    text = "Id: $it",
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    style = MaterialTheme.typography.body1.copy(color = Color.Gray, fontSize = 12.sp),
                    color = Color.Gray,
                    maxLines = 1,
                )
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun NotificationItemViewPreview() {

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
        )
    )
}