import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.PrettyPrint.blockWithTimeMeasure

val sequence = sequence {
    (1..3).forEach {
        yield("http://httpbin.org/delay/$it".httpGet().response())
    }
}

fun main() = blockWithTimeMeasure {
    runBlocking {
        launch {
            repeat(1000) {
                delay(100)
                println("other corutine $it")
            }
        }

        sequence.forEach { response ->
            println("recibida respuesta de ${response.first.url}: ${response.second.body().length} caracteres")
        }
}}



