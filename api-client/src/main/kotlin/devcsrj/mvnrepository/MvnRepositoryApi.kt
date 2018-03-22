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

interface MvnRepositoryApi {

    fun getRepositories(): List<Repository>

    fun getArtifact(groupId: String,
                    artifactId: String,
                    version: String): Optional<Artifact>

    fun getArtifactVersions(groupId: String, artifactId: String): List<String>

    fun search(query: String, page: Int = 1): Page<ArtifactEntry>

    companion object Factory {

        fun create(url: HttpUrl = HttpUrl.parse("https://mvnrepository.com/")!!,
                   okHttpClient: OkHttpClient = OkHttpClient()): MvnRepositoryApi {
            return ScrapingMvnRepositoryApi(url, okHttpClient)
        }
    }
}
