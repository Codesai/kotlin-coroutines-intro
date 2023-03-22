import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import util.PrettyPrint.blockWithTimeMeasure

fun main() = blockWithTimeMeasure { runBlocking {
    val responses = (1..5).map {
        async {
            val url = "http://httpbin.org/delay/$it"
            println("get $url")
            url.httpGet().response()
        }
    }

    responses.forEach { deferredResponse ->
        val response = deferredResponse.await()
        println("recibida respuesta de ${response.first.url}: ${response.second.body().length} caracteres")
    }
}}


