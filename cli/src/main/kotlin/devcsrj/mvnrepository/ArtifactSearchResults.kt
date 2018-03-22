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

import org.springframework.shell.TerminalSizeAware
import org.springframework.shell.table.CellMatchers
import org.springframework.shell.table.SimpleHorizontalAligner
import org.springframework.shell.table.SizeConstraints
import org.springframework.shell.table.Table
import org.springframework.shell.table.TableBuilder

internal class ArtifactSearchResults(private val page: Page<ArtifactEntry>) : TerminalSizeAware {

    private lateinit var table: Table

    init {
        val model = TableModel(page.items)

        val tableBuilder = TableBuilder(model)
        tableBuilder
            .on(CellMatchers.column(0))
            .addSizer { _, _, _ -> SizeConstraints.Extent(20, 50) }
            .on(CellMatchers.column(1))
            .addSizer { _, _, _ -> SizeConstraints.Extent(20, 50) }
            .on(CellMatchers.column(2))
            .addSizer { _, _, _ -> SizeConstraints.Extent(15, 15) }
            .on(CellMatchers.column(3))
            .addSizer { _, _, _ -> SizeConstraints.Extent(100, 100) }

        this.table = tableBuilder.build()
    }

    override fun render(terminalWidth: Int): CharSequence {
        var tableCs = table.render(terminalWidth)
        tableCs += "\n"
        tableCs += "Page ${page.number} of ${page.totalPages} (found ${page.totalItems} results)"
        return tableCs
    }

    class TableModel(collection: List<ArtifactEntry>) : BaseTableModel<ArtifactEntry>(COLUMNS, collection) {

        companion object {

            private val COLUMNS = arrayOf("Group", "Artifact", "Last Release", "Description")
        }

        override fun getColumnValue(t: ArtifactEntry, column: Int): Any {
            return when (column) {
                0 -> t.groupId
                1 -> t.id
                2 -> t.lastRelease
                3 -> t.description
                else -> throw IndexOutOfBoundsException("Unknown column $column")
            }
        }

    }

}
