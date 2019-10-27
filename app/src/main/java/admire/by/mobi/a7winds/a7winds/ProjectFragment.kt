package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContentData.msInDate
import admire.by.mobi.a7winds.a7winds.ContentData.persons_data
import admire.by.mobi.a7winds.a7winds.ContentData.projects_data
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ProjectFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    var index = 0

    lateinit var title: TextView
    lateinit var date: TextView
    lateinit var image: ImageView
    lateinit var layout: LinearLayout
    lateinit var text_progress: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.project_back).setOnClickListener {
            (parentFragment as PageFragment).backActivity()
        }

        title = view.findViewById(R.id.page_project_title)
        date = view.findViewById(R.id.page_project_date)
        image = view.findViewById(R.id.page_project_image)
        layout = view.findViewById(R.id.page_project_layout)
        text_progress = view.findViewById(R.id.page_project_text_progress)

        onDraw()
    }

    @SuppressLint("SetTextI18n")
    fun onDraw() {
        val data = projects_data.getJSONObject(index)

        title.text =
            data.getString("title")
        date.text = msInDate(
            data.getLong("start")
        ) + " - " + msInDate(
            data.getLong("end")
        )
        image.setImageResource(R.drawable.logo)
        layout.removeAllViews()
        for(i in 0 until data.getJSONArray("workers").length()) {
            for(j in 0 until persons_data.length()) {
                if(data.getJSONArray("workers").getInt(i) == persons_data.getJSONObject(j).getInt("id"))
                    layout.addView(PersonBlock(parentFragment as PageFragment, i, true).create())
            }
        }
        try {
            var calc_progress = Math.round(
                ((System.currentTimeMillis() - data.getLong("start")) /
                        (data.getLong("end") - data.getLong("start")) * 100).toDouble()
            )

            if(calc_progress > 100) {
                calc_progress = 100
            } else if(calc_progress < 0) {
                calc_progress = 0
            }

            text_progress.text = "$calc_progress%"
        } catch (e: Exception) {
            text_progress.text = "100%"
        }

        for(i in 0 until data.getJSONArray("platform").length()) {
            when (i) {
                0 -> {
                    ContentData.setIcon(
                        view!!.findViewById(R.id.page_project_icon_1),
                        data.getJSONArray("platform").getString(i)
                    )
                }
                1 -> {
                    ContentData.setIcon(
                        view!!.findViewById(R.id.page_project_icon_2),
                        data.getJSONArray("platform").getString(i)
                    )
                }
                2 -> {
                    ContentData.setIcon(
                        view!!.findViewById(R.id.page_project_icon_3),
                        data.getJSONArray("platform").getString(i)
                    )
                }
                3 -> {
                    ContentData.setIcon(
                        view!!.findViewById(R.id.page_project_icon_4),
                        data.getJSONArray("platform").getString(i)
                    )
                }
            }
        }

        ContentData.getImage(
            projects_data.getJSONObject(index).getString("avatar"),
            image,
            "project_" + data.getInt("id").toString()
        )
    }
}
