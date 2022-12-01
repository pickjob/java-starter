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
