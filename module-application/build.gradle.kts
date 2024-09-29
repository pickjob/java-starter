plugins {
    id("java-dependencies")
    application
}

dependencies {
    implementation(project(":common_implement"))
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.javaModuleVersion = provider { version as String }
    options.compilerArgs.add("--enable-preview")
    options.compilerArgs.add("-Xlint:deprecation")
//    options.compilerArgs.add("-Xlint:unchecked")
//    options.compilerArgs.add("-Xlint:preview")
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs("--enable-preview")
}

application {
    mainModule = "java.starter.main"
    mainClass = "app.showcase.ShowcaseTemplate"
    applicationDefaultJvmArgs = listOf(
        "--enable-preview",
        "--enable-native-access=java.starter",
        // log4j2 AsyncLogger
        "-Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector",
        // netty lead detection
        "-Dio.netty.leakDetection.level=paranoid",
    )
}