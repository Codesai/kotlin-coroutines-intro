package util

object PrettyPrint {

    var numberOfCalls = 0

    fun prettyPrint(s : String) {
        print("|")
        (0..numberOfCalls).forEach { print("-") }
        print("->")
        println(s)
        numberOfCalls++
    }

    suspend fun <T>printBlock(name : String, block : suspend () -> T) : T {
        prettyPrint("Begin [$name]")
        val s = block()
        prettyPrint("End [$name]")
        return s
    }

    fun blockWithTimeMeasure(block : () -> Unit) {
        val start = System.nanoTime()
        block()
        val end = System.nanoTime()
        println("Tiempo de ejecución del bloque: ${(end-start) / 1_000_000_000} segundos")
    }

}
