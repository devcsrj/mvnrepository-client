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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

internal class ArtifactSearchEntriesPage {

    @Selector("div.im:has(a)")
    lateinit var entries: List<Entry>

    @Selector("div.content > h2 > b")
    var totalResults: Long = 0L

    internal class Entry {

        @Selector("div.im-header > p > a:nth-child(1)")
        var groupId: String? = null

        @Selector("div.im-header > p > a:nth-child(2)")
        var artifactId: String? = null

        @Selector("div.im-header > p > span")
        var license: String? = null

        @Selector("div.im-description")
        var description: String? = null

        @Selector("div.im-description > div", converter = ReleaseDateElementConverter::class)
        var releaseDate: LocalDate? = null

        /**
         * @return whether this object is properly populated from the page
         */
        fun isPopulated(): Boolean = groupId != null && artifactId != null

        companion object ReleaseDateElementConverter : ElementConverter<LocalDate> {

            private const val PREFIX = "Last Release on "

            override fun convert(root: Element, selector: Selector): LocalDate? {
                val node = root.text()
                val str = node.substringAfter(PREFIX)
                val pattern = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault())
                return LocalDate.parse(str, pattern)

            }

        }

    }
}
