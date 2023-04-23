# SmsManagerLibrary

<p align="left">
  <a href="https://jitpack.io/#dorindorsman/SmsManagerLibrary/1.0.5"><img alt="License" src="https://badgen.net/badge/Jitpack/1.0.5/orange?icon=github"/></a>
  <a href="https://github.com/dorindorsman"><img alt="Profile" src="https://badgen.net/badge/Github/dorindorsman/green?icon=github"/></a>
</p>

## Directory for managing new/read/unread/deleted Sms messages.

### When downloading the library, you can access smsviewmodel and pull the relevant collections from there
- New Sms
- Sms whose status has changed
- Deleted Sms

#### Each collection contains a single SMS for now (the last updated SMS)

## Delete SMS

https://user-images.githubusercontent.com/62396222/233801125-481173e4-a967-4623-ace1-dc3dc65143ed.mp4

## READ SMS Status Changed

https://user-images.githubusercontent.com/62396222/233801126-c4cc449a-a362-48bf-abab-2b2d7856c94d.mp4

## New SMS

https://user-images.githubusercontent.com/62396222/233801127-52a709b4-86c2-44ff-bff3-64c4be1ebd4d.mp4


## Download
<a href="https://jitpack.io/#dorindorsman/SmsManagerLibrary/1.0.5"><img alt="License" src="https://badgen.net/badge/Jitpack/1.0.5/orange?icon=github"/></a>

### Gradle

Add the dependency below to your module's `build.gradle` file:
```gradle
dependencies {
    implementation 'com.github.dorindorsman:SmsManagerLibrary:1.0.5'
}
```
Add a repository in your `settings.gradle` file:
```
dependencyResolutionManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
## Usage
There are numbers of permission you need to add to your manifest 

```kotlin
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.READ_PHONE_NUMBERS"/>
<uses-permission android:name="android.permission.READ_CALL_LOG"/>
<uses-permission android:name="android.permission.RECEIVE_SMS"/>
<uses-permission android:name="android.permission.READ_SMS"/>
<uses-permission android:name="android.permission.SEND_SMS"/>
<uses-permission android:name="android.permission.RECEIVE_MMS"/>
<uses-permission android:name="android.permission.READ_CONTACTS"/>
```

```kotlin
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
```

# License
```xml
Designed and developed by 2022 dorindorsman (Dorin Dorsman)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
