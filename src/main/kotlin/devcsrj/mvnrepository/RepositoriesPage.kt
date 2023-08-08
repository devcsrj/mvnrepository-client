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

internal class RepositoriesPage {

    @Selector("div.im")
    lateinit var entries: List<Entry>

    internal class Entry {

        @Selector("h2.im-title > a",  attr = "href", converter = IdConverter::class)
        var id: String? = null

        @Selector("h2.im-title > a")
        var name: String? = null

        @Selector("p.im-subtitle", converter = UriElementConverter::class)
        var uri: URI? = null

        /**
         * @return whether this object is properly populated from the page
         */
        fun isPopulated(): Boolean = id != null && name != null && uri != null

        companion object IdConverter : ElementConverter<String> {

            override fun convert(node: Element, selector: Selector): String? {
                val href = node.attr(selector.attr)
                // hrefs are in the form of: /repos/{id}
                return href?.substringAfterLast("/")
            }

        }
    }
}
