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

import pl.droidsonroids.jspoon.annotation.Selector
import java.net.URI
import java.util.Date

internal class ArtifactSelector {

    @Selector("#maincontent > table > tbody > tr:nth-child(1) > td > span")
    lateinit var license: String

    @Selector("#maincontent > table > tbody > tr:nth-child(3) > td > a", converter = HrefElementConverter::class)
    lateinit var homepage: URI

    @Selector("#maincontent > table > tbody > tr:nth-child(4) > td", format = "(MMM dd, yyyy)")
    lateinit var date: Date

    @Selector("#snippets", converter = SnippetElementConverter::class)
    lateinit var snippets: List<Snippet>


}
