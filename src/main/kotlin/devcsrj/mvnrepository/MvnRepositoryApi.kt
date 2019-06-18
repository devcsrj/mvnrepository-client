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
import java.util.Optional

/**
 * Central interface for accessing [https://mvnrepository.com].
 */
interface MvnRepositoryApi {

    /**
     * Retrieves the available indexed repositories in the server.
     *
     * Note that this only returns the top 20 indexed repository.
     */
    fun getRepositories(): List<Repository>

    /**
     * Retrieves the artifact corresponding to the address formed by the [groupId]:[artifactId]:[version].
     */
    fun getArtifact(groupId: String,
                    artifactId: String,
                    version: String): Optional<Artifact>

    /**
     * Retrieves the available indexed versions for the [groupId]:[artifactId].
     */
    fun getArtifactVersions(groupId: String, artifactId: String): List<String>

    /**
     * Searches the repository based from the provided [query].
     */
    fun search(query: String, page: Int = 1): Page<ArtifactEntry>

    companion object Factory {

        /**
         * Constructs a [MvnRepositoryApi] that fetches data from [https://mvnrepository.com/]
         * with a default http client.
         */
        fun create(url: HttpUrl = HttpUrl.parse("https://mvnrepository.com/")!!,
                   okHttpClient: OkHttpClient = OkHttpClient()): MvnRepositoryApi {
            return ScrapingMvnRepositoryApi(url, okHttpClient)
        }
    }
}
