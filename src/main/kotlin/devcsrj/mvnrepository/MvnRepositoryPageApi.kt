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

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MvnRepositoryPageApi {

    @GET("/search")
    fun search(@Query("q") query: String,
               @Query("p") page: Int,
               @Query("sort") sort: String): Call<ArtifactSearchEntriesPage>

    @GET("/repos")
    fun getRepositoriesPage(): Call<RepositoriesPage>

    @GET("/artifact/{groupId}/{artifactId}")
    fun getArtifactVersionsPage(@Path("groupId") groupId: String,
                                @Path("artifactId") artifactId: String): Call<ArtifactVersionsPage>

    @GET("/artifact/{groupId}/{artifactId}/{version}")
    fun getArtifactPage(@Path("groupId") groupId: String,
                        @Path("artifactId") artifactId: String,
                        @Path("version") version: String): Call<ArtifactPage>

}
