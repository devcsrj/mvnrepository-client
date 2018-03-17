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

import java.net.URI
import java.time.LocalDate

/**
 * Represents a Maven artifact.
 *
 * In a typical [pom.xml], this corresponds to the dependency section:
 * ```
 *  <dependency>
 *      <groupId>...</groupId>
 *      <artifactId>...</artifactId>
 *      <version>...</version>
 *  </dependency>
 * ```
 */
data class Artifact(
    val groupId: String,
    val id: String,
    val version: String,
    val license: String,
    val homepage: URI,
    val date: LocalDate,
    val snippets: List<Snippet>
)
