import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withTimeout
import util.PrettyPrint
import kotlin.random.Random

// in this example we use the httpClient from ktor library instead of the fuel librery like in the
// other examples because doing some testing the fuel library has some problem with cancellation: Active request
// are not cancelled and the parent scope (run blocking) does not finish until all request are finished.
fun main() = PrettyPrint.blockWithTimeMeasure {
    runBlocking {

        val option1 = async {
            withTimeout(15_000) {
                val url = "http://httpbin.org/delay/${Random.nextInt(1, 5)}"
                println("get $url")
                HttpClient(CIO).get(url)
            }

        }

        val option2 = async {
            withTimeout(15_000) {
                val url = "http://httpbin.org/delay/${Random.nextInt(1, 10)}"
                println("get $url")
                HttpClient(CIO).get(url)
            }
        }

        val option3 = async {
            withTimeout(15_000) {
                val url = "http://httpbin.org/delay/${Random.nextInt(1, 15)}"
                println("get $url")
                HttpClient(CIO).get(url)
            }
        }

        select {
            option1.onAwait { response ->
                println("selected response: ${response.request.url}")
                option2.cancel()
                option3.cancel()
            }
            option2.onAwait { response ->
                println("selected response: ${response.request.url}")
                option1.cancel()
                option3.cancel()
            }
            option3.onAwait { response ->
                println("selected response: ${response.request.url}")
                option1.cancel()
                option2.cancel()
            }
        }
    }
}
