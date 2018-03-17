package devcsrj.maven

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.Okio

abstract class BaseApiMockTest {

    /**
     * Returns a started [MockWebServer] with queued responses
     *
     * @param paths Classpath resources to lookup and queue
     */
    fun serverWithResponses(vararg paths: String): MockWebServer {
        val server = MockWebServer()
        paths.forEach {
            val buffer = Buffer()
            javaClass.getResourceAsStream(it).use {
                buffer.writeAll(Okio.source(it))
            }
            server.enqueue(MockResponse().setBody(buffer))
        }
        server.start()

        return server
    }
}
