package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContentData.projects_data
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class AllProjectsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layout = view.findViewById<LinearLayout>(R.id.all_projects_layout)
        for(i in 0 until projects_data.length()) {
            layout.addView(ProjectBlock(parentFragment as PageFragment, i).create())
            view.findViewById<View>(R.id.all_projects_empty).visibility = View.GONE
        }
    }
}
