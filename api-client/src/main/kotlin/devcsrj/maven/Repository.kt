package devcsrj.maven

import java.net.URI

data class Repository(

    val id: String,
    val name: String,
    val uri: URI
)
