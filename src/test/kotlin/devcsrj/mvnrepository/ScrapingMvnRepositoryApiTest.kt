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
package devcsrj.mvnrepository

import devcsrj.mvnrepository.ScrapingMvnRepositoryApi.Companion.MAX_LIMIT
import devcsrj.mvnrepository.ScrapingMvnRepositoryApi.Companion.MAX_PAGE
import okhttp3.OkHttpClient
import org.junit.Test
import java.net.URI
import java.time.LocalDate
import java.time.Month
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ScrapingMvnRepositoryApiTest : BaseApiMockTest() {

    @Test
    fun `can parse repositories page`() {
        val server = serverWithResponses(
            "/responses/_repos.html",
        )

        val api = ScrapingMvnRepositoryApi(server.url("/"), OkHttpClient())
        val repositories = api.getRepositories()

        assertFalse { repositories.isEmpty() }
        assertEquals(20, repositories.size)
    }

    @Test
    fun `can parse artifact versions page`() {
        val server = serverWithResponses("/responses/artifact-versions-page.html")

        val api = ScrapingMvnRepositoryApi(server.url("/"), OkHttpClient())
        val versions = api.getArtifactVersions("io.projectreactor", "reactor-core")

        assertFalse { versions.isEmpty() }
    }

    @Test
    fun `can parse artifact page`() {
        val server = serverWithResponses("/responses/artifact-page.html")

        val api = ScrapingMvnRepositoryApi(server.url("/"), OkHttpClient())
        val artifact = api.getArtifact("io.projectreactor", "reactor-core", "3.5.8")

        assertTrue { artifact.isPresent }
        artifact.get().apply {
            assertEquals("io.projectreactor", groupId)
            assertEquals("reactor-core", id)
            assertEquals("3.5.8", version)
            assertEquals("Apache 2.0", license)
            assertEquals(LocalDate.of(2023, Month.JULY, 11), date)
            assertEquals(URI.create("https://github.com/reactor/reactor-core"), homepage)
            assertFalse { snippets.isEmpty() }
            snippets.forEach {
                assertFalse { it.value.isEmpty() }
            }
        }
    }

    @Test
    fun `can parse search results page`() {
        val server = serverWithResponses("/responses/search-reactor-page-p1.html")

        val api = ScrapingMvnRepositoryApi(server.url("/"), OkHttpClient())
        val result = api.search("reactor")

        assertEquals(1, result.number)
        assertEquals(545, result.totalItems)
        assertEquals(MAX_PAGE, result.totalPages)
        assertEquals(MAX_LIMIT, result.limit)
        assertEquals(MAX_LIMIT, result.items.size)

    }
}
