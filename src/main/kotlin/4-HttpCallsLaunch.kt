import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.PrettyPrint.blockWithTimeMeasure

fun main() = blockWithTimeMeasure {
    runBlocking {
        repeat(5) {
            launch {
                val url = "http://httpbin.org/delay/$it"
                println("get $url")
                val response = url.httpGet().awaitStringResponse()
                println("recibida respuesta de ${url}: ${response.second.body().length} caracteres")
            }
        }
    }
}
