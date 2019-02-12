package com.xun.eyepetizerkotlin.ui

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar
import com.xun.eyepetizerkotlin.R
import com.xun.eyepetizerkotlin.ui.fragment.FindFragment
import com.xun.eyepetizerkotlin.ui.fragment.HomeFragment
import com.xun.eyepetizerkotlin.ui.fragment.HotFragment
import com.xun.eyepetizerkotlin.ui.fragment.MineFragment
import com.xun.eyepetizerkotlin.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by wangxun on 2019/2/12.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    var homeFragment: HomeFragment? = null
    var findFragment: FindFragment? = null
    var hotFragment: HotFragment? = null
    var mineFragment: MineFragment? = null
    var toast: Toast? = null
    var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        val window = window
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params

        initToolbar()
        setRadioButton()
        initFragment(savedInstanceState)
    }

    private fun initToolbar() {
        val today = getToday()
        tv_bar_title.text = today
        tv_bar_title.typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        iv_search.setOnClickListener {
            if (rb_mine.isChecked) {
                showToast("点击设置")
            } else {//点击搜索

            }
        }
    }

    private fun setRadioButton() {
        rb_home.isChecked = true
        rb_home.setTextColor(resources.getColor(R.color.black))
        rb_home.setOnClickListener(this)
        rb_find.setOnClickListener(this)
        rb_hot.setOnClickListener(this)
        rb_mine.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        clearState()
        when (v?.id) {
            R.id.rb_home -> {
                rb_home.isChecked = true
                rb_home.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(homeFragment).hide(findFragment).hide(hotFragment).hide(mineFragment).commitAllowingStateLoss()
                tv_bar_title.text = getToday()
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
            }
            R.id.rb_find -> {
                rb_find.isChecked = true
                rb_find.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(findFragment).hide(homeFragment).hide(hotFragment).hide(mineFragment).commitAllowingStateLoss()
                tv_bar_title.text = "Discover"
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)

            }
            R.id.rb_hot -> {
                rb_hot.isChecked = true
                rb_hot.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(hotFragment).hide(findFragment).hide(homeFragment).hide(mineFragment).commitAllowingStateLoss()
                tv_bar_title.text = "Ranking"
                tv_bar_title.visibility = View.VISIBLE
                iv_search.setImageResource(R.drawable.icon_search)
            }
            R.id.rb_mine -> {
                rb_mine.isChecked = true
                rb_mine.setTextColor(resources.getColor(R.color.black))
                supportFragmentManager.beginTransaction().show(mineFragment).hide(findFragment).hide(hotFragment).hide(homeFragment).commitAllowingStateLoss()
                tv_bar_title.visibility = View.INVISIBLE
                iv_search.setImageResource(R.drawable.icon_setting)
            }
        }
    }

    private fun clearState() {
        rg_root.clearCheck()
        rb_home.setTextColor(resources.getColor(R.color.gray))
        rb_mine.setTextColor(resources.getColor(R.color.gray))
        rb_hot.setTextColor(resources.getColor(R.color.gray))
        rb_find.setTextColor(resources.getColor(R.color.gray))
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val fragments = supportFragmentManager.fragments
            for (item in fragments) {
                if (item is HomeFragment) {
                    homeFragment = item
                }
                if (item is FindFragment) {
                    findFragment = item
                }
                if (item is HotFragment) {
                    hotFragment = item
                }
                if (item is MineFragment) {
                    mineFragment = item
                }
            }
        } else {
            homeFragment = HomeFragment()
            findFragment = FindFragment()
            hotFragment = HotFragment()
            mineFragment = MineFragment()
            var fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fl_content, homeFragment)
            fragmentTransaction.add(R.id.fl_content, findFragment)
            fragmentTransaction.add(R.id.fl_content, hotFragment)
            fragmentTransaction.add(R.id.fl_content, mineFragment)
            fragmentTransaction.commitNowAllowingStateLoss()
        }
        supportFragmentManager.beginTransaction().show(homeFragment).hide(findFragment).hide(hotFragment).hide(mineFragment).commitAllowingStateLoss()
    }

    private fun getToday(): String {
        val list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return list[index]
    }

    override fun onPause() {
        super.onPause()
        toast?.let { it.cancel() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 3000) {
                finish()
                toast!!.cancel()
            } else {
                mExitTime = System.currentTimeMillis()
                toast = showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}