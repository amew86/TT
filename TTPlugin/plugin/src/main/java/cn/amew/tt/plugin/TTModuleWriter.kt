package cn.amew.tt.plugin

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
class TTModuleWriter private constructor(val currentPath: String) {

    private val allRouterModules = LinkedHashSet<TPageRouterModuleBean>()
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
                    val routerModules = gson.fromJson<LinkedHashSet<TPageRouterModuleBean>>(
                        jsonStr.toString(),
                        object : TypeToken<LinkedHashSet<TPageRouterModuleBean>>() {}.type
                    )
                    allRouterModules.addAll(routerModules)
                }
            }.onFailure {
                it.printStackTrace()
            }
            file.delete()
        }
        file.createNewFile()
    }

    fun append(path: String, className: String, level: Int) {
        val iterator = allRouterModules.iterator()
        while (iterator.hasNext()) {
            val existModule = iterator.next()
            if (existModule.path == path) {
                println("$path exist level is ${existModule.level}, current level is $level")
                if (existModule.level > level) {
                    println("ignore $className")
                    return
                } else {
                    println("replace ${existModule.className} to $className")
                    iterator.remove()
                }
            }
        }

        allRouterModules.add(TPageRouterModuleBean(path, className, level))

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
        private var cachedFileWriter: TTModuleWriter? = null

        private const val ASSET_JSON_SUFFIX = "/src/main/assets/tt.json"

        fun fromPath(path: String = "$currentProjectDir$ASSET_JSON_SUFFIX"): TTModuleWriter {
            if (null == cachedFileWriter || cachedFileWriter?.currentPath != path) {
                cachedFileWriter = TTModuleWriter(path)
            }
            return cachedFileWriter!!
        }
    }
}

data class TPageRouterModuleBean(
    val path: String,
    val className: String,
    val level: Int
)