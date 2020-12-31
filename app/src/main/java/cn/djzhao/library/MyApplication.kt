package cn.djzhao.library

import android.app.Application
import cn.djzhao.library.log.DJConsolePrinter
import cn.djzhao.library.log.DJLogConfig
import cn.djzhao.library.log.DJLogManger
import com.google.gson.Gson

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DJLogManger.init(object : DJLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src ->
                    Gson().toJson(src)
                }
            }

            override fun getGlobalTag(): String {
                return "Hello DJLog"
            }

            override fun enable(): Boolean {
                return true
            }
        }, DJConsolePrinter())
    }
}