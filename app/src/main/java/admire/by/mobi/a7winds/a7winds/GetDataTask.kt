package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContentData.br
import admire.by.mobi.a7winds.a7winds.ContentData.isUpdata
import admire.by.mobi.a7winds.a7winds.ContentData.persons_data
import admire.by.mobi.a7winds.a7winds.ContentData.plans_data
import admire.by.mobi.a7winds.a7winds.ContentData.projects_data
import android.os.AsyncTask
import android.view.View
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import android.net.ConnectivityManager
import android.content.IntentFilter


internal class GetDataTask(
    val context: MainActivity
) : AsyncTask<String, String, String>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String): String {
        var response = ""

        try {
            val url = URL(Server.url + "/get-all-data")

            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 8000
            conn.connectTimeout = 8000
            conn.requestMethod = "GET"
            conn.doInput = true
            conn.doOutput = true

            val os = conn.outputStream
            val writer = BufferedWriter(
                OutputStreamWriter(os, "UTF-8")
            )

            writer.flush()
            writer.close()
            os.close()
            val responseCode = conn.responseCode

            response = if (responseCode == HttpsURLConnection.HTTP_OK) {
                val br = BufferedReader(InputStreamReader(conn.inputStream))
                br.readText()
            } else {
                ""
            }
        } catch (e: Exception) {}

        return response
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)

        val dbController = DBController(context)
        dbController.startDB(dbController.writableDatabase)

        if (result != "") {
            val all_data = JSONObject(result)

            persons_data = all_data.getJSONArray("persons")
            dbController.setDBPersons(persons_data)
            projects_data = all_data.getJSONArray("projects")
            dbController.setDBProjects(projects_data)
            plans_data = all_data.getJSONArray("plans")
            dbController.setDBPlans(plans_data)

            val target = context.findViewById(R.id.main_internet_error) as View
            if(target.visibility == View.VISIBLE) {
                val lp = target.layoutParams
                val startHeight = target.height
                val startPadding = target.paddingTop
                val anim = target.animate().alpha(0f)
                anim.setUpdateListener {
                    val state = 1 - it.animatedValue as Float

                    lp.height = (state * startHeight).toInt()
                    target.layoutParams = lp

                    val newPadding = (state * startPadding).toInt()
                    target.setPadding(newPadding, newPadding, newPadding, newPadding)
                }
                anim.duration = 300
                anim.start()
            }
            if(br != null) {
                context.unregisterReceiver(br)
            }
            isUpdata = true
        } else {
            try {
                persons_data = dbController.getDBPersons()
                projects_data = dbController.getDBProjects()
                plans_data = dbController.getDBPlans()
            } catch (e: Exception) {}

            context.setInternetError(true)

            if(br == null) br = NetworkChecker()
            else context.unregisterReceiver(br)

            val intentFilter = IntentFilter()
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            context.registerReceiver(br, intentFilter)
        }

        context.onDataLoaded()
    }
}