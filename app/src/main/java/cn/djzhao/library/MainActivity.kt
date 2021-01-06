package cn.djzhao.library

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.djzhao.library.demo.DJLogDemoActivity
import cn.djzhao.ui.tab.bottom.DJTabBottom
import cn.djzhao.ui.tab.bottom.DJTabBottomInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var homeBottomInfo = DJTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )

        /*homeBottomInfo = DJTabBottomInfo(
            "首页",
            BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground),
            BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)
        )*/

        val homeBottom = findViewById<DJTabBottom>(R.id.tab_bottom)
        homeBottom.tabInfo = homeBottomInfo
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.dj_log_tv -> startActivity(Intent(this, DJLogDemoActivity::class.java))
        }
    }
}