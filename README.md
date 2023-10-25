# Spy

## Description

Spy was created to track events within the application, not only by developers, but also by other users of the application: QA, product managers, etc. The library is very easy to use and similar to a standard android console logger. All you need is to send a message, then a push notification will appear, by clicking on which you can see information about the current event.

<p align="center">
  <img src="https://github.com/viacheslav-chugunov/Spy/blob/master/github-assets/spy-preview.gif" height=500 />
</p>

## Setup

Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency:

```
dependencies {
    debugImplementation 'com.github.viacheslav-chugunov:Spy:1.0'
    releaseImplementation 'com.github.viacheslav-chugunov:Spy:1.0-no-op'
}
```

If your application has target sdk 33 or larger, then notification permission will be required. The code below will allow to request this permission.

```kotlin
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    val permissions = arrayOf(android.Manifest.permission.POST_NOTIFICATIONS)
    val requestCode = 1
    requestPermissions(permissions, requestCode)
}
```

## Usage

Sending a message:

```kotlin
val spy = Spy(applicationContext)
spy.success("Success message")
```

If you need to send a message with additional information:

```kotlin
val spy = Spy(applicationContext)
spy.success("Message", SpyMeta("Key1", "Value1"), SpyMeta("Key2", "Value2"))
```

| Spy method | Description |
| --- | --- |
| `success(message: String, vararg meta: SpyMeta)` | Sends success message. |
| `success(message: String, meta: Collection<SpyMeta>)` | Sends success message. |
| `success(model: Any, message: String, vararg meta: SpyMeta)` | Sends success message and parses model as meta fields. |
| `info(message: String, vararg meta: SpyMeta)` | Sends information message. |
| `info(message: String, meta: Collection<SpyMeta>)` | Sends information message. |
| `info(model: Any, message: String, vararg meta: SpyMeta)` | Sends information message and parses model as meta fields. |
| `warning(message: String, vararg meta: SpyMeta)` | Sends warning message. |
| `warning(message: String, meta: Collection<SpyMeta>)` | Sends warning message. |
| `warning(model: Any, message: String, vararg meta: SpyMeta)` | Sends warning message and parses model as meta fields. |
| `error(message: String, vararg meta: SpyMeta)` | Sends error message. |
| `error(message: String, meta: Collection<SpyMeta>)` | Sends error message. |
| `error(error: Throwable, vararg meta: SpyMeta)` | Sends error message. |
| `error(error: Throwable, meta: Collection<SpyMeta>)` | Sends error message. |
| `error(model: Any, message: String, vararg meta: SpyMeta)` | Sends error message and parses model as meta fields. |

You can also create Spy with custom configuration:

```kotlin
val config = SpyConfig.Builder()
    .setInitialMeta(
        SpyMeta("manufacturer", Build.MANUFACTURER),
        SpyMeta("model", Build.MODEL),
        SpyMeta("brand", Build.BRAND)
    )
    .showOpenSpyNotification(true)
    .isNotificationsImportant(true)
    .build()
val spy = Spy(applicationContext, config)
```

| SpyConfig.Builder method | Description |
| --- | --- |
| `setInitialMeta(initialMeta: List<SpyMeta>)` | Specifies the meta to be passed to each subsequent event. |
| `setInitialMeta(vararg initialMeta: SpyMeta)` |  Specifies the meta to be passed to each subsequent event. |
| `showOpenSpyNotification(show: Boolean)` | When you create Spy, you will see a notification that cannot be closed. After clicking on this notification will be open the screen with Spy events. |
| `isNotificationsImportant(isImportant: Boolean)` | The first time Spy is created, a channel will be created for notifications with the given importance. If the channel is marked as important, then you will see each subsequent notification from Spy on top of your UI, otherwise - the notification will be created in the background. |
| `build()` | Creates a final SpyConfig instance. |

## License

```
MIT License

Copyright (c) 2023 Viacheslav Chugunov

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
