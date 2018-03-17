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
package devcsrj.maven

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.ElementConverter
import pl.droidsonroids.jspoon.annotation.Selector
import java.net.URI

/**
 * Transforms href attributes into a URL.
 */
internal class HrefElementConverter : ElementConverter<URI> {

    override fun convert(root: Element?, selector: Selector): URI {
        val href = root?.selectFirst(selector.value)?.attr("href")!!
        return URI.create(href)
    }

}
