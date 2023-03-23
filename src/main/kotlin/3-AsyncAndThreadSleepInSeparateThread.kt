import util.PrettyPrint.blockWithTimeMeasure
import util.PrettyPrint.prettyPrint
import util.PrettyPrint.printBlock
import kotlinx.coroutines.*

fun main() = blockWithTimeMeasure {
    runBlocking { printBlock(name="runBlocking") {
        val deferredAsync1 = async(Dispatchers.IO) { printBlock(name = "async-1") {
            Thread.sleep(3_000)
            "Hola "
        }}
        val deferredAsync2 = async(Dispatchers.IO) { printBlock(name="async-2") {
            Thread.sleep(3_000)
            "Mundo"
        }}
        prettyPrint("runBlocking ${deferredAsync1.await()} ${deferredAsync2.await()}")
    }}
}


