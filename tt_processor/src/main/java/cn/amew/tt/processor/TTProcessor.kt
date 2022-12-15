package cn.amew.tt.processor

import cn.amew.tt.annotation.TTRoute
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

/**
 * Author:      A-mew
 * Create Date: Created in 2022/12/14 2:53 PM
 * Update Date:
 * Modified By:
 * Description:
 */
@AutoService(Processor::class)
class TTProcessor: AbstractProcessor() {

    private var elementUtils: Elements? = null

    private var typeUtils: Types? = null

    private var filer: Filer? = null

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        elementUtils = processingEnv?.elementUtils
        typeUtils = processingEnv?.typeUtils
        filer = processingEnv?.filer
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        roundEnv?.getElementsAnnotatedWith(TTRoute::class.java)?.forEach { classElement ->
            val qualifiedName = (classElement as TypeElement).qualifiedName
            val wrapperClassName = "${classElement.simpleName}__TTWrapper"

            val wrapperType = TypeSpec.classBuilder(wrapperClassName)
                .addKdoc("AUTO GENERATED, DO NOT MODIFY")
                .build()

            val packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf('.'))
//            println("packageName: $packageName, wrapperClassName: $wrapperClassName")
            val file = FileSpec.builder(packageName, wrapperClassName)
                .addType(wrapperType)
                .build()
            filer?.let { file.writeTo(it) }
        }
        return false
    }

    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_8

    override fun getSupportedAnnotationTypes() = hashSetOf(TTRoute::class.java.name)
}