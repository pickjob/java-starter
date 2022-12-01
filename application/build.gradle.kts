plugins {
    id("java-starter")
    application
}

// 依赖库版本
val libVersion = extra

dependencies {
    implementation("org.tukaani:xz:${libVersion["xzVersion"]}")
    implementation("org.apache.commons:commons-compress:${libVersion["compressVersion"]}")
}

application {
    mainModule.set("pickjob.java.starter")
    mainClass.set("app.Application")
    applicationDefaultJvmArgs = listOf(
        "--enable-preview",
        // log4j2 AsyncLogger
        "-Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector",
    )
}
