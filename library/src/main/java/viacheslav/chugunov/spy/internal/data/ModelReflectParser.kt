package viacheslav.chugunov.spy.internal.data

class ModelReflectParser {

    fun getFieldsClassInfo(any: Any): Map<String, String> {
        val map = mutableMapOf<String, String>()
        val fields = any.javaClass.declaredFields
        fields.forEach {
            try {
                it.isAccessible = true
                map[it.name] = it.get(any)!!.toString()
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
        return map
    }

}