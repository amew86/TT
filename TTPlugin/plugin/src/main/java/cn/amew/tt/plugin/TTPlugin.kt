package cn.amew.tt.plugin

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/14 2:25 PM
 * Update Date:
 * Modified By:
 * Description: entrance for plugin
 */
class TTPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.find { plugin ->
            if (plugin.javaClass.name == "com.android.build.gradle.AppPlugin") {
                currentProjectDir = project.projectDir.path
            }
            false
        }
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)
        androidComponents.onVariants { variant ->
            variant.instrumentation.transformClassesWith(
                TTAsmFactory::class.java,
                InstrumentationScope.ALL,
            ) {}
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
        }
    }
}

var currentProjectDir: String = ""