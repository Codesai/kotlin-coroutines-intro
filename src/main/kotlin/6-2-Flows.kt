import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.PrettyPrint.blockWithTimeMeasure

fun main() = blockWithTimeMeasure { runBlocking {
    launch {
        (1..1000).forEach {
            delay(100)
            println("other corutine $it")
        }
    }

    flow.collect { response ->
       println("recibida respuesta de ${response.first.url}: ${response.second.body().length} caracteres")
    }
}}

val flow = flow {
    (1..3).forEach {
        emit("http://httpbin.org/delay/$it".httpGet().awaitStringResponse())
    }
}

