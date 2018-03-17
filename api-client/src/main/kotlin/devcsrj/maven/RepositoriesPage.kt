package devcsrj.maven

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.ElementConverter
import pl.droidsonroids.jspoon.annotation.Selector
import java.net.URI

internal class RepositoriesPage {

    @Selector("#maincontent > div.im")
    lateinit var entries: List<Entry>

    internal class Entry {

        @Selector("div.im-header > h2 > a", converter = IdConverter::class)
        lateinit var id: String

        @Selector("div.im-header > h2 > a")
        lateinit var name: String

        @Selector("div.im-header > p", converter = UriElementConverter::class)
        lateinit var uri: URI

        companion object IdConverter : ElementConverter<String> {

            override fun convert(node: Element, selector: Selector): String {
                val href = node.selectFirst(selector.value).attr("href")
                // hrefs are in the form of: /repos/{id}
                return href.substringAfterLast("/")
            }

        }
    }
}
