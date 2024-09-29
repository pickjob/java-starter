plugins {
    id("java-dependencies")
    `java-library`
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.javaModuleVersion = provider { version as String }
    options.compilerArgs.add("--enable-preview")
    options.compilerArgs.add("-Xlint:deprecation")
//    options.compilerArgs.add("-Xlint:unchecked")
//    options.compilerArgs.add("-Xlint:preview")
}
