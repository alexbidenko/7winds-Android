package admire.by.mobi.a7winds.a7winds

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class PageFragment : Fragment() {

    private lateinit var fm: FragmentManager

    lateinit var pageData: PageDataObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fm = childFragmentManager

        fm.fragments.forEach {
            fm.beginTransaction().hide(it).remove(it).commit()
        }

        fm.beginTransaction()
            /*.add(R.id.page_container, pageData.personFragment).hide(pageData.personFragment)
            .add(R.id.page_container, pageData.projectFragment).hide(pageData.projectFragment)*/
            .replace(R.id.page_container, pageData.startFragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        fm.fragments.forEach {
            fm.beginTransaction().hide(it).remove(it).commit()
        }

        pageData.activeFragment = pageData.startFragment

        super.onSaveInstanceState(outState)
    }

    fun setActive(f: Fragment, sharedView: View) {
        ViewCompat.setTransitionName(sharedView, "shared_image")

        trimToActiveFragment(pageData.activeFragment)
        fm.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .addSharedElement(sharedView, "shared_image")
            .replace(R.id.page_container, f)
            /*.hide(pageData.activeFragment)
            .show(f)*/.commit()
        pageData.activeFragment = f
    }

    fun backActivity() {
        val f = pageData.history.last()
        pageData.history.remove(f)
        fm.beginTransaction().replace(R.id.page_container, f)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            /*.hide(pageData.activeFragment).show(f)*/.commit()
        pageData.activeFragment = f
    }

    private fun trimToActiveFragment(f: Fragment) {
        while(pageData.history.contains(f)) {
            pageData.history.remove(pageData.history.last())
        }
        pageData.history.add(f)
    }
}
