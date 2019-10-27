package admire.by.mobi.a7winds.a7winds

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.arellomobile.mvp.presenter.InjectPresenter



class MainActivity : MvpAppCompatActivity(), MainActivityView  {

    @InjectPresenter lateinit var mainActivityPresenter: MainActivityPresenter

    private var mainViewPagerAdapter: FragmentStatePagerAdapter? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_persons -> {
                main_view_pager.currentItem = 0
            }
            R.id.navigation_projects -> {
                main_view_pager.currentItem = 1
            }
            R.id.navigation_plans -> {
                main_view_pager.currentItem = 2
            }
            R.id.navigation_trainee -> {
                main_view_pager.currentItem = 4
            }
            /*R.id.navigation_chat -> {
                active = "chat"
                activeFragment = сhatFragment
            }*/
        }
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        main_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
            }
        })

        /*val lp = loaderImage.layoutParams
        lp.height = (loader.height * 0.2).toInt()
        lp.width = (loader.height * 0.2).toInt()
        loaderImage.layoutParams = lp

        loader.setPadding(0, (loader.height * 0.4).toInt(), 0, 0)*/

        main_internet_error.setOnClickListener {
            (main_internet_error.getChildAt(0) as TextView).text = getString(R.string.internet_update_message)
            main_internet_error.setBackgroundResource(R.color.color7WindsYellow)
            GetDataTask(
                this
            ).execute()
        }

        main_refresh.setColorSchemeResources(R.color.color7WindsYellow, android.R.color.black)
        main_refresh.setOnRefreshListener {
            main_refresh.isRefreshing = true
            GetDataTask(
                this
            ).execute()
        }

        GetDataTask(
            this
        ).execute()

        Handler().postDelayed({
            if(!ContentData.isUpdata) {
                setInternetError(true)
                onDataLoaded()
            }
        }, 5000)
    }

    fun onDataLoaded() {
        mainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        main_view_pager.offscreenPageLimit = 4
        main_view_pager.adapter = mainViewPagerAdapter

        val set = AnimatorInflater.loadAnimator(this, R.animator.loader_anim) as AnimatorSet
        set.setTarget(main_loader_image)
        set.start()

        Handler().postDelayed({
            main_loader.visibility = View.GONE
        }, 500)

        main_refresh.isRefreshing = false

        main_view_pager.currentItem = when(navigation.selectedItemId) {
            R.id.navigation_persons -> 0
            R.id.navigation_projects -> 1
            R.id.navigation_plans -> 2
            R.id.navigation_trainee -> 4
            else -> 0
        }
    }

    fun setInternetError(value: Boolean) {
        if(value) {
            (main_internet_error.getChildAt(0) as TextView).text = getString(R.string.internet_connect_error)
            main_internet_error.setBackgroundResource(android.R.color.holo_orange_light)
            main_internet_error.visibility = View.VISIBLE
        } else {
            main_internet_error.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()

        mainViewPagerAdapter?.saveState()
    }

    override fun onResume() {
        super.onResume()

        main_view_pager.adapter = mainViewPagerAdapter
        main_view_pager.currentItem = when(navigation.selectedItemId) {
            R.id.navigation_persons -> 0
            R.id.navigation_projects -> 1
            R.id.navigation_plans -> 2
            R.id.navigation_trainee -> 4
            else -> 0
        }
    }

    /*override fun onResume() {
        super.onResume()
        CiceroneApplication.INSTANCE!!.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        CiceroneApplication.INSTANCE!!.navigatorHolder.removeNavigator()
    }

    private val navigator = SupportAppNavigator(this, R.id.container)*/
}
