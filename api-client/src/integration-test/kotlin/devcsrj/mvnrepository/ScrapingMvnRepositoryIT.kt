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

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import java.net.URI
import java.time.LocalDate
import java.time.Month
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ScrapingMvnRepositoryIT {

    private lateinit var api: MvnRepositoryApi

    @BeforeClass
    fun setup() {
        val url = HttpUrl.parse("https://mvnrepository.com")!!
        api = ScrapingMvnRepositoryApi(url, OkHttpClient())
    }

    @Test
    fun `can parse repositories page`() {
        val repositories = api.getRepositories()

        assertFalse { repositories.isEmpty() }
        assertEquals(20, repositories.size)
    }

    @Test
    fun `can parse page from groupId-artifactId-version`() {
        val artifact = api.getArtifact("io.projectreactor", "reactor-core", "3.1.5.RELEASE")

        assertTrue { artifact.isPresent }
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


    @Test
    fun `can parse search results page`() {
        val result = api.search("reactor")

        assertEquals(1, result.number)
        assertTrue { result.totalItems > 500 }
        assertEquals(ScrapingMvnRepositoryApi.MAX_PAGE, result.totalPages)
        assertEquals(ScrapingMvnRepositoryApi.MAX_LIMIT, result.limit)
        assertEquals(ScrapingMvnRepositoryApi.MAX_LIMIT, result.items.size)

    }

    @AfterClass(alwaysRun = true)
    @Throws(Exception::class)
    fun tearDown() {

    }
}
