package cn.djzhao.library

import android.app.Application
import cn.djzhao.library.log.DJLogConfig
import cn.djzhao.library.log.DJLogManger

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DJLogManger.init(object : DJLogConfig() {
            override fun getGlobalTag(): String {
                return "Hello DJLog"
            }

            override fun enable(): Boolean {
                return true
            }
        })
    }
}