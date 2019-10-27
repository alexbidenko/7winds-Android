package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContentData.msInTime
import admire.by.mobi.a7winds.a7winds.ContentData.projects_data
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.json.JSONObject

class PlanBlock(val fragment: PageFragment, val data: JSONObject) {

    fun create(): View {
        val view = fragment.layoutInflater.inflate(R.layout.plan_block, null)

        val image = view.findViewById(R.id.plan_image) as ImageView

        view.findViewById<TextView>(R.id.plan_time).text = msInTime(data.getLong("time"))

        var project: JSONObject? = null
        for(i in 0 until projects_data.length()) {
            if(data.getInt("project_id") == projects_data.getJSONObject(i).getInt("id")) {
                project = projects_data.getJSONObject(i)
            }
        }

        if(project != null) {
            view.setOnClickListener {
                for(i in 0 until projects_data.length()) {
                    if(data.getInt("project_id") == projects_data.getJSONObject(i).getInt("id")) {
                        fragment.pageData.projectFragment.index = i
                        fragment.setActive(fragment.pageData.projectFragment, image)
                    }
                }
            }
            view.findViewById<TextView>(R.id.plan_title).text = project.getString("title")

            ContentData.getImage(
                project.getString("avatar"),
                image,
                "project_" + project.getInt("id").toString()
            )
        }

        return view
    }
}