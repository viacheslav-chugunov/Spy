package viacheslav.chugunov.spy.internal.data

class TestA{
    private val k = 123123
    private val a = 1
    private val cccccc = TestC()

    class TestC{
        private val abssss = "322"
    }

}

class TestB {
    private val amama = "amamam"
    val k = 343
    val myList = listOf(1,5, listOf(6,7,9),8)
    val myArray = arrayOf(7, listOf(TestA(), TestA.TestC(), setOf(TestA.TestC())),7)
    private val tested: TestA? = null
    private val enumchik: ABS? = null

    enum class ABS{
        ONE_VAR,
        SEC_VAR,
        THIRD_VAR,
    }
}