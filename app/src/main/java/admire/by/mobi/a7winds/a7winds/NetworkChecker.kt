package admire.by.mobi.a7winds.a7winds

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.widget.Toast


class NetworkChecker : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(isOnline(context!!)) {
            GetDataTask(
                context as MainActivity
            ).execute()
        }
    }

    private fun isOnline(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            netInfo != null && netInfo.isConnected
        } catch (e: NullPointerException) {
            e.printStackTrace()
            false
        }
    }
}