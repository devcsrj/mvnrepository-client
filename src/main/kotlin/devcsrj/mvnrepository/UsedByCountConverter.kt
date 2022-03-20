/**
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
import java.math.BigDecimal

/**
 *
 * Finds the "Used By" column in the mvnrepo table if it exists, parses it and returns a Used by value as a
 * BigDecimal. Or returns 0 if none can be found.
 */
internal class UsedByCountConverter :ElementConverter<BigDecimal> {

    override fun convert(root: Element, selector: Selector): BigDecimal? {
        val stringValue:Element = root.selectFirst(selector.value);
        for( node in stringValue.childNodes() ) {
            for( subNode in node.childNodes()) {
                if(subNode.toString().contains(" artifacts")) {
                    var hrefString:String = subNode.toString()
                    var hrefSub:String = hrefString.split(" artifacts")[0].replace(",","")
                    var lastIndex = hrefSub.lastIndexOf(">")

                    return BigDecimal(hrefSub.substring(lastIndex+1,hrefSub.length))
                }
            }
        }
        return BigDecimal.ZERO
    }
}
