package cn.djzhao.library.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.djzhao.library.R
import cn.djzhao.library.log.DJLog
import cn.djzhao.library.log.DJLogConfig
import cn.djzhao.library.log.DJLogManger
import cn.djzhao.library.log.DJLogType
import cn.djzhao.library.log.printer.DJFilePrinter
import cn.djzhao.library.log.printer.DJScreenPrinter
import kotlinx.android.synthetic.main.activity_d_j_log_demo.*

class DJLogDemoActivity : AppCompatActivity() {

    lateinit var screenPrinter: DJScreenPrinter
    lateinit var filePrinter: DJFilePrinter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_j_log_demo)

        // 屏幕日志输出
        screenPrinter = DJScreenPrinter(this)
        screenPrinter.printerProvider.showFloatingView()
        DJLogManger.getInstance().addPrinter(screenPrinter)

        button.setOnClickListener {
            printLog()
        }
    }

    private fun printLog() {
        DJLog.a("Hello DJLog")

        // 自定义
        DJLog.log(object : DJLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, DJLogType.E, "DDDJJJ", "Custom Log")
    }
}