mvnrepository | Client
===

[![Java CI](https://github.com/devcsrj/mvnrepository-client/actions/workflows/maven.yml/badge.svg)](https://github.com/devcsrj/mvnrepository-client/actions/workflows/maven.yml)
[![License](https://img.shields.io/github/license/devcsrj/mvnrepository-client.svg)](LICENSE)

This project provides a client for accessing data from [mvnrepository](http://mvnrepository.com/) - a site that indexes artifacts for [Maven](http://maven.apache.org/).

Download
---
Grab via Maven:

```xml
<dependency>
    <groupId>com.github.devcsrj</groupId>
    <artifactId>mvnrepository-api-client</artifactId>
    <version>${mvnrepo.version}</version>
</dependency>
```

or Gradle:

```groovy
compile 'com.github.devcsrj:mvnrepository-api-client:${mvnrepo.version}'
```


Usage
---
Create an instance using the factory method `create`:

```java
MvnRepositoryApi api = MvnRepositoryApi.create();
Page<ArtifactEntry> page = api.search("reactor-core");
```

> The above client defaults to [https://mvnrepository.com]().

For a CLI tool, checkout [jarl](https://github.com/devcsrj/jarl).

License
---
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
