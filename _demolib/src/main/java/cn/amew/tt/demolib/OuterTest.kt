package cn.amew.tt.demolib

import android.util.Log
import cn.amew.tt.core.TT

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/15 8:30 AM
 * Update Date:
 * Modified By:
 * Description:
 */
object OuterTest {

    fun testService() {
        TT.service("reverseService")?.invoke("testFun") {
            Log.e("TT", "on response")
            it?.entries?.forEach { entry ->
                Log.w("TT", "key: ${entry.key}, value: ${entry.value}")
            }
        }
    }
}