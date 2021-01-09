package cn.djzhao.library.demo.tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cn.djzhao.library.R
import cn.djzhao.library.utils.DJDisplayUtil
import cn.djzhao.ui.tab.bottom.DJTabBottom
import cn.djzhao.ui.tab.bottom.DJTabBottomInfo
import cn.djzhao.ui.tab.bottom.DJTabBottomLayout
import kotlinx.android.synthetic.main.activity_dj_tab_bottom_demo.*
import java.util.ArrayList

class DJTabBottomDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dj_tab_bottom_demo)

        initTablayout();
    }

    private fun initTablayout() {
        var tabLayout = findViewById<DJTabBottomLayout>(R.id.djTabLayout)
        tabLayout.setTabLayoutAlpha(0.85f)
        val bottomInfoList: MutableList<DJTabBottomInfo<*>> = ArrayList()


        val homeInfo = DJTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val recommendInfo = DJTabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val categoryInfo = DJTabBottomInfo(
            this,
            "分类",
            R.drawable.fire,
            R.drawable.fire,
            "#ff656667",
            "#ffd44949"
        )
        val chatInfo = DJTabBottomInfo(
            "聊天",
            "fonts/iconfont.ttf",
            getString(R.string.if_chat),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val profileInfo = DJTabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )

        bottomInfoList.addAll(
            listOf<DJTabBottomInfo<*>>(
                homeInfo,
                recommendInfo,
                categoryInfo,
                chatInfo,
                profileInfo
            )
        )
        djTabLayout.inflateInfo(bottomInfoList)
        djTabLayout.addTabSelectedChangeListener { _, _, _next ->
            Toast.makeText(
                this,
                _next.name,
                Toast.LENGTH_SHORT
            ).show()
        }
        djTabLayout.defaultSelected(homeInfo)
        var findTab = djTabLayout.findTab(categoryInfo)
        findTab?.resetHeight(DJDisplayUtil.dp2px(66f, resources), false)
    }
}