package viacheslav.chugunov.spy.internal.data

import java.lang.reflect.Field
import java.util.*

class ModelReflectParser {

    //TODO Builder mustn't exist as a field variable
    private val strBuilder = StringBuilder()

    fun getFieldsClassInfo(any: Any): Map<String, String> {

        if(isPrimitive(any)) {
            val type = any.javaClass.simpleName
            return mapOf(type to any.toString())
        }

        val qq: Queue<Pair<String, Any>> = LinkedList()
        val result = mutableMapOf<String, Any>()

        val nullValue = "null"
        val objectValue = if (any.javaClass.isAnonymousClass) "<object>" else null

        val allFields = getAllFieldsClass(any).toTypedArray()
        val classPath = objectValue ?: any.javaClass.simpleName
        loadFieldsToQueue(any, qq, classPath, allFields)

        while (qq.isNotEmpty()) {
            val item = qq.remove()
            val currentPath = item.first
            val currentValue = item.second
            when {
                isPrimitive(currentValue) -> {
                    result[currentPath] = currentValue.toString()
                }
                isIterable(currentValue) -> {
                    val iterable = currentValue as? Iterable<*> ?: (currentValue as Array<*>).asIterable()
                    val iterablePath = addTagToPath(currentPath, currentValue.javaClass.simpleName)

                    iterable.forEachIndexed { index, listItemValue ->
                        val typeVariable = listItemValue?.javaClass?.simpleName
                        val listItemPath = createIterableTag(iterablePath, typeVariable, index)

                        if (listItemValue == null) {
                            result[listItemPath] = nullValue
                        }
                        else if (isPrimitive(listItemValue)) {
                            result[listItemPath] = listItemValue.toString()
                        }
                        else {
                            qq.offer(Pair(listItemPath, listItemValue))
                        }
                    }
                }
                currentValue.javaClass.isEnum -> {
                    result[currentPath] = currentValue.toString()
                }
                else -> {
                    val newPath = addTagToPath(currentPath, currentValue.javaClass.simpleName)
                    loadFieldsToQueue(currentValue, qq, newPath)
                }
            }
        }
        return result.mapValues { it.value.toString() }
    }

    private fun loadFieldsToQueue(
        value: Any, queue: Queue<Pair<String, Any>>,
        path: String, fields: Array<out Field>? = null,
    ) {
        val nullValue = "null"
        val currentFields = fields ?: value.javaClass.declaredFields
        currentFields.forEach { field ->
            field.isAccessible = true
            val fieldPath = addTagToPath(path, field.name)
            val fieldValue = (field.get(value) ?: nullValue)
            queue.offer(Pair(fieldPath, fieldValue))
        }
    }

    private fun getAllFieldsClass(any: Any): List<Field> {
        val allFields = any.javaClass.declaredFields.toMutableList()
        var currentClass = any
        var superClass = currentClass.javaClass.superclass
        while (superClass != null) {
            allFields.addAll(superClass.declaredFields)
            currentClass = superClass
            superClass = currentClass.superclass
        }
        return allFields
    }

    private fun addTagToPath(path: String, tag: String): String {
        strBuilder.append(path)
            .append('.')
            .append(tag)
        val resultValue = strBuilder.toString()
        strBuilder.clear()
        return resultValue
    }

    private fun createIterableTag(path: String, type: String?, index: Int): String {
        val currentType = type ?: "Nothing"
        strBuilder
            .append(path)
            .append('<')
            .append(currentType)
            .append(">[")
            .append(index)
            .append(']')
        val resultValue = strBuilder.toString()
        strBuilder.clear()
        return resultValue
    }

    private fun isPrimitive(value: Any): Boolean {
        return value.javaClass.isPrimitive
                || value::class.javaPrimitiveType != null
                || value.javaClass.isAssignableFrom(String::class.java)
    }

    private fun isIterable(value: Any): Boolean {
        return value is Iterable<*> || value.javaClass.isArray
    }
}