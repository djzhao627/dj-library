package cn.djzhao.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.djzhao.library.demo.DJLogDemoActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.dj_log_tv -> startActivity(Intent(this, DJLogDemoActivity::class.java))
        }
    }
}