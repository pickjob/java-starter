plugins {
    id("java-dependencies")
    application
}

val library = extra

dependencies {
    // jee validation
    implementation("jakarta.validation:jakarta.validation-api:${library["validation.version"]}")
    // hibernate validator
    implementation("org.hibernate:hibernate-validator:${library["hibernate.version"]}")
    // guava
    implementation("com.google.guava:guava:${library["guava.version"]}")

    // aspectj
    implementation("org.aspectj:aspectjweaver:${library["aspectj.version"]}")

    // spring-framework
    implementation("org.springframework:spring-core:${library["spring.framework.version"]}")
    implementation("org.springframework:spring-beans:${library["spring.framework.version"]}")
    implementation("org.springframework:spring-context:${library["spring.framework.version"]}")
    implementation("org.springframework:spring-context-support:${library["spring.framework.version"]}")
    implementation("org.springframework:spring-jdbc:${library["spring.framework.version"]}")
}