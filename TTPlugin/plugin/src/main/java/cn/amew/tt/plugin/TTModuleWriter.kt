package cn.amew.tpagerouter.plugin

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/13 2:14 PM
 * Update Date:
 * Modified By:
 * Description:
 */
class TPageRouterModuleWriter private constructor(val currentPath: String) {

    private val existRouterPaths = HashSet<String>()
    private val allRouterModules = HashSet<TPageRouterModuleBean>()
    private val gson = Gson()
    private var file: File = File(currentPath)

    init {
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        if (file.exists()) {
            kotlin.runCatching {
                val jsonStr = StringBuilder()
                val fis = FileReader(file)
                fis.readLines().forEach { line ->
                    jsonStr.append(line)
                }
                if (jsonStr.isNotEmpty()) {
                    val routerModules = gson.fromJson<HashSet<TPageRouterModuleBean>>(
                        jsonStr.toString(),
                        object : TypeToken<HashSet<TPageRouterModuleBean>>() {}.type
                    )
                    existRouterPaths.addAll(routerModules.map { it.path })
                    allRouterModules.addAll(routerModules)
                }
            }.onFailure {
                it.printStackTrace()
            }
            file.delete()
        }
        file.createNewFile()
    }

    fun append(path: String, moduleClassName: String) {
        if (existRouterPaths.contains(path)) {
            println("path $path already exists, ignored")
            return
        }
        allRouterModules.add(TPageRouterModuleBean(path, moduleClassName))

        val jsonStr = gson.toJson(allRouterModules)
        kotlin.runCatching {
            val writer = BufferedWriter(FileWriter(file))
            writer.append(jsonStr)
            writer.close()
        }.onFailure {
            it.printStackTrace()
        }
    }

    companion object {
        private var cachedFileWriter: TPageRouterModuleWriter? = null

        private const val ASSET_JSON_SUFFIX = "/src/main/assets/tpagerouter.json"

        fun fromPath(path: String = "$currentProjectDir$ASSET_JSON_SUFFIX"): TPageRouterModuleWriter {
            if (null == cachedFileWriter || cachedFileWriter?.currentPath != path) {
                cachedFileWriter = TPageRouterModuleWriter(path)
            }
            return cachedFileWriter!!
        }
    }
}

data class TPageRouterModuleBean(
    val path: String,
    val moduleClassName: String,
)