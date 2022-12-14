package cn.amew.tt.core

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/14 2:01 PM
 * Update Date:
 * Modified By:
 * Description: use TT.plugin to create it
 */
class TTPlugin internal constructor(path: String) {

    operator fun invoke(params: HashMap<String, Any?>? = null, callback: () -> Unit) {

    }

    @Throws(Exception::class)
    operator fun <T> invoke(params: HashMap<String, Any?>? = null): T? {
        return null
    }
}