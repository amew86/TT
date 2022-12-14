package cn.amew.tt.core

import android.content.Context
import android.util.Log
import org.json.JSONArray
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
    fun service(path: String) = TTPage(path)

    /**
     *
     */
    fun component(path: String) = TTComponent(path)
}