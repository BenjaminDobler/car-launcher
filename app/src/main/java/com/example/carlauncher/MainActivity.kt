package com.example.carlauncher

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2


class MainActivity : AppCompatActivity() {


    lateinit var pager: ViewPager2;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide(); //<< this

        setContentView(R.layout.activity_main)

        pager = findViewById(R.id.pager);
        val pagerAdapter = DoppelgangerAdapter(this, 5)
        pager.adapter = pagerAdapter
        pager.setPageTransformer(ViewPager2PageTransformation())


        // Get a list of all installed widgets
        val mAppWidgetManager =
            AppWidgetManager.getInstance(applicationContext)
        val infoList =
            mAppWidgetManager.installedProviders

        for (info in infoList) {

        }


    }


}

class DoppelgangerAdapter(activity: AppCompatActivity, val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        println("Position $position")

        return when (position) {
            0 -> MenuScreenFragment.getInstance(position)
            else -> ScreenFragment.getInstance(position)
        }



    }
}


class ViewPager2PageTransformation : ViewPager2.PageTransformer {
    val MIN_SCALE = 0.85f

    override fun transformPage(page: View, position: Float) {
        when {
            position < -1 ->
                page.alpha = 0.1f
            position <= 1 -> {
                page.alpha = Math.max(0.2f, 1 - Math.abs(position))
                val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                page.scaleX = scaleFactor;
                page.scaleY = scaleFactor;

                val vertMargin = page.height * (1 - scaleFactor) / 2
                val horzMargin = page.width * (1 - scaleFactor) / 2
                page.translationX = if (position < 0) {
                    horzMargin - vertMargin / 2
                } else {
                    horzMargin + vertMargin / 2
                }

            }
            else -> page.alpha = 0.1f
        }
    }
}