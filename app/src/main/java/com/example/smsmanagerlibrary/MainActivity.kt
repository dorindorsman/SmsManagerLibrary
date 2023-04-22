package com.example.smsmanagerlibrary

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_library.SmsViewModel
import com.example.sms_library.SmsViewModelFactory
import com.example.smsmanagerlibrary.ui.theme.SmsManagerLibraryTheme
import com.google.accompanist.permissions.*


class MainActivity : ComponentActivity() {

    private lateinit var smsViewModel: SmsViewModel

    private val neededPermissions = listOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.RECEIVE_MMS,
        Manifest.permission.READ_PHONE_NUMBERS,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS
    )

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val smsViewModelFactory = SmsViewModelFactory(applicationContext)

            SmsManagerLibraryTheme {

                val permissionsState = rememberMultiplePermissionsState(
                    permissions = neededPermissions
                )

                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(
                    key1 = lifecycleOwner,
                    effect = {
                        val observer = LifecycleEventObserver { _, event ->
                            if (event == Lifecycle.Event.ON_START) {
                                permissionsState.launchMultiplePermissionRequest()
                            }
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)

                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }
                )

                if (permissionsState.allPermissionsGranted) {
                    smsViewModel = viewModel(factory = smsViewModelFactory)
                    SmsScreen(smsViewModel)
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SmsManagerLibraryTheme {
    }
}