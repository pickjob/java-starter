plugins {
    java
}

version = "1.0.0"
group = "java-starter"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

val library = extra
dependencies {
    // logging
    implementation("org.apache.logging.log4j:log4j-api:${library["log4j.version"]}")
    implementation("org.apache.logging.log4j:log4j-core:${library["log4j.version"]}")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${library["log4j.version"]}")

    // logging: async support
    implementation("org.tukaani:xz:${library["xz.version"]}")
    implementation("com.lmax:disruptor:${library["disruptor.version"]}")
    // commons io
    implementation("commons-io:commons-io:${library["commons.io.version"]}")
    // commons compress
    implementation("org.apache.commons:commons-compress:${library["commons.compress.version"]}")
    // commons codec
    implementation("commons-codec:commons-codec:${library["commons.codec.version"]}")
    // jackson
    implementation("com.fasterxml.jackson.core:jackson-core:${library["jackson.version"]}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${library["jackson.version"]}")
    implementation("com.fasterxml.jackson.core:jackson-annotations:${library["jackson.version"]}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${library["jackson.version"]}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-properties:${library["jackson.version"]}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${library["jackson.version"]}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:${library["jackson.version"]}")
    // jose4j
    implementation("org.bitbucket.b_c:jose4j:${library["jose4j.version"]}")
    // netty
    implementation("io.netty:netty-common:${library["netty.version"]}")
    implementation("io.netty:netty-resolver:${library["netty.version"]}")
    implementation("io.netty:netty-buffer:${library["netty.version"]}")
    implementation("io.netty:netty-transport:${library["netty.version"]}")
    implementation("io.netty:netty-transport-native-unix-common:${library["netty.version"]}")
    implementation("io.netty:netty-transport-native-epoll:${library["netty.version"]}")
    implementation("io.netty:netty-handler:${library["netty.version"]}")
    implementation("io.netty:netty-codec:${library["netty.version"]}")
    implementation("io.netty:netty-codec-http:${library["netty.version"]}")
    implementation("io.netty:netty-codec-http2:${library["netty.version"]}")
    implementation("io.netty:netty-codec-redis:${library["netty.version"]}")
    implementation("io.netty:netty-codec-mqtt:${library["netty.version"]}")
    implementation("io.netty:netty-codec-stomp:${library["netty.version"]}")
    implementation("io.netty:netty-resolver-dns:${library["netty.version"]}")
    implementation("io.netty:netty-resolver-dns:${library["netty.version"]}")
    // implementation("io.netty:netty-all:${library["netty.version"]}")
    // sqlite3
    implementation("org.xerial:sqlite-jdbc:${library["xerial.version"]}")
    // hikariCP
    implementation("com.zaxxer:HikariCP:${library["hikari.version"]}")
    // mybatis
    implementation("org.mybatis:mybatis:${library["mybatis.version"]}")
    // javaassist
    implementation("org.javassist:javassist:${library["javaassist.version"]}")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(Integer.valueOf("${library["jdk.version"]}")))
    }
}