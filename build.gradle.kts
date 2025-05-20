plugins {
    id("java")
    id("groovy")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.4"
}

version = "0.1"
group = "cz.nekola.micronaut.mcp.demo.cli"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("info.picocli:picocli-codegen")
    implementation("info.picocli:picocli")
    implementation("io.micronaut.picocli:micronaut-picocli")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.modelcontextprotocol:kotlin-sdk:0.4.0")
    implementation("org.apache.groovy:groovy:4.0.18")
    runtimeOnly("ch.qos.logback:logback-classic")
    // jackson-module-kotlin is no longer needed when using pure Java

    testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
}


application {
    mainClass = "cz.nekola.micronaut.mcp.demo.cli.MicronautMcpCliCommand"
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}



micronaut {
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("cz.nekola.micronaut.mcp.demo.cli.*")
    }
}
