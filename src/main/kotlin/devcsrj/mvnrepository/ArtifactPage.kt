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

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.ElementConverter
import pl.droidsonroids.jspoon.annotation.Selector
import java.net.URI
import java.util.*

internal class ArtifactPage {

    @Selector("main > div.content > table > tbody > tr:nth-child(1) > td > span")
    lateinit var license: String

    @Selector("main > div.content > table > tbody > tr:nth-child(5) > td > a",
        attr = "href", converter = UriElementConverter::class)
    lateinit var homepage: URI
    @Selector("main > div.content > table > tbody > tr:nth-child(6) > td", format = "MMM dd, yyyy")
    lateinit var date: Date

    @Selector("div.snippets li > a", converter = SnippetElementConverter::class)
    lateinit var snippets: List<Snippet>


    internal class SnippetElementConverter : ElementConverter<List<Snippet>> {

        override fun convert(node: Element?, selector: Selector): List<Snippet> {
            val links = node?.select(selector.value) ?: return emptyList()

            val snippets = mutableListOf<Snippet>()
            for (link in links) {
                val href = link.attr("href")
                val type = getSnippetType(href)
                val snippet = node.select("textarea$href-a").`val`() ?: continue
                snippets.add(Snippet(type, snippet))
            }
            return snippets.toList()
        }

        /**
         * Expects href to be in the form of:
         *  - #maven
         *  - #gradle
         *  - #gradle-short
         *  - #gradle-short-kotlin
         *  - #sbt
         *  - #ivy
         *  - #leiningen
         *  - #grape
         *  - #builder
         */
        private fun getSnippetType(href: String): Snippet.Type {
            val type = href.substring(1)
            val uppercased = type.uppercase(Locale.getDefault())
            val underscored = uppercased.replace('-', '_')
            return Snippet.Type.valueOf(underscored)
        }

    }
}
