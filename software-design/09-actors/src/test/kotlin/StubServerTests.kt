import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import com.fasterxml.jackson.databind.ObjectMapper
import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action.delay
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition.uri
import com.xebialabs.restito.server.StubServer
import scala.concurrent.`AwaitPermission$`.`MODULE$`
import java.time.Duration
import java.util.concurrent.atomic.AtomicReference
import kotlin.test.Test
import kotlin.test.assertEquals

class StubServerTests {

    private val port = 32453
    private val address = "http://localhost:$port"

    private data class RawLink(val header: String, val url: String)

    private val rawLinks = listOf(
        RawLink("Bing", "https://www.bing.com/"),
        RawLink("Google", "https://google.com/"),
        RawLink("Yandex", "https://yandex.ru/")
    )

    private val objectMapper = ObjectMapper()

    private val childMap = mapOf(
        "zero" to "$address/0",
        "first" to "$address/1",
        "second" to "$address/2",
    )

    private val testResultQuantity = AtomicReference<Int?>(null)

    @Test
    fun `aggregator is stopped by result quantity satisfaction`() = withStubSearchServer {
        performRequest(rawLinks.size * childMap.size, Duration.ofDays(1))
    }

    @Test
    fun `aggregator is stopped by timeout exceeding`() = withStubSearchServer(listOf(0, 1, 2)) {
        performRequest(rawLinks.size, Duration.ofMillis(500))
    }

    private fun withStubSearchServer(
        secondFreezes: List<Int>,
        requests: () -> ActorSystem
    ) {
        check(secondFreezes.size == childMap.size)

        var stubServer: StubServer? = null

        try {
            stubServer = StubServer(port).run()

            secondFreezes.forEachIndexed { index, it ->
                whenHttp(stubServer)
                    .match(uri("/$index"))
                    .then(delay(it * 1000), stringContent(objectMapper.writeValueAsString(rawLinks)))
            }

            requests()
        } finally {
            stubServer?.stop()
        }
    }

    private fun withStubSearchServer(requests: () -> ActorSystem) =
        withStubSearchServer(List(childMap.size) { 0 }, requests)

    private fun performRequest(
        expectedAmount: Int,
        timeout: Duration = Duration.ofSeconds(1)
    ): ActorSystem {
        testResultQuantity.set(null)

        val request = Request("request")

        val actorSystem = ActorSystem.create("searching")
        val props = Props.create(
            RequestAggregator::class.java,
            childMap,
            timeout,
            { it: List<Link> -> testResultQuantity.set(it.size) },
        )


        actorSystem.log().info("Sending request: $request")
        actorSystem.actorOf(props, "aggregator").tell(request, ActorRef.noSender())

        while (testResultQuantity.get() == null) {
            println("Sleeping")
            Thread.sleep(500)
        }

        actorSystem.terminate().result(scala.concurrent.duration.Duration.Inf(), `MODULE$`)

        assertEquals(expectedAmount, testResultQuantity.get())

        return actorSystem
    }
}
