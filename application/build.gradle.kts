plugins {
    id("java-starter")
    application
}

// 依赖库版本
val libVersion = extra

dependencies {
    implementation(project(":common"))
    implementation(project(":lib"))
    // guava
    implementation("com.google.guava:guava:${libVersion["guavaVersion"]}")
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

application {
    mainModule.set("java.starter")
    mainClass.set("app.Application")
    applicationDefaultJvmArgs = listOf(
        "--enable-preview",
        // log4j2 AsyncLogger
        "-Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector",
    )
}
