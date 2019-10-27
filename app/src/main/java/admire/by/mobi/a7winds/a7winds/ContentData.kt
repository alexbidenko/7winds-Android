package admire.by.mobi.a7winds.a7winds

import android.content.BroadcastReceiver
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import org.json.JSONArray
import java.util.*

object ContentData {

    var isUpdata = false
    var br: BroadcastReceiver? = null

    var persons_data = JSONArray()
    var projects_data = JSONArray()
    var plans_data = JSONArray()

    val images_map = mutableMapOf<String, Bitmap>()

    val fragmentsMap = listOf(
        PageDataObject(AllPersonsFragment()),
        PageDataObject(AllProjectsFragment()),
        PageDataObject(AllPlansFragment()),
        PageDataObject(AllTraineeFragment())
    )

    fun getImage(url: String, imageView: ImageView, key: String) {
        val url_image = if(url.startsWith("http")) url
            else Server.url + "/files/" + url

        if(images_map.containsKey(key)) {
            imageView.setImageBitmap(images_map[key])
        } else if(url != "") {
            Glide.with(imageView)
                .asBitmap()
                .load(url_image)
                .centerCrop()
                .circleCrop()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
                        images_map[key] = resource

                        imageView.setImageBitmap(resource)
                    }
                })
        }
    }

    fun msInDate(time: Long): String {
        val date = Calendar.getInstance()
        date.time = Date(time)
        val month = date.get(Calendar.MONTH) + 1
        return date.get(Calendar.DAY_OF_MONTH).toString() + "." +
                (if (month < 10) { "0$month" } else { month.toString() }) + "." +
                date.get(Calendar.YEAR).toString()
    }

    fun msInTime(time: Long): String {
        val date = Calendar.getInstance()
        date.time = Date(time)
        val minute = date.get(Calendar.MINUTE)
        return date.get(Calendar.HOUR_OF_DAY).toString() + ":" + (if (minute < 10) { "0$minute" } else { minute.toString() })
    }

    fun setIcon(icon: ImageView, pr: String) {
        when(pr) {
            "ios_swift" -> icon.setImageResource(R.drawable.ios_swift)
            "ios_objective_c" -> icon.setImageResource(R.drawable.ios_objective_c)
            "android_java" -> icon.setImageResource(R.drawable.android_java)
            "android_kotlin" -> icon.setImageResource(R.drawable.android_kotlin)
            "unity" -> icon.setImageResource(R.drawable.unity)
            "scetch" -> icon.setImageResource(R.drawable.scetch)
            "figma" -> icon.setImageResource(R.drawable.figma)
            "adobe_illustrator" -> icon.setImageResource(R.drawable.adobe_illustrator)
            "adobe_photoshop" -> icon.setImageResource(R.drawable.adobe_photoshop)
            "tree_ds_max" -> icon.setImageResource(R.drawable.tree_ds_max)
            "autodesk_maya" -> icon.setImageResource(R.drawable.autodesk_maya)
            "blender" -> icon.setImageResource(R.drawable.blender)
            "substance" -> icon.setImageResource(R.drawable.substance)
            "adobe" -> icon.setImageResource(R.drawable.adobe)
            "server_java" -> icon.setImageResource(R.drawable.server_java)
            "server_kotlin" -> icon.setImageResource(R.drawable.server_kotlin)
            "server_php" -> icon.setImageResource(R.drawable.server_php)
        }
    }

    fun sortJSONByAlpha(jsonArray: JSONArray): JSONArray {
        if(jsonArray.length() > 1) {
            do {
                var is_correct = true
                for (i in 1 until jsonArray.length()) {
                    if (jsonArray.getJSONObject(i).getString("name") <
                        jsonArray.getJSONObject(i - 1).getString("name")) {
                        val cashObject = jsonArray.getJSONObject(i)
                        jsonArray.put(i, jsonArray.getJSONObject(i - 1))
                        jsonArray.put(i - 1, cashObject)
                        is_correct = false
                    }
                }
            } while (!is_correct)
        }
        return jsonArray
    }
}