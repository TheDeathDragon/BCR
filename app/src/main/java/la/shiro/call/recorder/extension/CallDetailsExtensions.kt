package la.shiro.call.recorder.extension

import android.telecom.Call
import la.shiro.call.recorder.output.PhoneNumber

val Call.Details.phoneNumber: PhoneNumber?
    get() = handle?.phoneNumber?.let {
        try {
            PhoneNumber(it)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
