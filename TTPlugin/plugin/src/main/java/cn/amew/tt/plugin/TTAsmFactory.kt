package cn.amew.tt.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
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
abstract class TTAsmFactory: AsmClassVisitorFactory<InstrumentationParameters.None> {

    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        return TTClassVisitor(classContext, nextClassVisitor)
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

class TTClassVisitor(
    private val classContext: ClassContext,
    nextVisitor: ClassVisitor
) : ClassVisitor(Opcodes.ASM5, nextVisitor) {

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        if (descriptor == "Lcn/amew/tt/annotation/TTRoute;") {
            return TTAnnotationVisitor(classContext.currentClassData.className, api)
        }
        return super.visitAnnotation(descriptor, visible)
    }
}

class TTAnnotationVisitor(
    private val className: String,
    api: Int,
) : AnnotationVisitor(api) {

    private var mPath: String? = null
    private var mLevel: Int = 0

    override fun visit(name: String?, value: Any?) {
        super.visit(name, value)
        if (name == "path" && value is String) {
            println("name: $name, path: $value")
            mPath = value
        } else if (name == "level" && value is Int) {
            mLevel = value
        }
    }

    override fun visitEnd() {
        mPath?.let { TTModuleWriter.fromPath().append(it, className, mLevel) }
        super.visitEnd()
    }
}