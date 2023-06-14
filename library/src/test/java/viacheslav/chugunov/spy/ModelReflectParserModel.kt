package viacheslav.chugunov.spy

import org.junit.Assert
import org.junit.Test
import viacheslav.chugunov.spy.internal.data.ModelReflectParser

class ModelReflectParserModel {
    private val parser = ModelReflectParser()

    @Test
    fun parseEmptyModel() {
        class Model
        val model = Model()
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, emptyMap<String, String>())
    }

    @Test
    fun parseSimpleModel() {
        class Model(val field1: Int, val field2: String, private val field3: Double)
        val model = Model(1, "2", 3.0)
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "Model.field1" to "1",
            "Model.field2" to "2",
            "Model.field3" to "3.0"
        ))
    }

    @Test
    fun primitiveClass() {
        val a = 5
        val map = parser.getFieldsClassInfo(a)
        val expected = mapOf("Integer" to "5")
        Assert.assertEquals(
            expected, map
        )
    }

    @Test
    fun primitiveInterface() {
        val sequence: CharSequence = "abs"
        val map = parser.getFieldsClassInfo(sequence)
        val expected = mapOf("String" to sequence)
        Assert.assertEquals(expected, map)
    }

    @Test
    fun parseSimpleModelWithNulls() {
        class Model(val field1: String?, private val field2: Int?)
        val model = Model(null, null)
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "Model.field1" to "null",
            "Model.field2" to "null"
        ))
    }

    @Test
    fun parseModelWithInnerClass() {
        class Inner(val field1: Int, private val field2: String)
        class Model(val field1: Int, private val field2: Inner, private val field3: String)
        val model = Model(1, Inner(2, "3"), "4")
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "Model.field1" to "1",
            "Model.field2.Inner.field1" to "2",
            "Model.field2.Inner.field2" to "3",
            "Model.field3" to "4"
        ))
    }

    @Test
    fun parseModelWithSimpleList() {
        class Model(val field1: Int, private val field2: String, val field3: List<Int>)
        val model = Model(1, "2", listOf(3, 4, 5))
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "Model.field1" to "1",
            "Model.field2" to "2",
            "Model.field3.ArrayList<Integer>[0]" to "3",
            "Model.field3.ArrayList<Integer>[1]" to "4",
            "Model.field3.ArrayList<Integer>[2]" to "5",
        ))
    }

    @Test
    fun parseModelWithInnerClassList() {
        class Inner(val field1: Int, private val field2: String)
        class Model(val field1: Int, private val field2: List<Inner>, private val field3: String)
        val model = Model(1, listOf(Inner(2, "3"), Inner(4, "5")), "6")
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "Model.field1" to "1",
            "Model.field2.ArrayList<Inner>[0].Inner.field1" to "2",
            "Model.field2.ArrayList<Inner>[0].Inner.field2" to "3",
            "Model.field2.ArrayList<Inner>[1].Inner.field1" to "4",
            "Model.field2.ArrayList<Inner>[1].Inner.field2" to "5",
            "Model.field3" to "6"
        ))
    }

    @Test
    fun parseModelWithTwoInnerClasses() {
        class Inner1(val field1: Int, val field2: String)
        class Inner2(val field1: Double, val field2: Float)
        class Model(private val field1: Inner1, private val field2: Inner2)
        val model = Model(Inner1(1, "2"), Inner2(3.0, 4.0f))
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "Model.field1.Inner1.field1" to "1",
            "Model.field1.Inner1.field2" to "2",
            "Model.field2.Inner2.field1" to "3.0",
            "Model.field2.Inner2.field2" to "4.0",
        ))
    }

    @Test
    fun parseAnonymousInterfaceObject() {
        val model = object : InterfaceModel {
            override val field1: String =  "1"
            override val field2: Int = 2
        }
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "<object>.field1" to "1",
            "<object>.field2" to "2"
        ))
    }

    @Test
    fun parseAnonymousAbstractObject() {
        abstract class AbstractModel {
            abstract val field1: Int
            abstract val field2: String
        }
        val model = object : AbstractModel() {
            override val field1: Int =  1
            override val field2: String = "2"
        }
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "<object>.field1" to "1",
            "<object>.field2" to "2"
        ))
    }

    @Test
    fun parseInheritClass() {
        open class Parent(val field1: String, private val field2: Int)
        class Model(private val field3: Double, val field4: Float) : Parent("1", 2)
        val model = Model(3.0, 4.0f)
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "Model.field1" to "1",
            "Model.field2" to "2",
            "Model.field3" to "3.0",
            "Model.field4" to "4.0"
        ))
    }

    @Test
    fun parseClassWithItselfInstance() {
        class Model(private val field1: Model?)
        val model = Model(Model(Model(null)))
        val map = parser.getFieldsClassInfo(model)
        Assert.assertEquals(map, mapOf(
            "Model.field1.Model.field1.Model.field1" to "null"
        ))
    }

    interface InterfaceModel {
        val field1: String
        val field2: Int
    }
}