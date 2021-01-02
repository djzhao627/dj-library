package cn.djzhao.library.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.djzhao.library.R
import cn.djzhao.library.log.*
import kotlinx.android.synthetic.main.activity_d_j_log_demo.*

class DJLogDemoActivity : AppCompatActivity() {

    lateinit var screenPrinter: DJScreenPrinter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_j_log_demo)

        screenPrinter = DJScreenPrinter(this)
        screenPrinter.printerProvider.showFloatingView()

        button.setOnClickListener {
            printLog()
        }
    }

    private fun printLog() {
        DJLogManger.getInstance().addPrinter(screenPrinter)
        DJLog.log(object : DJLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, DJLogType.E, "DDDJJJ", "Custom Log")
        DJLog.a("Hello DJLog")
        Log.println(Log.ERROR, "tag", "message")
    }
}