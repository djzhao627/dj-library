package cn.djzhao.library

import cn.djzhao.common.ui.component.DJBaseApplication
import cn.djzhao.library.log.DJLogConfig
import cn.djzhao.library.log.DJLogConfig.JsonParser
import cn.djzhao.library.log.DJLogManger
import cn.djzhao.library.log.printer.DJConsolePrinter
import cn.djzhao.library.log.printer.DJFilePrinter
import com.google.gson.Gson

class MyApplication : DJBaseApplication() {
    override fun onCreate() {
        super.onCreate()
        DJLogManger.init(
            object : DJLogConfig() {
                override fun injectJsonParser(): JsonParser {
                    return JsonParser { src ->
                        Gson().toJson(src)
                    }
                }

                override fun getGlobalTag(): String {
                    return "DJLog"
                }

                override fun enable(): Boolean {
                    return true
                }

                override fun includeThread(): Boolean {
                    return true
                }

                override fun stackTraceDepth(): Int {
                    return 5
                }
            },
            DJConsolePrinter(),
            DJFilePrinter.getInstance(externalCacheDir?.absolutePath, 0)
        )
    }
}