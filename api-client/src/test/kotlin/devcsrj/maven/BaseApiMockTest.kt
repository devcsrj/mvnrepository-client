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

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.Okio

abstract class BaseApiMockTest {

    /**
     * Returns a started [MockWebServer] with queued responses
     *
     * @param paths Classpath resources to lookup and queue
     */
    fun serverWithResponses(vararg paths: String): MockWebServer {
        val server = MockWebServer()
        paths.forEach {
            val buffer = Buffer()
            javaClass.getResourceAsStream(it).use {
                buffer.writeAll(Okio.source(it))
            }
            server.enqueue(MockResponse().setBody(buffer))
        }
        server.start()

        return server
    }
}
