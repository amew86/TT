package cn.amew.tt.wrapper

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/15 7:55 AM
 * Update Date:
 * Modified By:
 * Description:
 */
interface ITTServiceWrapper<S> {

    fun injectService(service: S)

    operator fun invoke(serviceName: String, params: HashMap<String, Any?>? = null, callback: (HashMap<String, Any?>?) -> Unit)
}