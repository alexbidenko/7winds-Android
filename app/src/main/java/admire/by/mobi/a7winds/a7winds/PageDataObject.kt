package admire.by.mobi.a7winds.a7winds

import android.support.v4.app.Fragment

class PageDataObject(val startFragment: Fragment) {

    var activeFragment = startFragment

    val personFragment = PersonFragment()
    val projectFragment = ProjectFragment()

    val history = ArrayList<Fragment>()
}