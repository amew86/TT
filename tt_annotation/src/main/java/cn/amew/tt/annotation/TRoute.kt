package cn.amew.tt.annotation

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/14 1:57 PM
 * Update Date:
 * Modified By:
 * Description:
 */
annotation class TRoute(
    val path: String,
    val type: RouteType = RouteType.NONE,
    val level: Int = 0
)
