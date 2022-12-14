package cn.amew.tpagerouter.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.provider.Property
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes


/**
 * Author:      A-mew
 * Create Date: Created in 2022/11/28 10:28 AM
 * Update Date:
 * Modified By:
 * Description:
 */
abstract class TPageRouterAsmFactory: AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        return TPageRouterClassVisitor(classContext, nextClassVisitor)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        // exclude unnecessary classes
        if (classData.className.startsWith("android")) return false
        if (classData.className.startsWith("kotlin")) return false
        if (classData.className.startsWith("com.google")) return false
        if (classData.className.startsWith("org.intellij")) return false
        if (classData.className.startsWith("org.jetbrains")) return false

        if (classData.className.contains(".R$")) return false

        if (classData.className.endsWith(".R")) return false
        if (classData.className.endsWith(".BuildConfig")) return false
        return true
    }
}

class TPageRouterClassVisitor(
    private val classContext: ClassContext,
    nextVisitor: ClassVisitor
) : ClassVisitor(Opcodes.ASM5, nextVisitor) {

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        if (descriptor == "Lcn/amew/tpagerouter/annotation/TPageRouter;") {
            return TPageRouterAnnotationVisitor(classContext.currentClassData.className, api)
        }
        return super.visitAnnotation(descriptor, visible)
    }
}

class TPageRouterAnnotationVisitor(
    private val className: String,
    api: Int,
) : AnnotationVisitor(api) {

    override fun visit(name: String?, value: Any?) {
        super.visit(name, value)
        if (name == "value" && value is String) {
            TPageRouterModuleWriter.fromPath().append(value, className)
        }
    }

    override fun visitEnd() {
        println("visit end")
        super.visitEnd()
    }
}