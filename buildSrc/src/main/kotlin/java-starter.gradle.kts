plugins {
    java
}

group = "pickjob"
version = "0.0.1"

// 依赖库版本
val libVersion = extra

dependencies {
    // logging
    implementation("org.apache.logging.log4j:log4j-api:${libVersion["log4jVersion"]}")
    implementation("org.apache.logging.log4j:log4j-core:${libVersion["log4jVersion"]}")
    // logging async support
    implementation("com.lmax:disruptor:${libVersion["disruptorVersion"]}")
    // compress
    implementation("org.tukaani:xz:${libVersion["xzVersion"]}")
    implementation("org.apache.commons:commons-compress:${libVersion["compressVersion"]}")
    // netty
    implementation("io.netty:netty-common:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-resolver:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-buffer:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-transport:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-transport-native-unix-common:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-transport-native-epoll:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-handler:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-codec:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-codec-http:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-codec-http2:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-codec-redis:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-codec-mqtt:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-codec-stomp:${libVersion["nettyVersion"]}")
    implementation("io.netty:netty-resolver-dns:${libVersion["nettyVersion"]}")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(Integer.valueOf("${libVersion["jdkVersion"]}")))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
    options.encoding = "UTF-8"
}

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
