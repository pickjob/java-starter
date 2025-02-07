plugins {
    id("java-dependencies")
    application
}

val library = extra

dependencies {
    implementation("net.freeutils:jcharset:${library["jcharset.version"]}")
}