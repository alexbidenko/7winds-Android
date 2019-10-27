package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContentData.plans_data
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class AllPlansFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_plans, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layout = view.findViewById<LinearLayout>(R.id.all_plans_layout)
        for(i in 0 until plans_data.length()) {
            if(plans_data.getJSONObject(i).getLong("time") > System.currentTimeMillis()) {
                layout.addView(PlanBlock(parentFragment as PageFragment, plans_data.getJSONObject(i)).create())
                view.findViewById<View>(R.id.all_plans_empty).visibility = View.GONE
            }
        }
    }
}
