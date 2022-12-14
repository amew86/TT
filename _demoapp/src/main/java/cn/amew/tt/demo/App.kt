package cn.amew.tt.demo

import android.app.Application
import cn.amew.tt.core.TT

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/14 5:00 PM
 * Update Date:
 * Modified By:
 * Description:
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        TT.init(this)
    }
}