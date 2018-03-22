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

import org.springframework.shell.table.TableModel

/**
 * Base class for table models that contain a header, and is populated by a collection.
 *
 * @param <T> Specific model type
 */
internal abstract class BaseTableModel<in T>(private val columnNames: Array<String>,
                                             private val collection: Collection<T>) : TableModel() {

    override fun getRowCount(): Int {
        return collection.size + 1
    }

    override fun getColumnCount(): Int {
        return columnNames.size
    }

    override fun getValue(row: Int, column: Int): Any? {
        if (row <= 0)
            return columnNames[column]

        val t = collection.elementAtOrElse(row - 1, { null }) ?: return null
        return getColumnValue(t, column)
    }

    internal abstract fun getColumnValue(t: T, column: Int): Any
}
