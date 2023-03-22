import util.PrettyPrint.blockWithTimeMeasure
import util.PrettyPrint.prettyPrint
import util.PrettyPrint.printBlock
import kotlinx.coroutines.*

fun main(args: Array<String>) { blockWithTimeMeasure {
    runBlocking(CoroutineName("principal")) { printBlock(name="runBlocking") {
        val deferredAsync1 = async { printBlock(name = "async-1") {
                delay(3_000)
                "Hola "
            }
        }
        val deferredAsync2 = async { printBlock(name="async-2") {
                delay(3_000)
                "Mundo"
            }
        }

        prettyPrint("runBlocking ${deferredAsync1.await()} ${deferredAsync2.await()}")
    }}
}}


