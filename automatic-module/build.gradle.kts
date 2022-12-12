plugins {
    id("java-starter")
}

// 依赖库版本
val libVersion = extra

dependencies {
    /**
     * Jakarta EE
     */
    // Expression language
    implementation("jakarta.el:jakarta.el-api:${libVersion["elVersion"]}")
    // bean validation
    // implementation("jakarta.validation:jakarta.validation-api:${libVersion["validationVersion"]}")
    implementation("org.hibernate.validator:hibernate-validator:${libVersion["hibernateVersion"]}")
}

tasks.jar({
    manifest {
        attributes["Automatic-Module-Name"] = "${project.name}"
//        attributes(mapOf("Automatic-Module-Name" to "${project.name}"))
    }
})
