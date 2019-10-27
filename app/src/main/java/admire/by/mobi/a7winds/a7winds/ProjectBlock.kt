package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContentData.msInDate
import admire.by.mobi.a7winds.a7winds.ContentData.projects_data
import admire.by.mobi.a7winds.a7winds.ContentData.setIcon
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class ProjectBlock(val fragment: PageFragment, val index: Int, val is_with_tile: Boolean = false) {

    fun create(): View {
        val data = projects_data.getJSONObject(index)

        val view = fragment.layoutInflater.inflate(R.layout.project_block, null)

        val image = view.findViewById(R.id.project_image) as ImageView

        view.setOnClickListener {
            fragment.pageData.projectFragment.index = index
            fragment.setActive(fragment.pageData.projectFragment, image)
        }

        view.findViewById<TextView>(R.id.project_title).text = data.getString("title")
        view.findViewById<TextView>(R.id.project_start).text = msInDate(data.getLong("start"))
        view.findViewById<TextView>(R.id.project_end).text = msInDate(data.getLong("end"))

        val scaleTransform = fragment.resources.displayMetrics.density

        val icons = view.findViewById<LinearLayout>(R.id.project_icons)
        for(i in 0 until data.getJSONArray("platform").length()) {
            val icon = ImageView(fragment.context)
            icon.scaleType = ImageView.ScaleType.CENTER_CROP
            icon.layoutParams = LinearLayout.LayoutParams(Math.round(24 * scaleTransform), Math.round(24 * scaleTransform))
            setIcon(icon, data.getJSONArray("platform").getString(i))
            icons.addView(icon)
        }

        ContentData.getImage(
            data.getString("avatar"),
            image,
            "project_" + data.getInt("id").toString()
        )

        if(is_with_tile) {
            view.background = fragment.resources.getDrawable(R.drawable.yellow_tile)
            val lp = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(32, 8, 32, 8)
            view.layoutParams = lp

            view.findViewById<TextView>(R.id.project_title).setTextColor(Color.rgb(0, 0, 0))
            view.findViewById<TextView>(R.id.project_start).setTextColor(Color.rgb(0, 0, 0))
            view.findViewById<TextView>(R.id.project_end).setTextColor(Color.rgb(0, 0, 0))
        }

        return view
    }
}