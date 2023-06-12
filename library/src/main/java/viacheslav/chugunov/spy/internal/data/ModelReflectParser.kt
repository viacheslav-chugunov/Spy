package viacheslav.chugunov.spy.internal.data

import java.util.*

class ModelReflectParser {

    fun getFieldsClassInfo(any: Any): Map<String, String> {
        val qq: Queue<Pair<String, Any>> = LinkedList()
        val result = mutableMapOf<String, Any>()
        val strBuilder = StringBuilder()
        val nullValue = "null"
        val collectionValue = ".collection"
        any.javaClass.declaredFields.forEach {field ->
            field.isAccessible = true
            strBuilder.append(any.javaClass.simpleName).append('.').append(field.name)
            val path = strBuilder.toString()
            val item = field.get(any) ?: nullValue
            qq.offer(Pair(path, item))
            strBuilder.clear()
        }
        while (qq.isNotEmpty()) {
            val item = qq.remove()
            val path = item.first
            val itemValue = item.second
            when {
                isPrimitive(itemValue) -> {
                    result[path] = itemValue.toString()
                }
                isIterable(itemValue) -> {
                    val iterable = itemValue as? Iterable<*> ?: (itemValue as Array<*>).asIterable()
                    iterable.forEach { listItem ->
                        if (listItem == null) {
                            result[path] = nullValue
                        } else if (isPrimitive(listItem)) {
                            if(result[path] != null) {
                                strBuilder.append(result[path].toString()).append(", ")
                            }
                            strBuilder.append(listItem.toString())
                            result[path] = strBuilder.toString()
                            strBuilder.clear()
                        } else {
                            strBuilder.append(path)
                            if(isIterable(listItem)) {
                                strBuilder.append(collectionValue)
                            }
                            qq.offer(
                                Pair(
                                    strBuilder.toString(),
                                    listItem
                                )
                            )
                            strBuilder.clear()
                        }
                    }
                }
                itemValue.javaClass.isEnum -> {
                    result[path] = itemValue.toString()
                }
                else -> {
                    itemValue.javaClass.declaredFields.forEach { field ->
                        field.isAccessible = true
                        strBuilder.append(path)
                            .append('.')
                            .append(itemValue.javaClass.simpleName)
                            .append('.')
                            .append(field.name)
                        qq.offer(
                            Pair(
                                strBuilder.toString(),
                                (field.get(itemValue) ?: nullValue)
                            )
                        )
                        strBuilder.clear()
                    }
                }
            }
        }
        return result.mapValues { it.value.toString() }
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