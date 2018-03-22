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

import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.shell.jline.PromptProvider

@SpringBootApplication
internal open class Cli {

    @Bean
    open fun mvnRepositoryApi() = MvnRepositoryApi.create()

    @Bean
    open fun promptProvider() = PromptProvider {
        AttributedString("maven : ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW))
    }
}

fun main(args: Array<String>) {
    val argList = listOf(
        "--spring.shell.command.script.enabled=false")

    SpringApplicationBuilder()
        .bannerMode(Banner.Mode.CONSOLE)
        .sources(Cli::class.java)
        .run(*argList.toTypedArray())
}
