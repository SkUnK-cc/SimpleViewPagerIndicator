package com.example.hp.simpleviewpagerindicator

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.hp.simpleviewpagerindicator.custom.SimpleIndicator


class MainActivity : AppCompatActivity() {

    var string_array: Array<String> = arrayOf("番茄","土豆","西红柿")

    var indicator: SimpleIndicator? = null
    var mViewpager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        indicator = findViewById(R.id.ll_indicator)
        indicator?.setIndicatorListener(object: SimpleIndicator.IndicatorListener{
            override fun onClick(position: Int) {
                mViewpager?.currentItem = position
            }
        })
        indicator?.setTitles(string_array)

        mViewpager = findViewById(R.id.vp_fragment)
        var list: MutableList<Fragment> = mutableListOf()
        for(String in string_array){
            list.add(TestFragment())
        }
        var mAdapter: TabFragmentPagerAdapter = TabFragmentPagerAdapter(supportFragmentManager,list)
        mViewpager?.adapter = mAdapter
        mViewpager?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            /**
             * 从第一项滚动到第二项时，滚动前position为0，滚动时position为0,offset由0增到1，滚动完成position为1
             * 从第二项滚动到第一项是，滚动前position为1，滚动时position为0,offset由1减到0，滚动完成position为0
             */
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                Log.e("scroll","position = "+p0)
                Log.e("scroll","offset = "+p1)
                indicator?.vpScroll(p0,p1)
            }

            override fun onPageSelected(p0: Int) {

            }

        })
    }
}
