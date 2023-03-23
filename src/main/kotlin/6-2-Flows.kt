import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.PrettyPrint.blockWithTimeMeasure

val flow = flow {
    (1..3).forEach {
        val url = "http://httpbin.org/delay/$it"
        println("get $url")
        emit(url.httpGet().awaitStringResponse())
    }
}

fun main() = blockWithTimeMeasure {
    runBlocking {
        launch {
            (1..1000).forEach {
                delay(100)
                println("other corutine $it")
            }
        }

        flow.collect { response ->
            println("recibida respuesta de ${response.first.url}: ${response.second.body().length} caracteres")
        }
    }
}


