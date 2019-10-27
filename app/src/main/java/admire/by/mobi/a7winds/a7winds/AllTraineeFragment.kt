package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContentData.persons_data
import admire.by.mobi.a7winds.a7winds.ContentData.sortJSONByAlpha
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class AllTraineeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_trainee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layout = view.findViewById<LinearLayout>(R.id.all_trainee_layout)

        val persons = sortJSONByAlpha(persons_data)

        var last_let: String? = null

        var count = 0
        for(i in 0 until persons.length()) {
            if(persons.getJSONObject(i).getInt("status") == 2) {
                if(last_let != persons.getJSONObject(i).getString("name").substring(0, 1)) {
                    last_let = persons.getJSONObject(i).getString("name").substring(0, 1)
                    if (count != 0) {
                        val line_view = View(activity!!)
                        val line_lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
                        line_lp.setMargins(24, 0, 24, 0)
                        line_view.layoutParams = line_lp
                        line_view.setBackgroundColor(Color.rgb(255, 255, 255))
                        layout.addView(line_view)
                    }
                    val let_view = TextView(activity!!)
                    let_view.text = last_let
                    let_view.setTextColor(Color.rgb(255, 255, 255))
                    let_view.setPadding(24, 8, 8, 8)
                    layout.addView(let_view)
                }
                layout.addView(PersonBlock(parentFragment as PageFragment, i).create())
                view.findViewById<View>(R.id.all_trainee_empty).visibility = View.GONE
                count++
            }
        }
    }
}
