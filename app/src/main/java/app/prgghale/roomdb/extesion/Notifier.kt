package app.prgghale.roomdb.extesion

import android.content.Context
import android.widget.Toast


fun Context.toastS(message: String?) {
    Toast.makeText(this, message.orEmpty(), Toast.LENGTH_SHORT).show()
}

fun Context.toastL(message: String?) {
    Toast.makeText(this, message.orEmpty(), Toast.LENGTH_LONG).show()
}