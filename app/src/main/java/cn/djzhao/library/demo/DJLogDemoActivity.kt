package cn.djzhao.library.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.djzhao.library.R
import cn.djzhao.library.log.DJLog
import kotlinx.android.synthetic.main.activity_d_j_log_demo.*

class DJLogDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_j_log_demo)

        button.setOnClickListener {
            printLog()
        }
    }

    private fun printLog() {
        DJLog.a("Hello DJLog")
    }
}