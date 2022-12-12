plugins {
    id("java-starter")
}

// 依赖库版本
val libVersion = extra

tasks.jar({
    manifest {
        attributes["Automatic-Module-Name"] = "${project.name}"
//        attributes(mapOf("Automatic-Module-Name" to "${project.name}"))
    }
})
