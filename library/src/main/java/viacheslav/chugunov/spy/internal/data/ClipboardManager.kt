package viacheslav.chugunov.spy.internal.data

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE

class ClipboardManager(private val context: Context) {

    fun copy(text: String) {
        val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Spy", text)
        clipboard.setPrimaryClip(clip)
    }

}