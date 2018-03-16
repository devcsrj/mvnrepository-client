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
import kotlin.test.assertEquals

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
        assertEquals("io.projectreactor", artifact.get().groupId)
        assertEquals("reactor-core", artifact.get().id)
        assertEquals("3.1.5.RELEASE", artifact.get().version)

    }

}
