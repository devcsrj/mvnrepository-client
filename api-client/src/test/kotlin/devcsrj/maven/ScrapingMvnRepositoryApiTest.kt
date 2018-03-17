/**
 * Copyright © 2018 Reijhanniel Jearl Campos (devcsrj@apache.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package devcsrj.maven

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.Okio
import org.testng.annotations.Test
import java.net.URI
import java.time.LocalDate
import java.time.Month
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ScrapingMvnRepositoryApiTest {

    @Test
    fun `can parse page from groupId-artifactId-version`() {
        val server = MockWebServer()
        val buffer = Buffer()
        javaClass.getResourceAsStream("/responses/groupId-artifactId-version.html").use {
            buffer.writeAll(Okio.source(it))
        }
        server.enqueue(MockResponse().setBody(buffer))
        server.start()

        val api = ScrapingMvnRepositoryApi(server.url("/"), OkHttpClient())
        val artifact = api.getArtifact("io.projectreactor", "reactor-core", "3.1.5.RELEASE")

        assert(artifact.isPresent)
        artifact.get().apply {
            assertEquals("io.projectreactor", groupId)
            assertEquals("reactor-core", id)
            assertEquals("3.1.5.RELEASE", version)
            assertEquals("Apache 2.0", license)
            assertEquals(LocalDate.of(2018, Month.FEBRUARY, 27), date)
            assertEquals(URI.create("https://github.com/reactor/reactor-core"), homepage)
            assertFalse { snippets.isEmpty() }
            snippets.forEach {
                assertFalse { it.value.isEmpty() }
            }
        }
    }

}
