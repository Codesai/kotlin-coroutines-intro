import com.github.kittinunf.fuel.core.ResponseOf
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.github.kittinunf.fuel.httpGet
import io.ktor.client.plugins.observer.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.PrettyPrint
import kotlin.random.Random.Default.nextInt

fun main() = PrettyPrint.blockWithTimeMeasure { runBlocking {
    val channel = Channel<String>()

    launch {
        (1..5).forEach {
            launch {
                val url = "http://httpbin.org/delay/${nextInt(1, 5)}"
                println("get $url")
                val response = url.httpGet().awaitStringResponse()
                channel.send("peticion $it recibida, url: ${response.first.url}")
            }
        }
    }.invokeOnCompletion {
        channel.close()
    }

    channel.consumeEach { response ->
        println(response)
    }
}}

