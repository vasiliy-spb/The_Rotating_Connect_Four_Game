plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("application")
}

group = "dev.cheercode.connectfour"
//version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.fusesource.jansi:jansi:2.4.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("dev.cheercode.connectfour.MainForJar")
}

tasks.test {
    useJUnitPlatform()
}

// Конфигурация для shadowJar
tasks.shadowJar {
    manifest {
        from("src/main/resources/META-INF/MANIFEST.MF")
        attributes(
                "Main-Class" to application.mainClass.get(),
        )
    }
}