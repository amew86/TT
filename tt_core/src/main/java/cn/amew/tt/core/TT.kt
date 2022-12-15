package cn.amew.tt.core

import android.content.Context
import cn.amew.tt.wrapper.ITTServiceWrapper
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/14 1:59 PM
 * Update Date:
 * Modified By:
 * Description: core entrance
 */
object TT {

    private val allServiceWrappers = HashMap<String, ITTServiceWrapper<*>>()

    fun init(context: Context) {
        val jsonBuilder = StringBuilder()
        kotlin.runCatching {
            val inputStream = context.assets.open("tt.json")
            val bf = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                jsonBuilder.append(line)
            }
            // TODO: dispatch unfinished
        }.onFailure {
            it.printStackTrace()
        }
    }

    /**
     *  route to page
     */
    fun page(path: String) = TTPage(path)

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun service(path: String): ITTServiceWrapper<*>? {
        return allServiceWrappers[path]
    }

    /**
     *
     */
    fun plugin(path: String) = TTPlugin(path)
}