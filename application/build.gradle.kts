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
