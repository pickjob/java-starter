plugins {
    id("java-dependencies")
    application
}

val library = extra

dependencies {
    implementation("jakarta.validation:jakarta.validation-api:${library["validation.version"]}")
    implementation("org.hibernate:hibernate-validator:${library["hibernate.version"]}")
    implementation("com.google.guava:guava:${library["guava.version"]}")
}