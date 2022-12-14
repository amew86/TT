package cn.amew.tt.core

import android.content.Context

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/14 1:59 PM
 * Update Date:
 * Modified By:
 * Description: core entrance
 */
object TT {

    fun init(context: Context) {
        // TODO:  init from asset path
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