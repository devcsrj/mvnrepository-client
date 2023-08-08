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
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import java.time.ZoneId
import java.util.Optional

internal class ScrapingMvnRepositoryApi(
    private val baseUrl: HttpUrl,
    private val okHttpClient: OkHttpClient) : MvnRepositoryApi {

    companion object {

        const val MAX_LIMIT = 10 // Pages are always in 10 entries
        const val MAX_PAGE = 50 // Site throws a 404 when exceeding 50
    }

    private val logger: Logger = LoggerFactory.getLogger(MvnRepositoryApi::class.java)
    private val pageApi: MvnRepositoryPageApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(JspoonConverterFactory.create())
            .validateEagerly(true)
            .build()
        pageApi = retrofit.create(MvnRepositoryPageApi::class.java)
    }

    override fun getRepositories(): List<Repository> {
        val response = pageApi.getRepositoriesPage().execute()
        if (!response.isSuccessful) {
            logger.warn("Request to $baseUrl failed while fetching repositories, got: ${response.code()}")
            return emptyList()
        }
        val page = response.body();
        return if (page !== null) {
            page.entries
                .filter { it.isPopulated() }
                .map { Repository(it.id!!, it.name!!, it.uri!!) }
        } else {
            emptyList()
        }
    }

    override fun getArtifactVersions(groupId: String, artifactId: String): List<String> {
        val response = pageApi.getArtifactVersionsPage(groupId, artifactId).execute()
        if (!response.isSuccessful) {
            logger.warn("Request to $baseUrl failed while fetching versions for artifact '" +
                "$groupId:$artifactId', got: ${response.code()}")
            return emptyList()
        }

        val body = response.body() ?: return emptyList()
        return body.versions
    }

    override fun getArtifact(groupId: String, artifactId: String, version: String): Optional<Artifact> {
        val response = pageApi.getArtifactPage(groupId, artifactId, version).execute()
        if (!response.isSuccessful) {
            logger.warn("Request to $baseUrl failed while fetching artifact '" +
                "$groupId:$artifactId:$version', got: ${response.code()}")
            return Optional.empty()
        }
        val body = response.body() ?: return Optional.empty()
        val localDate = body.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        val artifact = Artifact(groupId, artifactId, version, body.license, body.homepage, localDate, body.snippets)

        return Optional.of(artifact)
    }

    override fun search(query: String, page: Int): Page<ArtifactEntry> {
        if (page < 1 || page > MAX_PAGE)
            return Page.empty()

        val response = pageApi.search(query, page, "relevance").execute()
        if (!response.isSuccessful) {
            logger.warn("Request to $baseUrl failed while searching for '$query' on page '$page'")
            return Page.empty()
        }

        val body = response.body() ?: return Page.empty()
        val entries = body.entries
            .filter { it.isPopulated() }
            .map { ArtifactEntry(it.groupId!!, it.artifactId!!, it.license, it.description!!, it.releaseDate!!) }

        val totalPages = Math.min(Math.ceil((body.totalResults / MAX_LIMIT).toDouble()).toInt(), MAX_PAGE)
        return Page(page, MAX_LIMIT, entries.toList(), totalPages, body.totalResults)
    }
}
