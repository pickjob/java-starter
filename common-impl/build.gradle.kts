plugins {
    id("java-starter")
}

val libVersion = extra

dependencies {
    implementation(project(":common"))
}

tasks.jar {
    manifest {
        attributes["Automatic-Module-Name"] = project.name
    }
}