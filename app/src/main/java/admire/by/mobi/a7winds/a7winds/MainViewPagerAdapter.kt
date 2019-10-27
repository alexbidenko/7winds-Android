package admire.by.mobi.a7winds.a7winds

import admire.by.mobi.a7winds.a7winds.ContentData.fragmentsMap
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MainViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int  = 4

    override fun getItem(i: Int): Fragment {
        val fragment = PageFragment()
        fragment.pageData = fragmentsMap[i]
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}